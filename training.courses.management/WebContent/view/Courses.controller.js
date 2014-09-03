sap.ui.core.mvc.Controller.extend("training.courses.management.view.Courses", {

	MODELS : {
		courseProviders : "courseProviders",
		context : "context",
		courses : "courses"
	},

	onInit : function() {
		this._subscribeForCustomEvents();
	},

	_subscribeForCustomEvents : function() {
		sap.ui.getCore().getEventBus().subscribe("getFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);
	},

	_searchEnabled : false,

	_enableSearchLayout : function(bEnable) {
		this.getView().byId("searchLayout").setVisible(bEnable);
	},

	_enableCourseProviderCombo : function(bEnable) {
		this.getView().byId("courseProviderNameCombo").setEnabled(bEnable);
	},

	_enableUrlTextField : function(bEnable) {
		this.getView().byId("providerUrlTextField").setEnabled(bEnable);
	},

	_enableOpenCourseBtn : function(bEnable) {
		this.getView().byId("courseOpen").setEnabled(bEnable);
	},

	_enableSearchField : function(bEnable) {
		this.getView().byId("searchField").setEnabled(bEnable);
	},

	load : function() {
		var context = training.courses.management.util.Helper.getContext();
		if (!context) {
			this._searchEnabled = true;
			this._enableSearchLayout(true);
			this._enableCourseProviderCombo(false);
			this._enableUrlTextField(false);
		} else {
			this._asynchLoadModel(this.MODELS.context, "/rest/api/v1/contexts/" + context);
		}
		this._enableOpenCourseBtn(false);
		this._clearTheFields();
		this.refresh(context);
	},

	refresh : function(context) {
		this._asynchLoadModel(this.MODELS.courseProviders, "/rest/api/v1/providers");

		var coursesModel = new sap.ui.model.json.JSONModel();
		coursesModel.setData({});
		this.getView().setModel(coursesModel, this.MODELS.courses);

		var courseProvidersModel = this.getView().getModel(this.MODELS.courseProviders);
		if (!courseProvidersModel || courseProvidersModel.getData().length == 0) {
			sap.ui.commons.MessageBox.alert("missingRegisteredCoursesProvidersMsg".localize(), null, "alert".localize());
			this._enableSearchField(false);
			this._enableCourseProviderCombo(false);
			this._enableUrlTextField(false);
		} else {
			this._enableSearchField(true);
		}
	},

	_asynchLoadModel : function(modelName, dataPath) {
		var model = new sap.ui.model.json.JSONModel();
		model.loadData(dataPath, null, false);
		this.getView().setModel(model, modelName);
	},

	_clearTheFields : function() {
		this.getView().byId("courseProviderNameCombo").setValue(null);
		this.getView().byId("providerUrlTextField").setValue(null);
		this.getView().byId("searchField").setValue(null);
	},

	formatCount : function(aItems) {
		return aItems && aItems.length ? "(" + aItems.length + ")" : "";
	},

	onCourseProviderSelect : function(evnt) {
		var selectedItem = evnt.getParameter("selectedItem");
		if (!selectedItem) {
			return;
		}

		var selectedCourseProvider = selectedItem.getBindingContext("courseProviders").getObject();
		this._setURL(selectedCourseProvider.url);

		if (selectedCourseProvider.courses && !this._searchEnabled) {
			this.getView().getModel(this.MODELS.courses).setData(selectedCourseProvider.courses);
		} else {
			var courses = this._loadProviderCourses(selectedCourseProvider);
			this._filterCourses(courses);
			selectedCourseProvider.courses = courses;
			this.getView().getModel(this.MODELS.courses).setData(courses);
		}
	},

	_setURL : function(sUrl) {
		this.getView().byId("providerUrlTextField").setValue(sUrl);
	},

	_loadProviderCourses : function(courseProvider) {
		this.getView().setBusy(true);
		this.getView().setBusyIndicatorDelay(500);
		var context = this.getView().getModel(this.MODELS.context).getData();
		var aKeywords = context.keywords;
		if (aKeywords.length == 0) {
			sap.ui.commons.MessageBox.alert("missingKeywords".localize(), null, "error".localize());
			return {};
		}

		var courses = [];
		for (var idx = 0; idx < aKeywords.length; idx++) {
			var foundCourses = this._searchCourses(courseProvider.name, aKeywords[idx]);
			if (foundCourses) {
				courses = courses.concat(foundCourses);
			}
		}
		return courses;
	},

	_searchCourses : function(providerName, oKeyword) {
		var successFunc = jQuery.proxy(function(oResponseData) {
			console.log("Successful search for keyword " + oKeyword.name);
		}, this);

		var failFunc = jQuery.proxy(function(oResponseData) {
			if (oResponseData.status == 400) {
				sap.ui.commons.MessageBox.alert("Could not find courses with keyword " + oKeyword.name, null, "operationFailed"
						.localize());
			} else {
				sap.ui.commons.MessageBox.alert("Failed to get courses with keyword " + oKeyword.name + ".", null,
						"operationFailed".localize());
			}
		}, this);

		var path = "/rest/api/v1/providers/" + providerName + "/courses?searchPhrase=" + oKeyword.name;
		return training.courses.management.util.Helper.synchGetJSON(path, successFunc, failFunc);
	},

	_filterCourses : function(courses) {
		var aTitles = [];
		for (var idx = 0; idx < courses.length; idx++) {
			var courseTitle = courses[idx].title;
			if (aTitles.indexOf(courseTitle) != -1 || training.courses.management.util.Helper.isInvalidString(courseTitle)) {
				courses.splice(idx, 1);
				continue;
			}
			aTitles.push(courseTitle);
		}
	},

	onAfterRendering : function() {
		this.load();
	},

	onRowChange : function(evnt) {
		this._enableOpenCourseBtn(true);
	},

	onShowCourse : function(evnt) {
		var selectedCourseData = this._getSelectedItemData();
		var win = window.open(selectedCourseData.url, '_blank');
		win.focus();
	},

	_getSelectedItemData : function() {
		var table = this.byId("coursesTable");
		return table.getModel("courses").getProperty("/" + table.getSelectedIndex());
	},

	onSearch : function(evnt) {
		var query = evnt.getParameter("query");
		this.getView().byId("courseProviderNameCombo").setSelectedItemId(null);
		if (training.courses.management.util.Helper.isInvalidString(query)) {
			sap.ui.commons.MessageBox.alert("searchEmptyStringMsg".localize(), null, "error".localize());
			this._enableCourseProviderCombo(false);
			this._enableUrlTextField(false);
			return;
		}
		var ctxModel = new sap.ui.model.json.JSONModel();
		ctxModel.setData({
			"name" : "searchContext",
			"keywords" : [{
				"name" : query.trim()
			}]
		});
		this.getView().setModel(ctxModel, this.MODELS.context);
		this._enableCourseProviderCombo(true);
		this._enableUrlTextField(true);
	}

});

sap.ui.core.mvc.Controller.extend("training.courses.management.view.Courses", {

	MODELS : {
		courseProviders : "courseProviders",
		context : "context",
		courses : "courses"
	},

	CONTROLS : null,

	onInit : function() {
		this._loadControls();
		this._subscribeForCustomEvents();
		this._attachCustomEvents();
	},

	_loadControls : function() {
		this.CONTROLS = {
			searchField : this.byId("searchField"),
			courseProviderNameCombo : this.byId("courseProviderNameCombo"),
			providerUrlTextField : this.byId("providerUrlTextField"),
			searchLayout : this.byId("searchLayout"),
			courseOpenBtn : this.byId("courseOpen"),
			coursesTable : this.byId("coursesTable"),
			providerOpenBtn : this.byId("providerOpenBtn")
		};
	},

	_subscribeForCustomEvents : function() {
		sap.ui.getCore().getEventBus().subscribe("getFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);
	},

	_attachCustomEvents : function() {
		this.CONTROLS.searchField.attachBrowserEvent("click", jQuery.proxy(this.onSearchFieldClicked, this));
	},

	onSearchFieldClicked : function(evnt) {
		this.CONTROLS.courseProviderNameCombo.setValue(null);
		this._setURL(null);
		this._enableCourseProviderCombo(false);
		this.CONTROLS.coursesTable.setSelectedIndex(undefined);
		this._enableOpenCourseBtn(false);
		this.getView().getModel(this.MODELS.courses).setData([]);
	},

	_searchEnabled : false,

	_enableSearchLayout : function(bEnable) {
		this.CONTROLS.searchLayout.setVisible(bEnable);
	},

	_enableCourseProviderCombo : function(bEnable) {
		this.CONTROLS.courseProviderNameCombo.setEnabled(bEnable);
	},

	_enableOpenCourseBtn : function(bEnable) {
		this.CONTROLS.courseOpenBtn.setEnabled(bEnable);
	},

	_enableSearchField : function(bEnable) {
		this.CONTROLS.searchField.setEnabled(bEnable);
	},

	_enableProviderOpenBtn : function(bEnable) {
		this.CONTROLS.providerOpenBtn.setEnabled(bEnable);
	},

	load : function() {
		var context = training.courses.management.util.Helper.getContext();
		if (!context) {
			this._searchEnabled = true;
			this._enableSearchLayout(true);
			this._enableCourseProviderCombo(false);
		} else {
			this._loadContextsModel(context);
		}
		this._enableOpenCourseBtn(false);
		this._clearTheFields();
		this.refresh(context);
	},

	_loadContextsModel : function(context) {
		var successFunc = jQuery.proxy(function(oResponseData) {
			console.log("Successful search for context " + context);
		}, this);

		var failFunc = jQuery.proxy(function(oResponseData) {
			if (oResponseData.status == 400) {
				sap.ui.commons.MessageBox.alert("Could not find context with name " + context, null, "operationFailed"
						.localize());
			} else {
				sap.ui.commons.MessageBox.alert("Failed to get courses with keyword " + oKeyword.name + ".", null,
						"operationFailed".localize());
			}
		}, this);

		var data = training.courses.management.util.Helper.synchGetJSON("/rest/api/v1/contexts/" + context, successFunc,
				failFunc);
		var ctxModel = new sap.ui.model.json.JSONModel();
		ctxModel.setData(data);
		this.getView().setModel(ctxModel, this.MODELS.context);
	},

	refresh : function(context) {
		this._asynchLoadModel(this.MODELS.courseProviders, "/rest/api/v1/providers");

		var coursesModel = new sap.ui.model.json.JSONModel();
		coursesModel.setData({});
		this.getView().setModel(coursesModel, this.MODELS.courses);

		var courseProvidersModel = this.getView().getModel(this.MODELS.courseProviders);
		if (!courseProvidersModel || courseProvidersModel.getData().length == 0) {
			sap.ui.commons.MessageBox.alert("missingRegisteredCoursesProvidersMsg".localize(), null, "alert".localize());
			this._disableFields();
		} else if (this._searchEnabled
				|| (this.getView().getModel(this.MODELS.context) && this.getView().getModel(this.MODELS.context).getData())) {
			this._enableSearchField(true);
		} else {
			this._disableFields();
		}
	},

	_asynchLoadModel : function(modelName, dataPath) {
		var model = new sap.ui.model.json.JSONModel();
		model.loadData(dataPath, null, false);
		this.getView().setModel(model, modelName);
	},

	_clearTheFields : function() {
		this.CONTROLS.courseProviderNameCombo.setValue(null);
		this._setURL(null);
		this.CONTROLS.searchField.setValue(null);
		this.CONTROLS.coursesTable.setSelectedIndex(undefined);
		this._enableOpenCourseBtn(false);
	},

	_disableFields : function() {
		this._enableSearchField(false);
		this._enableCourseProviderCombo(false);
	},

	formatCount : function(aItems) {
		return aItems && aItems.length ? "(" + aItems.length + ")" : "";
	},

	onCourseProviderSelect : function(evnt) {
		var selectedItem = evnt.getParameter("selectedItem");
		if (!selectedItem) {
			return;
		}

		var selectedCourseProvider = selectedItem.getBindingContext(this.MODELS.courseProviders).getObject();
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
		this.CONTROLS.providerUrlTextField.setValue(sUrl);
		if (training.courses.management.util.Helper.isInvalidString(sUrl)) {
			this._enableProviderOpenBtn(false);
		} else {
			this._enableProviderOpenBtn(true);
		}
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
		this._openNewTab(selectedCourseData.url);
	},

	_getSelectedItemData : function() {
		var table = this.CONTROLS.coursesTable;
		return table.getModel(this.MODELS.courses).getProperty("/" + table.getSelectedIndex());
	},

	_openNewTab : function(sUrl) {
		var win = window.open(sUrl, '_blank');
		win.focus();
	},

	onOpenProvider : function(evnt) {
		var providerUrl = this.CONTROLS.providerUrlTextField.getValue();
		if(training.courses.management.util.Helper.isInvalidString(providerUrl)){
			return
		}
		this._openNewTab(providerUrl);
	},

	onSearch : function(evnt) {
		var query = evnt.getParameter("query");
		this.CONTROLS.courseProviderNameCombo.setSelectedItemId(null);
		if (training.courses.management.util.Helper.isInvalidString(query)) {
			sap.ui.commons.MessageBox.alert("searchEmptyStringMsg".localize(), null, "error".localize());
			this._enableCourseProviderCombo(false);
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
	}

});

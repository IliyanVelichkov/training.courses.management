sap.ui.core.mvc.Controller.extend("training.courses.management.view.Courses", {

	MODELS : {
		courseProviders : "courseProviders",
		context : "context",
		courses : "courses"
	},

	onInit : function() {
		this.load();
		this._subscribeForCustomEvents();
	},

	load : function() {
		var context = training.courses.management.util.Helper.getContext();
		if (!context) {
			sap.ui.commons.MessageBox.alert("missingContextQueryParameterMsg".localize(), null, "error".localize());
			return;
		}
		this.refresh(context);
		var courseProvidersModel = this.getView().getModel(this.MODELS.courseProviders);
		if (!courseProvidersModel || courseProvidersModel.getData().length == 0) {
			sap.ui.commons.MessageBox.alert("missingRegisteredCoursesProvidersMsg".localize(), null, "alert".localize());
		} else {
			var courseProvidersCombo = this.getView().byId("courseProviderNameCombo");
			var firstItem = courseProvidersCombo.getItems()[0];
			courseProvidersCombo.setValue(firstItem.getKey());
			courseProvidersCombo.fireChange({
				"newValue" : firstItem.getKey(),
				"selectedItem" : courseProvidersCombo.getItems()[0]
			});
		}
	},

	refresh : function(context) {
		this._asynchLoadModel(this.MODELS.courseProviders, "/rest/api/v1/providers");
		this._asynchLoadModel(this.MODELS.context, "/rest/api/v1/contexts/" + context);
		var coursesModel = new sap.ui.model.json.JSONModel();
		coursesModel.setData({});
		this.getView().setModel(coursesModel, this.MODELS.courses);
	},

	_asynchLoadModel : function(modelName, dataPath) {
		var model = new sap.ui.model.json.JSONModel();
		model.loadData(dataPath, null, false);
		this.getView().setModel(model, modelName);
	},

	_subscribeForCustomEvents : function() {
		sap.ui.getCore().getEventBus().subscribe("getFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);
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

		if (selectedCourseProvider.courses) {
			this.getView().getModel(this.MODELS.courses).setData(selectedCourseProvider.courses);
		} else {
			var courses = this._loadProviderCourses(selectedCourseProvider);
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
			sap.ui.commons.MessageBox.alert("Failed to get courses with keyword " + oKeyword.name, null, "operationFailed"
					.localize());
		}, this);

		var path = "/rest/api/v1/providers/" + providerName + "/courses?searchPhrase=" + oKeyword.name;
		return training.courses.management.util.Helper.synchGetJSON(path, successFunc, failFunc);
	},

	onAfterRendering : function() {
		this.load();
	},

	onRowChange : function(evnt) {
		var openBtn = this.getView().byId("courseOpen");
		openBtn.setEnabled(true);
	},

	onShowCourse : function(evnt) {
		var selectedCourseData = this._getSelectedItemData();
		var win = window.open(selectedCourseData.url, '_blank');
		win.focus();
	},

	_getSelectedItemData : function() {
		var table = this.byId("coursesTable");
		return table.getModel("courses").getProperty("/" + table.getSelectedIndex());
	}

});

sap.ui.core.mvc.Controller.extend("training.courses.management.view.LMSOnlineCourses", {

	MODELS : {
		courses : "courses",
		context : "context"
	},

	CONTROLS : null,

	onInit : function() {
		this._loadControls();
		this._subscribeForCustomEvents();
	},

	_loadControls : function() {
		this.CONTROLS = {
			coursesTable : this.byId("coursesTable"),
			addToLearningPlanBtn : this.byId("addToLearningPlanBtn")
		};
	},

	_subscribeForCustomEvents : function() {
		sap.ui.getCore().getEventBus().subscribe("getFinished", "finished", function() {
			this._setBusy(false);
		}, this);
	},

	onAfterRendering : function() {
		this.load();
		this._clearTheFields();
	},

	_clearTheFields : function() {
		this.CONTROLS.coursesTable.setSelectedIndex(undefined);
		this._enableAddToLearningPlanBtn(false);
	},

	load : function() {
		var context = training.courses.management.util.Helper.getContext();
		if (!context) {
			sap.ui.commons.MessageBox.alert("missingContextQueryParameterMsg".localize(), null, "alert".localize());
		} else {
			this._loadContextsModel(context);
			this.refresh(context);
		}
	},

	_loadContextsModel : function(context) {
		this._setBusy(true);

		var data = training.courses.management.util.ContextUtil.loadContexts(context);
		var ctxModel = new sap.ui.model.json.JSONModel();
		ctxModel.setData(data);
		this.getView().setModel(ctxModel, this.MODELS.context);
	},

	_setBusy : function(bValue) {
		this.getView().setBusy(bValue);
		this.getView().setBusyIndicatorDelay(500);
	},

	refresh : function(context) {
		var courses = this._loadOnlineCourses();
		training.courses.management.util.CoursesUtil.filterCourses(courses);

		var coursesModel = new sap.ui.model.json.JSONModel();
		coursesModel.setData(courses);
		this.getView().setModel(coursesModel, this.MODELS.courses);

	},

	_loadOnlineCourses : function() {
		this._setBusy(true);

		var context = this.getView().getModel(this.MODELS.context).getData();
		var aKeywords = context.keywords;
		if (aKeywords.length == 0) {
			sap.ui.commons.MessageBox.alert("missingKeywords".localize(), null, "error".localize());
			return {};
		}

		var courses = [];
		for (var idx = 0; idx < aKeywords.length; idx++) {
			var foundCourses = this._searchCourses(aKeywords[idx]);
			if (foundCourses) {
				courses = courses.concat(foundCourses);
			}
		}
		return courses;
	},

	_searchCourses : function(oKeyword) {
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

		var path = "/rest/api/v1/LMS/onlineCourses?searchPhrase=" + oKeyword.name;
		return training.courses.management.util.Helper.synchGetJSON(path, successFunc, failFunc);
	},

	onRowChange : function(evnt) {
		this._enableAddToLearningPlanBtn(true);
	},

	_enableAddToLearningPlanBtn : function(bEnable) {
		this.CONTROLS.addToLearningPlanBtn.setEnabled(bEnable);
	},

	onAddCourse : function(evnt) {
		var selectedCourse = this._getSelectedItemData();
		this._addCourseToLearningPlan(selectedCourse);
	},

	_getSelectedItemData : function() {
		var table = this.CONTROLS.coursesTable;
		return table.getModel(this.MODELS.courses).getProperty("/" + table.getSelectedIndex());
	},

	_addCourseToLearningPlan : function(course) {
		var successFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("successfulAddingCourseToLearningPlan".localize(), null, "operationSuccess"
					.localize());
			this.refresh();
		}, this);

		var failFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox
					.alert("failedAddingCourseToLearningPlan".localize(), null, "operationFailed".localize());
		}, this);

		training.courses.management.util.Helper.postJson("/rest/api/v1/LMS/learningPlanCourses", course, successFunc,
				failFunc);
	}

});
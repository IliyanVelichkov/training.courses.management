sap.ui.core.mvc.Controller.extend("training.courses.management.view.Courses", {

	onInit : function() {
		var model = new sap.ui.model.json.JSONModel();
		this.getView().setModel(model, "courses");

	},

	onRowChange : function(evnt) {

	},

	formatCount : function(aItems) {
		return aItems && aItems.length ? "(" + aItems.length + ")" : "";
	},

	onShowCourse : function(evnt) {

	}

});

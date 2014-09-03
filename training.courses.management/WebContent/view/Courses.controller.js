sap.ui.core.mvc.Controller.extend("training.courses.management.view.Courses", {

	onInit : function() {
		var context = training.courses.management.util.Helper.getContext();
		if (!context) {
			sap.ui.commons.MessageBox.alert("missingContextQueryParameterMsg".localize(), null, "error".localize());
		}

		var model = new sap.ui.model.json.JSONModel();
		this.getView().setModel(model, "courses");

	},

	onAfterRendering : function() {

	},

	onRowChange : function(evnt) {

	},

	formatCount : function(aItems) {
		return aItems && aItems.length ? "(" + aItems.length + ")" : "";
	},

	onShowCourse : function(evnt) {

	}

});

sap.ui.core.mvc.Controller.extend("training.courses.management.view.Courses", {

	onInit : function() {
		var model = new sap.ui.model.json.JSONModel();
		this.getView().setModel(model, "positions");

	}

});

sap.ui.core.mvc.Controller.extend("training.courses.management.view.Favorites", {

	onInit : function() {
		var model = new sap.ui.model.json.JSONModel();
		this.getView().setModel(model, "draft");
	}

});
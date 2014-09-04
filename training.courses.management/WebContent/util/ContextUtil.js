jQuery.sap.declare("training.courses.management.util.ContextUtil");

jQuery.sap.require("sap.ui.core.format.DateFormat");

training.courses.management.util.ContextUtil = {

	loadContexts : function(context) {
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

		return training.courses.management.util.Helper.synchGetJSON("/rest/api/v1/contexts/" + context, successFunc,
				failFunc);
	},
};

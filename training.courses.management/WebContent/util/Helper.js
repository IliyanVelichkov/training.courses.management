jQuery.sap.declare("training.courses.management.util.Helper");

jQuery.sap.require("sap.ui.core.format.DateFormat");

training.courses.management.util.Helper = {

	formatDate : function(sDate) {
		var inputDateFormatter = sap.ui.core.format.DateFormat.getDateTimeInstance({
			pattern : "yyyy-MM-dd HH:mm:ss.SSS"
		});

		var outputDateFormatter = sap.ui.core.format.DateFormat.getDateInstance({
			style : "medium"
		});

		var oDate = inputDateFormatter.parse(sDate);
		return oDate && outputDateFormatter.format(oDate);
	},

	httpDelete : function(path, fSuccess, fFail) {
		$.ajax({
			type : "DELETE",
			url : path,
			success : fSuccess
		}).fail(fFail).always(function(arg0, textStatus, arg2) {
			sap.ui.getCore().getEventBus().publish("deleteFinished", "finished");
		});
	},

	postJson : function(path, oData, fSuccess, fFail) {
		$.ajax({
			type : "POST",
			url : path,
			data : JSON.stringify(oData),
			success : fSuccess,
			contentType : "application/json; charset=utf-8"
		}).fail(fFail).always(function(arg0, textStatus, arg2) {
			sap.ui.getCore().getEventBus().publish("postFinished", "finished");
		});
	},

	putJson : function(path, oData, fSuccess, fFail) {
		$.ajax({
			type : "PUT",
			url : path,
			data : JSON.stringify(oData),
			success : fSuccess,
			contentType : "application/json; charset=utf-8"
		}).fail(fFail).always(function(arg0, textStatus, arg2) {
			sap.ui.getCore().getEventBus().publish("putFinished", "finished");
		});
	},

};

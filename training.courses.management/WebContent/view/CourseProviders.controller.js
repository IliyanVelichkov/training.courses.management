sap.ui.core.mvc.Controller.extend("training.courses.management.view.CourseProviders", {

	MODELS : {
		courseProvider : "courseProvider",
		courseProviders : "courseProviders"
	},

	CONTROLS : null,

	onInit : function() {
		this._loadControls();
		this._attachCustomEvents();
		this.refresh();
		this._subscribeForCustomEvents();
	},

	_loadControls : function() {
		this.CONTROLS = {
			arrayResultTypeBtn : this.byId("arrayResultTypeBtn"),
			objectResultTypeBtn : this.byId("objectResultTypeBtn"),
			courseProviderNameCombo : this.byId("courseProviderNameCombo"),
			saveProviderBtn : this.byId("saveProviderBtn"),
			deleteProviderBtn : this.byId("deleteProviderBtn"),
			resultFieldTypeSegmButtons : this.byId("resultFieldTypeSegmButtons"),
		};
	},

	_attachCustomEvents : function() {
		this.CONTROLS.courseProviderNameCombo.attachLiveChange(jQuery.proxy(this.onCourseProviderNameTyping, this));
	},

	refresh : function() {
		this._loadModel(this.MODELS.courseProvider, "/model/courseProvider.json");
		this._loadModel(this.MODELS.courseProviders, "/rest/api/v1/providers");
		this._enableDeleteBtn(false);
		this._enableSaveBtn(false);
		this.CONTROLS.resultFieldTypeSegmButtons.setSelectedButton(undefined);
	},

	_loadModel : function(modelName, dataPath) {
		var model = new sap.ui.model.json.JSONModel();
		model.loadData(dataPath, null, false);
		this.getView().setModel(model, modelName);
	},

	_subscribeForCustomEvents : function() {
		sap.ui.getCore().getEventBus().subscribe("deleteFinished", "finished", function() {
			this.refresh();
			this.getView().setBusy(false);
		}, this);

		sap.ui.getCore().getEventBus().subscribe("postFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);

		sap.ui.getCore().getEventBus().subscribe("putFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);
	},

	onAfterRendering : function() {
		// this.refresh();
	},

	onCourseProviderSelect : function(evnt) {
		var selectedItem = evnt.getParameter("selectedItem");
		if (!selectedItem) {
			return;
		}
		var selectedCourseProvider = selectedItem.getBindingContext("courseProviders").getObject();
		var copiedObject = {};
		jQuery.extend(copiedObject, selectedCourseProvider);
		this.getView().getModel(this.MODELS.courseProvider).setData(copiedObject);
		this._enableDeleteBtn(true);
		this._enableSaveBtn(true);

		switch (copiedObject.searchResultParserCfg.resultFieldType) {
			case "ARRAY" :
				this.getView().byId("resultFieldTypeSegmButtons").setSelectedButton(this.CONTROLS.arrayResultTypeBtn);
				this.CONTROLS.arrayResultTypeBtn.firePress();
				break;
			case "OBJECT" :
				this.getView().byId("resultFieldTypeSegmButtons").setSelectedButton(this.CONTROLS.objectResultTypeBtn);
				this.CONTROLS.objectResultTypeBtn.firePress();
				break;
			default :
				console.log("Invalid result field type " + copiedObject.searchResultParserCfg.resultFieldType);
		}
	},

	showWarning : function(newValue) {
		return newValue == null || newValue == undefined ? true : !newValue.trim();
	},

	onArrayResultTypeBtnPress : function(evnt) {
		var oView = sap.ui.getCore().byId("AdministrationId--providersView");
		oView.getModel(oView.getController().MODELS.courseProvider).setProperty("/searchResultParserCfg/resultFieldType",
				"ARRAY");
	},

	onObjectResultTypeBtnPress : function(evnt) {
		var oView = sap.ui.getCore().byId("AdministrationId--providersView");
		oView.getModel(oView.getController().MODELS.courseProvider).setProperty("/searchResultParserCfg/resultFieldType",
				"OBJECT");
	},

	onCourseProviderNameTyping : function(evnt) {
		var liveValue = evnt.getParameter("liveValue");
		var existingProviderItem = this._getExistingProviderItem(liveValue);
		if (existingProviderItem) {
			// this.CONTROLS.courseProviderNameCombo.fireChange({
			// "newValue" : liveValue,
			// "selectedItem" : existingProviderItem
			// });
			this._enableDeleteBtn(true);
		} else {
			this._enableDeleteBtn(false);
			if (this._isInvalidString(liveValue)) {
				this._enableSaveBtn(false);
			}
		}
	},

	_getExistingProviderItem : function(key) {
		var providers = this.CONTROLS.courseProviderNameCombo.getItems();
		for ( var field in providers) {
			var provider = providers[field];
			if (provider.getKey() == key) {
				return provider;
			}
		}
		return null;
	},

	_enableDeleteBtn : function(bEnabled) {
		this.CONTROLS.deleteProviderBtn.setEnabled(bEnabled);
	},

	_enableSaveBtn : function(bEnabled) {
		this.CONTROLS.saveProviderBtn.setEnabled(bEnabled);
	},

	determineSaveBtnState : function() {
		for ( var idx in arguments) {
			var value = arguments[idx];
			if (this._isInvalidString(value)) {
				return false;
			}
		}
		return true;
	},

	_isInvalidString : function(value) {
		return value == null || value == undefined ? true : !value.trim();
	},

	onSaveCourseProvider : function(evnt) {
		this.getView().setBusy(true);

		var selectedKey = this.CONTROLS.courseProviderNameCombo.getSelectedKey();
		var comboValue = this.CONTROLS.courseProviderNameCombo.getValue();
		var body = this.getView().getModel(this.MODELS.courseProvider).getData();
		if (selectedKey == comboValue) {
			this._putCourseProvider(body);
		} else {
			this._postCourseProvider(body);
		}
	},

	_putCourseProvider : function(oBody) {
		var successFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("successfulCourseProviderPut".localize(), null, "operationSuccess".localize());
			this.refresh();
		}, this);

		var failFunc = jQuery.proxy(function(oResponseData) {
			if (oResponseData.status == 400) {
				var aErrors = oResponseData.responseJSON.message;
				sap.ui.commons.MessageBox.alert(this._createErrorsMessage(aErrors), null, "operationFailed".localize());
			} else {
				sap.ui.commons.MessageBox.alert("failedCourseProviderPut".localize(), null, "operationFailed".localize());
			}
		}, this);

		training.courses.management.util.Helper.putJson("/rest/api/v1/providers/" + oBody.name, oBody, successFunc,
				failFunc);
	},

	_postCourseProvider : function(oBody) {
		var successFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("successfulCourseProviderPost".localize(), null, "operationSuccess".localize());
			this.refresh();
		}, this);

		var failFunc = jQuery.proxy(function(oResponseData) {
			if (oResponseData.status == 400) {
				var aErrors = oResponseData.responseJSON.message;
				sap.ui.commons.MessageBox.alert(this._createErrorsMessage(aErrors), null, "operationFailed".localize());
			} else {
				sap.ui.commons.MessageBox.alert("failedCourseProviderPost".localize(), null, "operationFailed".localize());
			}
			sap.ui.getCore().getEventBus().publish("postFinished", "failed");
		}, this);

		training.courses.management.util.Helper.postJson("/rest/api/v1/providers", oBody, successFunc, failFunc);
	},

	_createErrorsMessage : function(aErrors) {
		var errMsg = "Invalid course provider configuration. Errors: ";
		for (var idx = 0; idx < aErrors.length; idx++) {
			errMsg += (idx + 1) + ". " + aErrors[idx];
		}
		errMsg = errMsg.replace("{", "\\{");
		errMsg = errMsg.replace("}", "\\}");
		return errMsg;
	},

	onDeleteCourseProvider : function(evnt) {
		this.getView().setBusy(true);
		var courseProviderName = this.CONTROLS.courseProviderNameCombo.getSelectedKey();
		training.courses.management.util.Helper.httpDelete("/rest/api/v1/providers/" + courseProviderName, jQuery.proxy(
				function(oResponseData) {
					sap.ui.commons.MessageBox.alert("successfulCourseProviderDeletion".localize(), null, "operationSuccess"
							.localize());
					this.refresh();
				}, this), jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("failedCourseProviderDeletion".localize(), null, "operationFailed".localize());
		}, this));

	},

	onLiveChangeDetermineSaveBtnState : function(evnt) {
		var liveValue = evnt.getParameter("liveValue");
		evnt.getSource().updateBindings();
		if (this._isInvalidString(liveValue)) {
			this._enableSaveBtn(false);
		}
	}

});
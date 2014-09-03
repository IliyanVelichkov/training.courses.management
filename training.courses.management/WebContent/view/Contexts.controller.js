sap.ui.core.mvc.Controller.extend("training.courses.management.view.Contexts", {

	MODELS : {
		contexts : "contexts"
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
			saveBtn : this.byId("saveContextBtn"),
			deleteBtn : this.byId("contextDeleteBtn"),
			contextCombo : this.byId("contextsCombo"),
			keywordsTextField : this.byId("keywordsTextField"),
			keywordsWarningImg : this.byId("keywordsWarningImage"),
			contextWarningImg : this.byId("contextWarningImage")
		};
	},

	_attachCustomEvents : function() {
		this.CONTROLS.contextCombo.attachLiveChange(jQuery.proxy(this.onContextTyping, this));
		this.CONTROLS.keywordsTextField.attachLiveChange(jQuery.proxy(this.onKeywordsTyping, this));
	},

	refresh : function() {
		var model = new sap.ui.model.json.JSONModel();
		model.loadData("/rest/api/v1/contexts", null, false);
		this.getView().setModel(model, this.MODELS.contexts);

		this.CONTROLS.contextCombo.setValue(null);
		this._displayWarningContext(null);

		this.CONTROLS.keywordsTextField.setValue(null);
		this._displayWarningKeywords(null);

		this._enableDeleteButton(false);
		this._enableSaveButton(false);
	},

	_subscribeForCustomEvents : function() {
		sap.ui.getCore().getEventBus().subscribe("deleteFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);

		sap.ui.getCore().getEventBus().subscribe("postFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);

		sap.ui.getCore().getEventBus().subscribe("putFinished", "finished", function() {
			this.getView().setBusy(false);
		}, this);
	},

	onContextSelect : function(evnt) {
		var selectedItem = evnt.getParameter("selectedItem");
		if (selectedItem) {
			this._fillKeywords(selectedItem);
			this._enableDeleteButton(true);
			this._enableSaveButton(true);
		}
	},

	_fillKeywords : function(selectedItem) {
		var keywords = selectedItem.getBindingContext("contexts").getProperty("keywords");
		var value = "";
		keywords.forEach(function(keyword) {
			value += keyword.name + ", ";
		});
		value = value.substring(0, value.length - 2);
		this._setKeywordsTxtFieldValue(value);
	},

	_setKeywordsTxtFieldValue : function(value) {
		this.CONTROLS.keywordsTextField.setValue(value);
		this.CONTROLS.keywordsTextField.fireLiveChange({
			liveValue : value
		});
	},

	_enableDeleteButton : function(bValue) {
		this.CONTROLS.deleteBtn.setEnabled(bValue);
	},

	_enableSaveButton : function(bValue) {
		this.CONTROLS.saveBtn.setEnabled(bValue);
	},

	onContextTyping : function(evnt) {
		var liveValue = evnt.getParameter("liveValue");
		var isContextExists = this._isContextExists(liveValue);
		// this._setApprKeywords(isContextExists, liveValue);
		this._determineSaveBtnState(liveValue);
		this._enableDeleteButton(isContextExists);
		this._displayWarningContext(liveValue);
	},

	_isContextExists : function(key) {
		var contexts = this.CONTROLS.contextCombo.getItems();
		var isExists = false;
		for ( var field in contexts) {
			var context = contexts[field];
			if (context.getKey() == key) {
				isExists = true;
				break;
			}
		}
		return isExists;
	},

	_setApprKeywords : function(isContextExists, value) {
		if (isContextExists) {
			var item = this._getItemWithKey(value);
			this._fillKeywords(item);
		}
	},

	_getItemWithKey : function(key) {
		var items = this.CONTROLS.contextCombo.getItems();
		var searchedItem = undefined;
		items.forEach(function(item) {
			if (item.getKey() == key) {
				searchedItem = item;
			}
		});
		return searchedItem;
	},

	_displayWarningContext : function(bValue) {
		if (bValue) {
			this.CONTROLS.contextWarningImg.addStyleClass("displayNone");
		} else {
			this.CONTROLS.contextWarningImg.removeStyleClass("displayNone");
		}
	},

	onKeywordsTyping : function(evnt) {
		var liveValue = evnt.getParameter("liveValue");
		this._determineSaveBtnState(null, liveValue);
		this._displayWarningKeywords(liveValue);
	},

	_determineSaveBtnState : function(newContextValue, newKeywordsValue) {
		var contextValue = newContextValue ? newContextValue : this.CONTROLS.contextCombo.getValue();
		var keywordsValue = newKeywordsValue ? newKeywordsValue : keywordsValue = this.CONTROLS.keywordsTextField
				.getValue();

		this._enableSaveButton(!!contextValue && !!keywordsValue);
	},

	_displayWarningKeywords : function(bValue) {
		if (bValue) {
			this.CONTROLS.keywordsWarningImg.addStyleClass("displayNone");
		} else {
			this.CONTROLS.keywordsWarningImg.removeStyleClass("displayNone");
		}
	},

	onDeleteContext : function() {
		this.getView().setBusy(true);
		var context = this.CONTROLS.contextCombo.getSelectedKey();
		training.courses.management.util.Helper.httpDelete("/rest/api/v1/contexts/" + context, jQuery.proxy(function(
				oResponseData) {
			sap.ui.commons.MessageBox.alert("successfulContextDeletion".localize(), null, "operationSuccess".localize());
			this.refresh();
		}, this), jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("failedContextDeletion".localize(), null, "operationFailed".localize());
		}, this));
	},

	onSaveContext : function() {
		this.getView().setBusy(true);

		var keywordsStr = this.CONTROLS.keywordsTextField.getValue();
		var cleanKeywords = this._getCleanedKeywords(keywordsStr);
		if (cleanKeywords.length == 0) {
			sap.ui.commons.MessageBox.alert("invalidKeywordsFormat".localize(), null, "invalidKeywords".localize());
		}

		var selectedKey = this.CONTROLS.contextCombo.getSelectedKey();
		var comboValue = this.CONTROLS.contextCombo.getValue();
		if (selectedKey == comboValue) {
			this._putContext(selectedKey, cleanKeywords);
		} else {
			this._postContext(comboValue, cleanKeywords);
		}
	},

	_getCleanedKeywords : function(sKeywords) {
		var aTrimedKeywords = jQuery.map(sKeywords.split(","), jQuery.trim);
		var cleanKeywords = [];
		for ( var idx in aTrimedKeywords) {
			if (aTrimedKeywords[idx]) {
				cleanKeywords.push(aTrimedKeywords[idx]);
			}
		}
		return cleanKeywords;
	},

	_putContext : function(contextName, aKeywords) {
		var successFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("successfulContextPut".localize(), null, "operationSuccess".localize());
			this.refresh();
		}, this);

		var failFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("failedContextPut".localize(), null, "operationFailed".localize());
		}, this);

		training.courses.management.util.Helper.putJson("/rest/api/v1/contexts/" + contextName.trim() + "/" + "keywords",
				this._createKeywordObjects(aKeywords), successFunc, failFunc);
	},

	_postContext : function(contextName, aKeywords) {
		var body = this._createBody(contextName, aKeywords);

		var successFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("successfulContextPost".localize(), null, "operationSuccess".localize());
			this.refresh();
		}, this);

		var failFunc = jQuery.proxy(function(oResponseData) {
			sap.ui.commons.MessageBox.alert("failedContextPost".localize(), null, "operationFailed".localize());
		}, this);

		training.courses.management.util.Helper.postJson("/rest/api/v1/contexts", body, successFunc, failFunc);
	},

	_createBody : function(contextName, aKeywords) {
		return {
			name : contextName.trim(),
			keywords : this._createKeywordObjects(aKeywords)
		};
	},

	_createKeywordObjects : function(aKeywordNames) {
		var keywordObject = [];
		for ( var field in aKeywordNames) {
			keywordObject.push({
				name : aKeywordNames[field]
			});
		}
		return keywordObject;
	}

});
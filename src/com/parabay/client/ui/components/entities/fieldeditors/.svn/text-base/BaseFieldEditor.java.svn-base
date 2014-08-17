package com.parabay.client.ui.components.entities.fieldeditors;

import java.util.Map;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.api.Globals;
import com.parabay.client.api.ui.IFieldEditor;
import com.parabay.client.ui.components.PanelLabel;
import com.parabay.client.ui.components.entities.EntityEditor;
import com.parabay.client.ui.validation.client.ErrorHandler;
import com.parabay.client.ui.validation.client.Subject;
import com.parabay.client.ui.validation.client.Validator;
import com.parabay.client.ui.validation.client.ValidatorController;
import com.parabay.client.utils.SharedUtils;

public class BaseFieldEditor extends ErrorHandler implements IFieldEditor {

	protected Map<String, Object> field;
	protected EntityEditor entityEditor;
	protected Label labelError;
	
	protected ValidatorController validatorController;
	protected Subject validatorTarget;
	
	protected String fieldName = "";
	protected Object oldValue = "";
	protected boolean hasError = false;
	
	public BaseFieldEditor(Map<String, Object> field, EntityEditor entityEditor) {
		super();
		this.entityEditor = entityEditor;
		this.field = field;
		
		this.validatorController = new ValidatorController();
		this.validatorController.setErrorHandler(this);
	}
	
	protected void addControls(ComplexPanel panel) {
		
	}
	
	protected void setValue(String value) {
		this.oldValue = value;
	}
	
	protected Object getValue() {
		return this.oldValue;
	}

	protected Subject getValidatorSubject() {
		return null;
	}
	
	protected FocusWidget getTargetControl() {
		return null;
	}
	
	protected void addValidators() {

		validatorTarget = this.getValidatorSubject();
		
		if (null != validatorTarget) {
			
			Object [] validators = (Object [])this.field.get("validators");
			if (null != validators) {
				for(Object v : validators) {
					Map<String, Object> valObj = (Map<String, Object>)v;
					
					Validator validator = Globals.getInstance().getValidator(valObj);
					
				    validatorController.addSubject(validatorTarget);
				    validatorController.addValidator(validator);				    				    
				}
			}
			
			if (null != this.getTargetControl()) {
				this.getTargetControl().addFocusListener(validatorController);
			}
		}
	}
		
	public Widget toScreen(Map<String, Object> item) {
		
		Map<String, Object> params = (Map<String, Object>) field.get("params");
		
		fieldName = SharedUtils.getStringValue(params, "data");
		oldValue = item.get(fieldName);
		
		FlowPanel hPanel = new FlowPanel();
		hPanel.setStyleName("EditorRow");
		Label labelName = new Label();
		labelName.setStyleName("InputLabel");
		labelName.setText(fieldName + ": ");
		hPanel.add(labelName);
				
		this.addControls(hPanel);
		
		labelError = new Label();
		labelError.setText("");
		labelError.setVisible(false);
		labelError.setStyleName("InputError");
		hPanel.add(labelError);
		
		this.addValidators();
		this.reportError(null);
		
		return hPanel;
	}
		
	public void fromScreen(Map<String, Object> item) {
		
		item.put(fieldName, this.getValue());
	}
	
	public boolean validate() {
		
		validatorController.validate();
		return (!this.hasError);
	}
	
	public void reportError(String errorMessage) {
		
		this.labelError.setText(errorMessage);
		
		if (null != errorMessage && errorMessage.length() > 0) {
			this.labelError.setVisible(true);
			this.hasError = true;
		}
		else {
			this.labelError.setVisible(false);
			this.hasError = false;
		}
	}	
}

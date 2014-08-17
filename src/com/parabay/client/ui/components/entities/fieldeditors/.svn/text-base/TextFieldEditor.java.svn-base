package com.parabay.client.ui.components.entities.fieldeditors;

import java.util.Map;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.parabay.client.ui.components.entities.EntityEditor;
import com.parabay.client.ui.validation.client.DefaultTextBoxSubject;
import com.parabay.client.ui.validation.client.Subject;

public class TextFieldEditor extends BaseFieldEditor {

	protected TextBox textBoxValue;
	
	public TextFieldEditor(Map<String, Object> field, EntityEditor entityEditor) {
		super(field, entityEditor);
	}

	@Override
	protected void addControls(ComplexPanel panel) {

		textBoxValue = new TextBox();
		textBoxValue.setStyleName("InputBox");
		textBoxValue.setText((String) oldValue);
		panel.add(textBoxValue);	
	}

	@Override
	protected Object getValue() {
		return textBoxValue.getText();
	}
	
	@Override
	protected void setValue(String value) {
		textBoxValue.setText(value);
	}
	
	@Override
	protected Subject getValidatorSubject() {
		return new DefaultTextBoxSubject(textBoxValue);
	}
	
	@Override
	protected FocusWidget getTargetControl() {
		return this.textBoxValue;
	}	
}

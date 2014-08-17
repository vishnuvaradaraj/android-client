package com.parabay.client.ui.components.entities.fieldeditors;

import java.util.Map;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.parabay.client.ui.components.entities.EntityEditor;

public class BooleanFieldEditor extends BaseFieldEditor {

	private CheckBox checkBox;
	
	public BooleanFieldEditor(Map<String, Object> field,
			EntityEditor entityEditor) {
		super(field, entityEditor);
	}

	@Override
	protected void addControls(ComplexPanel panel) {
		checkBox = new CheckBox();
		panel.add(checkBox);
	}

	@Override
	protected Object getValue() {
		return checkBox.getText();
	}

}

package com.parabay.client.ui.components.entities.fieldeditors;

import java.util.Map;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.widgetideas.datepicker.client.DateBox;
import com.parabay.client.ui.components.entities.EntityEditor;

public class DateFieldEditor extends BaseFieldEditor {

	DateBox p;
	
	public DateFieldEditor(Map<String, Object> field, EntityEditor entityEditor) {
		super(field, entityEditor);
	}
	
	@Override
	protected void addControls(ComplexPanel panel) {

		p = new DateBox();
		panel.add(p);	
	}

	@Override
	protected Object getValue() {
		return p.getDatePicker().getDateShown().toLocaleString();
	}	
}

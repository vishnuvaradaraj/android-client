package com.parabay.client.ui.components;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class FieldGroupPanel extends ComplexPanel {

  public FieldGroupPanel() {
    setElement(DOM.createFieldSet());
  }

  @Override
  public void add(Widget w) {
    add(w, getElement());
  }

  public void insert(Widget w, int beforeIndex) {
    insert(w, getElement(), beforeIndex, true);
  }
}

package com.parabay.client.ui.components;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.ui.resources.Resources;

public class LocalePicker {
	
	public LocalePicker() {
		super();
	}

	private static native String getHostPageLocation()
	/*-{
	  var s = $doc.location.href;
	
	  // Pull off any hash.
	  var i = s.indexOf('#');
	  if (i != -1)
	    s = s.substring(0, i);
	
	  // Pull off any query string.
	  i = s.indexOf('?');
	  if (i != -1)
	    s = s.substring(0, i);
	
	  // Ensure a final slash if non-empty.
	  return s;
	}-*/;

	public Widget initialize() {

		// Add the option to change the locale
		final ListBox localeBox = new ListBox();
		String currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (currentLocale.equals("default")) {
			currentLocale = "en";
		}
		String[] localeNames = LocaleInfo.getAvailableLocaleNames();
		for (String localeName : localeNames) {
			if (!localeName.equals("default")) {
				String nativeName = LocaleInfo
						.getLocaleNativeDisplayName(localeName);
				localeBox.addItem(nativeName, localeName);
				if (localeName.equals(currentLocale)) {
					localeBox.setSelectedIndex(localeBox.getItemCount() - 1);
				}
			}
		}
		localeBox.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				String localeName = localeBox.getValue(localeBox
						.getSelectedIndex());
				Window.open(getHostPageLocation() + "?locale=" + localeName + "#settings",
						"_self", "");
			}
		});
		HorizontalPanel localeWrapper = new HorizontalPanel();
		
		Label label = new Label();
		label.setText("Select language:");
		localeWrapper.add(label);

		localeWrapper.add(Resources.INSTANCE.locale().createImage());
		localeWrapper.add(localeBox);
		return localeWrapper;

	}
}

package com.parabay.client.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.ui.resources.StyleSheetLoader;
import com.parabay.client.utils.Constants;

public class ThemePicker {

	private Widget parent;

	public static String CUR_THEME = Constants.STYLE_THEMES[0];

	/**
	 * A special version of the ToggleButton that cannot be clicked if down. If
	 * one theme button is pressed, all of the others are depressed.
	 */
	private static class ThemeButton extends ToggleButton {

		private static List<ThemeButton> allButtons = null;

		private String theme;

		public ThemeButton(String theme) {
			super();
			this.theme = theme;
			addStyleName("sc-ThemeButton-" + theme);

			// Add this button to the static list
			if (allButtons == null) {
				allButtons = new ArrayList<ThemeButton>();
				setDown(true);
			}
			allButtons.add(this);
		}

		public String getTheme() {
			return theme;
		}

		@Override
		protected void onClick() {
			if (!isDown()) {
				// Raise all of the other buttons
				for (ThemeButton button : allButtons) {
					if (button != this) {
						button.setDown(false);
					}
				}

				// Fire the click listeners
				super.onClick();
			}
		}
	}

	public ThemePicker(Widget parent) {
		super();
		this.parent = parent;
	}

	public Widget initialize() {
		
		final HorizontalPanel styleWrapper = new HorizontalPanel();
		styleWrapper.setStyleName(Constants.DEFAULT_STYLE_NAME + "-options");
		
		Label label = new Label();
		label.setText("Select theme:");
		styleWrapper.add(label);
		
		for (int i = 0; i < Constants.STYLE_THEMES.length; i++) {
			
			final ThemeButton button = new ThemeButton(
					Constants.STYLE_THEMES[i]);
			styleWrapper.add(button);
			
			button.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					// Update the current theme
					CUR_THEME = button.getTheme();

					// Load the new style sheets
					updateStyleSheets();
				}
			});
		}
		
		return styleWrapper;
	}

	private void updateStyleSheets() {
		// Generate the names of the style sheets to include
		String gwtStyleSheet = "gwt/" + CUR_THEME + "/" + CUR_THEME + ".css";
		String showcaseStyleSheet = CUR_THEME + "/Mobile.css";
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			gwtStyleSheet = gwtStyleSheet.replace(".css", "_rtl.css");
			showcaseStyleSheet = showcaseStyleSheet.replace(".css", "_rtl.css");
		}

		// Find existing style sheets that need to be removed
		boolean styleSheetsFound = false;
		final HeadElement headElem = StyleSheetLoader.getHeadElement();
		final List<Element> toRemove = new ArrayList<Element>();
		NodeList<Node> children = headElem.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.getItem(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = Element.as(node);
				if (elem.getTagName().equalsIgnoreCase("link")
						&& elem.getPropertyString("rel").equalsIgnoreCase(
								"stylesheet")) {
					styleSheetsFound = true;
					String href = elem.getPropertyString("href");
					// If the correct style sheets are already loaded, then we
					// should have
					// nothing to remove.
					if (!href.contains(gwtStyleSheet)
							&& !href.contains(showcaseStyleSheet)) {
						toRemove.add(elem);
					}
				}
			}
		}

		// Return if we already have the correct style sheets
		if (styleSheetsFound && toRemove.size() == 0) {
			return;
		}

		// Detach the app while we manipulate the styles to avoid rendering
		// issues
		RootPanel.get().remove(this.parent);

		// Remove the old style sheets
		for (Element elem : toRemove) {
			headElem.removeChild(elem);
		}

		// Load the GWT theme style sheet
		String modulePath = GWT.getModuleBaseURL();
		Command callback = new Command() {
			/**
			 * The number of style sheets that have been loaded and executed
			 * this command.
			 */
			private int numStyleSheetsLoaded = 0;

			public void execute() {
				// Wait until all style sheets have loaded before re-attaching
				// the app
				numStyleSheetsLoaded++;
				if (numStyleSheetsLoaded < 2) {
					return;
				}

				// Different themes use different background colors for the body
				// element, but IE only changes the background of the visible
				// content
				// on the page instead of changing the background color of the
				// entire
				// page. By changing the display style on the body element, we
				// force
				// IE to redraw the background correctly.
				RootPanel.getBodyElement().getStyle().setProperty("display",
						"none");
				RootPanel.getBodyElement().getStyle()
						.setProperty("display", "");
				RootPanel.get().add(parent);
			}
		};
		StyleSheetLoader.loadStyleSheet(modulePath + gwtStyleSheet,
				getCurrentReferenceStyleName("gwt"), callback);

		// Load the showcase specific style sheet after the GWT theme style
		// sheet so
		// that custom styles supercede the theme styles.
		StyleSheetLoader.loadStyleSheet(modulePath + showcaseStyleSheet,
				getCurrentReferenceStyleName("Application"), callback);
	}

	private String getCurrentReferenceStyleName(String prefix) {
		String gwtRef = prefix + "-Reference-" + CUR_THEME;
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			gwtRef += "-rtl";
		}
		return gwtRef;
	}
}

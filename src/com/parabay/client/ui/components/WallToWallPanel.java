/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.parabay.client.ui.components;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.ui.IDisplayHost;
import com.parabay.client.data.L10n;
import com.parabay.client.data.PageMetadata;
import com.parabay.client.services.wrappers.UserInfoWrapper;
import com.parabay.client.utils.Constants;

/**
 * This is the base class for all of the major UI panels. It is designed to fill
 * the full width of the client area and to have at most a single instance
 * attached to the DOM. The panel has a concept of having a parent or previous
 * panel that can be accessed by a "back" button, as well as a distinct "edit"
 * or alternate command that can be accessed by a second button in the title
 * bar. The contents of the panel should consist only of PanelLabel widgets.
 */
public abstract class WallToWallPanel extends Composite implements HasHTML, IDisplayHost {

	public static WallToWallPanel activePanel = null;

	protected final ClickListener parentClickListener = new ClickListener() {
		public void onClick(Widget w) {
			exit();
		}
	};

	private final WallToWallPanel parent;
	private final FlowPanel contents = new FlowPanel();
	private final HorizontalPanel header = new HorizontalPanel();
	private final PanelLabel panelLabel;
	private final UnsunkLabel titleLabel = new UnsunkLabel("");
	private final Label	editCommandLabel = new Label();
	
	private Command editCommand;
	private HasText hasText;
	private HasHTML hasHtml;
	private PanelLabel lastSelectedLabel;

	private String token = "";
	
	public WallToWallPanel(String title, WallToWallPanel parent) {
		this.parent = parent;
		
		Log.info("Creating panel: " + title);

		panelLabel = new PanelLabel("", new Command() {
			public void execute() {
				enter();
			}
		}) {
			public void setText(String title) {
				titleLabel.setText(title);
				super.setText(title);
			}
		};
		panelLabel.setText(title);

		header.setVerticalAlignment(HorizontalPanel.ALIGN_TOP);
		header.addStyleName("header");

		if (parent != null) {
			Label l = new Label(parent.getShortTitle());
			l.addClickListener(parentClickListener);
			l.addStyleName("button");
			l.addStyleName("backButton");
			header.add(l);
		}

		titleLabel.addStyleName("titleLabel");
		header.add(titleLabel);
		header.setCellWidth(titleLabel, "100%");
		header.setCellHorizontalAlignment(titleLabel,
				HorizontalPanel.ALIGN_CENTER);

		//add edit command placeholder
		editCommandLabel.setVisible(false);
		editCommandLabel.addStyleName("button");
		editCommandLabel.addStyleName("goButton");

		editCommandLabel.addClickListener(new ClickListener() {
			public void onClick(Widget w) {
				if (null != editCommand) {
					editCommand.execute();
				}
			}
		});
		header.add(editCommandLabel);
		header.setCellHorizontalAlignment(editCommandLabel, HorizontalPanel.ALIGN_RIGHT);
		
		FlowPanel vp = new FlowPanel();
		vp.add(header);

		contents.addStyleName("contents");
		vp.add(contents);

		initWidget(vp);
		addStyleName("SliderPanel");
	}

	public void add(PanelLabel label) {
		if ((hasText != null) || (hasHtml != null)) {
			hasText = hasHtml = null;
			contents.clear();
		}

		contents.add(label);
	}

	public void clear() {
		contents.clear();
	}

	public String getHTML() {
		return hasHtml == null ? null : hasHtml.getHTML();
	}

	public PanelLabel getLabel() {
		return panelLabel;
	}

	public String getText() {
		return hasText == null ? null : hasText.getText();
	}

	public void remove(PanelLabel label) {
		contents.remove(label);
	}

	/**
	 * Set the command to be executed by a right-justified button in the title
	 * bar.
	 * 
	 * @param label
	 *            the label for the button
	 * @param title
	 *            the title or alt-text for the button
	 * @param command
	 *            the Command to execute when the button is pressed.
	 */
	public void setEditCommand(String label, String title, Command command) {
		editCommand = command;
		editCommandLabel.setText(label);
		editCommandLabel.setTitle(title);
		editCommandLabel.setVisible(true);
	}

	public void setHTML(String html) {
		HTML h;
		hasText = hasHtml = h = new HTML(html);

		contents.clear();
		contents.add(h);
	}

	public void setText(String text) {
		UnsunkLabel l;
		hasText = l = new UnsunkLabel(text);
		hasHtml = null;

		contents.clear();
		contents.add(l);
	}

	/**
	 * Display the panel, removing any currently-displayed panel from the
	 * screen. If the panel is already displayed, calling this method again will
	 * produce no result.
	 */
	protected void enterImpl() {
		if (isAttached()) {
			return;
		}

		if (activePanel != null) {
			// Save the label to scroll to when backing into the parent panel.
			if (activePanel == parent) {
				parent.setLastSelectedLabel(getLabel());
			}

			RootPanel.get().remove(activePanel);
		}

		activePanel = WallToWallPanel.this;
		RootPanel.get().add(WallToWallPanel.this, 0, 0);

		DeferredCommand.addPause();
		DeferredCommand.addCommand(new ScrollToCommand(lastSelectedLabel));
	}

	/**
	 * Return to the parent panel.
	 */
	public void exit() {
		if (parent == null) {
			throw new RuntimeException("SliderPanel has no parent");
		}

		// When backing out, we don't want to go to our last label when the
		// panel
		// is re-entered.
		setLastSelectedLabel(null);

		parent.enter();
	}

	/**
	 * A short title to be used as the label of the back button.
	 */
	protected abstract String getShortTitle();

	/**
	 * Remember the last PanelLabel that was selected on the current panel. This
	 * is used to scroll the viewport down to the last selected panel when the
	 * WallToWallPanel is backed into.
	 */
	private void setLastSelectedLabel(PanelLabel label) {
		lastSelectedLabel = label;
	}
	
	/*Added by Parabay */
	private L10n l10nData;
	
	public String l10n(String key) {
		
		if (null == l10nData) {
			return key;
		}
		
		return l10nData.get(key);
	}
	
	private boolean hasErrors = false;
	
	protected void updateErrors(IDataEvent event) {
		
		if (event.getStatus()>0) {
			
			Log.severe("Server errror status: " + String.valueOf(event.getStatus()));
			
			if (!this.hasErrors) {
				clear();
				this.add(new PanelLabel(l10n("Failed to load data.")));
			}
			
			this.hasErrors = true;
		}
	}
	
	private Map<String, Boolean> pendingDataLoads = new HashMap<String, Boolean>();
	
	public void startDataLoad(String name) {
		
		if (this.pendingDataLoads.isEmpty()) {
			PanelLabel label = new PanelLabel(l10n("Loading..."));
			label.setBusy(true);
			this.add(label);
		}
		
		this.pendingDataLoads.put(name, true);
	}
	
	public void endDataLoad(IDataEvent event) {
		this.pendingDataLoads.remove(event.getType());
		
		this.updateErrors(event);
		
		if (!this.hasErrors && this.pendingDataLoads.isEmpty()) {
			clear();
			refresh();
		}
		else {
			Log.info("endDataLoad: Not refreshing display (" + String.valueOf(this.pendingDataLoads.size()) + ")" );
		}
	}
	
	public void initialize() {
		
		this.l10nData = Globals.getInstance().getL10n(
				Globals.getInstance().getApp(), this.page, 
				Globals.getInstance().getCurrentLocale());
		
		this.startDataLoad(this.l10nData.getDataKey());
		this.l10nData.load(new IDataEventHandler() {

			public void onDataChanged(IDataEvent event) {
				endDataLoad(event);
			}
		});
	}

	private String page = null;
	
	public String getPage() {
		return this.page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	private boolean dirty = false;
	
	public void setDirty() {
		this.dirty = true;
	}
	
	public boolean checkLoggedIn(WallToWallPanel parent) {
		boolean ret = true;
		
		String token = Globals.getInstance().getToken();
		
		if (null == token || token.length()==0) {
			Globals.getInstance().getNavigationManager().navigateToPage(Constants.LOGIN);
			ret = false;
		}
		
		return ret;
	}
	
	public void enter() {
		
		if (checkLoggedIn(this)) {
			
			if (this.dirty) {
				this.dirty = false;
				
				this.clear();
				this.refresh();
			}

			enterImpl();
			
			Globals.getInstance().getNavigationManager()
				.updatePageToken(this.getToken());
		}		
	}
	
	public void reset() {
		pendingDataLoads.clear();
		this.dirty = true;
		this.hasErrors = false;
		this.clear();
	}
	
	public void refresh() {
		
	}
	
	public PageMetadata getPageMetadata() {
		return null;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void replaceStyle(String oldStyle, String newStyle) {
		this.removeStyleName(oldStyle);
		this.addStyleName(newStyle);	
	}

}

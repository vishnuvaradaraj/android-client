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

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.parabay.client.api.Globals;
import com.parabay.client.utils.Constants;

/**
 * A simple about panel.
 */
public class AboutPanel extends WallToWallPanel {
	
	public AboutPanel(WallToWallPanel parent) {
		
		super("About...", parent);
		this.setPage(Constants.ABOUT);
		
		addStyleName("AboutPanel");

		refresh();
	}

	protected String getShortTitle() {
		return "About";
	}

	@Override
	public void refresh() {
		
		UnsunkLabel logoLabel = new UnsunkLabel();
		logoLabel.addStyleName("logo");

		UnsunkLabel label = new UnsunkLabel("Parabay Client<br/>"
				+ "<div class=\"snippit\">A generic client for"
				+ "the Parabay suite of applications</div>", true);
		label.addStyleName("aboutLabel");

		FlowPanel fp = new FlowPanel();
		fp.add(logoLabel);
		fp.add(label);

		add(new PanelLabel(fp, null));
	}
}

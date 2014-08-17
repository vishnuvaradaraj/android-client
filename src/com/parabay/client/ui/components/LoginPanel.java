package com.parabay.client.ui.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.IDataEventManager;
import com.parabay.client.services.commands.LoginCommand;
import com.parabay.client.services.wrappers.UserInfoWrapper;
import com.parabay.client.ui.components.StatusBar.Type;
import com.parabay.client.utils.Constants;

public class LoginPanel extends WallToWallPanel {

	WallToWallPanel parentPanel;
	StatusBar statusBar = GWT.create(StatusBar.class);
	
	public LoginPanel(WallToWallPanel parent) {
		super("Login", parent);

		addStyleName("LoginPanel");
		this.replaceStyle("SliderPanel", "TransparentPanel");
		
		this.parentPanel = parent;
		this.setPage(Constants.LOGIN);
				
		IDataEventManager eventMgr = Globals.getInstance()
				.getDataEventManager();
		eventMgr.addHandler(Constants.USER, new IDataEventHandler() {

			public void onDataChanged(IDataEvent event) {

				UserInfoWrapper wrapper = (UserInfoWrapper)event.getValue();
				if (null != wrapper) {
					
					if (wrapper.isValid()) {
						parentPanel.enter();
					}
					else {
						statusBar.setMessage(l10n("Failed to login"));
						statusBar.setMessageType(Type.ERROR);
					}					
				}
			}
		});
		
		this.refresh();
	}

	@Override
	protected String getShortTitle() {
		return l10n("Login");
	}

	@Override
	public void refresh() {
		
		//add(new PanelLabel(statusBar, null));

		FlowPanel groupPanel = new FlowPanel();
		groupPanel.setStyleName("TransparentPanel");
		Label groupHeader = new Label();					
		groupHeader.setText("Sign in to Parabay");
		groupHeader.setStyleName("GroupHeader");
		groupPanel.add(groupHeader);
		groupPanel.add(statusBar);

		FieldGroupPanel fieldGroupPanel = new FieldGroupPanel();
		fieldGroupPanel.setStyleName("GroupPanel");
		groupPanel.add(fieldGroupPanel);
		
		FlowPanel hPanel;		
		hPanel = new FlowPanel();
		hPanel.setStyleName("EditorRow");
		Label labelName = new Label();
		labelName.setStyleName("InputLabel");
		labelName.setText("Name:");
		hPanel.add(labelName);
		final TextBox textBoxName = new TextBox();
		textBoxName.setStyleName("InputBox");
		textBoxName.setText("");
		hPanel.add(textBoxName);
		fieldGroupPanel.add(hPanel);
		
		hPanel = new FlowPanel();
		hPanel.setStyleName("EditorRow");
		Label labelPassword = new Label();
		labelPassword.setStyleName("InputLabel");
		textBoxName.setTabIndex(1);
		labelPassword.setText("Password:");
		hPanel.add(labelPassword);
		final PasswordTextBox textBoxPassword = new PasswordTextBox();
		textBoxPassword.setStyleName("InputBox");
		textBoxPassword.setText("");
		textBoxPassword.setTabIndex(2);
		hPanel.add(textBoxPassword);
		fieldGroupPanel.add(hPanel);
		
		add(new PanelLabel(groupPanel, null));
			
		Button loginButton = new Button("Login");
		loginButton.setStyleName("grayButton");
		loginButton.setTabIndex(3);
		loginButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				LoginCommand login = new LoginCommand(textBoxName.getText(),
						textBoxPassword.getText());
				login.execute();
			}
		});
		add(new PanelLabel(loginButton, null));
		
		String registerToken = Globals.getInstance().getNavigationManager()
			.getTokenForPage(Constants.REGISTER);
		Hyperlink registerLink = new Hyperlink(l10n("I don't have an account."), registerToken);
		add(new PanelLabel(registerLink, null));

	}

	@Override
	public boolean checkLoggedIn(WallToWallPanel parent) {
		return true;
	}
		
	
}

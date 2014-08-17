package com.parabay.client.ui.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.IDataEventManager;
import com.parabay.client.services.commands.LoginCommand;
import com.parabay.client.services.commands.RegisterUserCommand;
import com.parabay.client.services.wrappers.UserInfoWrapper;
import com.parabay.client.ui.components.StatusBar.Type;
import com.parabay.client.utils.Constants;

public class RegisterPanel extends WallToWallPanel {

	WallToWallPanel parentPanel;
	StatusBar statusBar = GWT.create(StatusBar.class);
	
	public RegisterPanel(WallToWallPanel parent) {
		super("Register", parent);

		addStyleName("RegisterPanel");
		this.replaceStyle("SliderPanel", "TransparentPanel");
		
		this.parentPanel = parent;
		this.setPage(Constants.REGISTER);
				
		IDataEventManager eventMgr = Globals.getInstance()
				.getDataEventManager();
		eventMgr.addHandler(Constants.USER, new IDataEventHandler() {

			public void onDataChanged(IDataEvent event) {

				UserInfoWrapper wrapper = (UserInfoWrapper)event.getValue();
				if (null != wrapper) {
					
					if (wrapper.isValid()) {
						Globals.getInstance().getNavigationManager().navigateToPage(Constants.HOME);
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
		return l10n("Register");
	}

	@Override
	public void refresh() {
		
		//add(new PanelLabel(statusBar, null));

		FlowPanel groupPanel = new FlowPanel();
		groupPanel.setStyleName("TransparentPanel");
		Label groupHeader = new Label();					
		groupHeader.setText("Register Parabay account");
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
		labelName.setText("Email:");
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
		labelPassword.setText("Password:");
		hPanel.add(labelPassword);
		final PasswordTextBox textBoxPassword = new PasswordTextBox();
		textBoxPassword.setStyleName("InputBox");
		textBoxPassword.setText("");
		hPanel.add(textBoxPassword);
		fieldGroupPanel.add(hPanel);
		
		add(new PanelLabel(groupPanel, null));
			
		Button loginButton = new Button("Register");
		loginButton.setStyleName("grayButton");
		loginButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				RegisterUserCommand register = new RegisterUserCommand(textBoxName.getText(),
						textBoxPassword.getText());
				register.execute();
			}
		});
		add(new PanelLabel(loginButton, null));
	}

	@Override
	public boolean checkLoggedIn(WallToWallPanel parent) {
		return true;
	}
		
	
}

package com.parabay.client.ui.components;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class StatusBar extends Composite{

	public enum Type {
		NONE, INFO, ERROR
	}
	
	private String	message;
	private Type messageType;
	
	private Label textBoxMessage;
	
	public StatusBar() {
		super();
		this.message = "";
		this.refresh();
	}

	public void refresh() {
		
		FlowPanel vp = new FlowPanel();
		
		textBoxMessage = new Label();
		textBoxMessage.setText(this.message);
		textBoxMessage.setStyleName("StatusMessage");

		vp.add(textBoxMessage);
		initWidget(vp);

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		this.textBoxMessage.setText(this.message);
	}

	public Type getMessageType() {
		return messageType;
	}

	public void setMessageType(Type messageType) {
		this.messageType = messageType;
	}
}

package com.parabay.client.api.ui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.data.PageMetadata;
import com.parabay.client.ui.components.PanelLabel;

public interface IDisplayHost {

	public abstract void add(PanelLabel label);

	public abstract void clear();

	public abstract void remove(PanelLabel label);

	public abstract String l10n(String key);

	public abstract void startDataLoad(String name);

	public abstract void endDataLoad(IDataEvent event);

	public abstract void setDirty();

	public abstract void reset();

	public abstract void refresh();

	public abstract String getPage();
	public abstract void setEditCommand(String label, String title, Command command);
	public abstract PageMetadata getPageMetadata();
	
	public void exit();
	public void enter();
	
	public String getToken();
	public void setToken(String token);
	
	public PanelLabel getLabel();
	public void replaceStyle(String oldStyle, String newStyle);
}
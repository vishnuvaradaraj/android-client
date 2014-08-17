package com.parabay.client.api.ui;


public interface IPageTypeManager {

	public abstract IDisplayComponent createPage(String type,
			IDisplayHost parent);

}
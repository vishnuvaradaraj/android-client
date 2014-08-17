package com.parabay.client.utils;

import com.parabay.client.api.services.IServiceRequestHandler;

public class JSONRequest 
{
	public static void get( String url, IServiceRequestHandler handler )
	{
		String callbackName = "JSONCallback"+handler.hashCode();
		get( url+callbackName, callbackName, handler );
	}
	
	public static void get( String url, String callbackName, IServiceRequestHandler handler )
	{
		createCallbackFunction( handler, callbackName );
		addScript(url, callbackName, handler);
	}
	
	public static native void addScript(String url, String callback, IServiceRequestHandler obj) /*-{
	  var scr = document.createElement("script");
	  scr.setAttribute("language", "JavaScript");
	  scr.setAttribute("src", url);

	  // JSON download has 1-second timeout    
	  setTimeout(function() 
	  {      
		if (!window[callback + "done"]) 
		{ 
		  //window.alert("Error: Server timeout");      
		  obj.@com.parabay.client.api.services.IServiceRequestHandler::onRequestComplete(Lcom/google/gwt/core/client/JavaScriptObject;)( null );      
		}       
		// cleanup      
		document.body.removeChild(scr);      
		delete window.callback;      
		delete window[callback + "done"];    
	  }, 10000);        

	  document.body.appendChild(scr);	  
	}-*/;
	
	private native static void createCallbackFunction( IServiceRequestHandler obj, String callback )/*-{
		tmpcallback = function( j )
		{
			obj.@com.parabay.client.api.services.IServiceRequestHandler::onRequestComplete(Lcom/google/gwt/core/client/JavaScriptObject;)( j );
			window[callback + "done"] = true;  
		};
		eval( "window."+callback+"=tmpcallback" );
	}-*/;
}

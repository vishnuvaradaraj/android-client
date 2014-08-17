package com.parabay.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.IDataEventManager;

public class DataEventManager implements IDataEventManager {
    private final HashMap<String, List<?>> map = new HashMap<String, List<?>>();

    public void addHandler(String type, IDataEventHandler handler) {
      List<IDataEventHandler> l = get(type);
      if (l == null) {
        l = new ArrayList<IDataEventHandler>();
        map.put(type, l);
      }
      l.add(handler);
    }

    public void fireEvent(final IDataEvent event) {

    	List<IDataEventHandler> newList = new ArrayList<IDataEventHandler>();
    	
    	List<IDataEventHandler> l = get(event.getType());
    	if (null != l) {
    		
	        for(Iterator<IDataEventHandler> iter = l.iterator(); iter.hasNext(); ) {
	        	final IDataEventHandler handler = iter.next();
	        	
	        	if (!newList.contains(handler)) {
	        		newList.add(handler);
	        		
		        	DeferredCommand.addCommand(new Command() {
		        		
						public void execute() {
							invokeHandler(handler, event);
						}
					});
	        	}
	        }
	        
	        map.put(event.getType(), newList);
    	}
    }

    protected void invokeHandler(IDataEventHandler handler, IDataEvent event) {
    	
    	try {
    		handler.onDataChanged(event);
    	}
    	catch(Exception e) {
    	}    	
    }
    
    @SuppressWarnings("unchecked")
    private List<IDataEventHandler> get(String type) {
      // This cast is safe because we control the puts.
      return (List<IDataEventHandler>) map.get(type);
    }

    private IDataEventHandler getHandler(String eventKey,
        int index) {
      List<IDataEventHandler> l = get(eventKey);
      return l.get(index);
    }

    private int getHandlerCount(String eventKey) {
      List<?> l = map.get(eventKey);
      return l == null ? 0 : l.size();
    }

    public void removeHandler(String eventKey, IDataEventHandler handler) {
      List<IDataEventHandler> l = get(eventKey);
      boolean result = l.remove(handler);
      assert result : "Tried to remove unknown handler: " + handler + " from "
          + eventKey;
    }

}

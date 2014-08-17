package com.parabay.client.ui.components.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.ui.IDisplayComponent;
import com.parabay.client.api.ui.IDisplayHost;
import com.parabay.client.api.ui.IFieldEditor;
import com.parabay.client.api.ui.INavigationManager;
import com.parabay.client.services.wrappers.MobileLayoutWrapper;
import com.parabay.client.services.wrappers.PageMetadataWrapper;
import com.parabay.client.ui.components.FieldGroupPanel;
import com.parabay.client.ui.components.PanelLabel;
import com.parabay.client.utils.Constants;
import com.parabay.client.utils.JSONCodec;

public class EntityEditor  implements IDisplayComponent {

	private IDisplayHost parent;	
	private Map<String, IDataCollectionPager> dataMap;
	private List<IFieldEditor> fieldEditors = new ArrayList<IFieldEditor>();
	
	private Map<String, Object> item;
	
	public EntityEditor(IDisplayHost parent) {
		super();
		this.parent = parent;
		this.parent.replaceStyle("SliderPanel", "TransparentPanel");
	}

	protected boolean onValidate() {
		boolean ret = true;
		
		for(IFieldEditor e : this.fieldEditors) {
			
			if (!e.validate()) {
				ret = false;
			}
		}
		return ret;
	}
	
	public void onDelete() {

		String id = (String)item.get("id");
		if (null != id) {
			this.getPager().deleteObject(id, new IDataEventHandler() {
	
					public void onDataChanged(IDataEvent event) {
							
						parent.exit();
					}
				});
		}
	}
	
	public void onSave() {
		
		if (this.onValidate()) {
		
			for(IFieldEditor e : this.fieldEditors) {
				e.fromScreen(item);
			}
			
			this.getPager().saveObject(item, new IDataEventHandler() {

				private Map<String, Object> obj = item;
				public void onDataChanged(IDataEvent event) {
						
					String id = (String)obj.get("id");
					getPager().onUpdateObject(id, obj);
					parent.exit();
				}
			});
		}
		else {
			Window.alert("Please retry after fixing errors.");
		}
		
	}
	
	public void refresh() {
		
		this.parent.clear();
		
		String id = Globals.getInstance().getNavigationManager().getTokenValue("id");		
		if (null != id && id.length()>0 ) {
			
			item = (Map<String, Object>) this.getPager().get(id);		
		}
		else {
			item = new HashMap<String, Object>();
		}
		
		if (null != item) {
			
			this.parent.getLabel().setText("Editor");
			
			this.parent.setEditCommand("Save", "Save entity", new Command() {
				public void execute() {
					
			    	  onSave();
			    	  refresh();
				}
			});
			
			this.fieldEditors.clear();
			
			PageMetadataWrapper wrapper = (PageMetadataWrapper) this.parent.getPageMetadata().getWrapper();
			if (wrapper.isValid() && null != wrapper.getMobileLayout()) {
				
				MobileLayoutWrapper layout = wrapper.getMobileLayout();	
				Object []groups = (Object []) layout.getGroups();
								
				for(Object groupObj : groups) {

					Map<String, Object> group = (Map<String, Object>) groupObj;
					String groupTitle = (String)group.get("name");
					Object []fields = (Object []) group.get("fields");
					
					FlowPanel groupPanel = new FlowPanel();
					groupPanel.setStyleName("TransparentPanel");
					Label groupHeader = new Label();					
					groupHeader.setText(groupTitle);
					groupHeader.setStyleName("GroupHeader");
					groupPanel.add(groupHeader);

					FieldGroupPanel fieldGroupPanel = new FieldGroupPanel();
					fieldGroupPanel.setStyleName("GroupPanel");
					groupPanel.add(fieldGroupPanel);					
										
					for(Object fieldObj : fields) {
						Map<String, Object> field = (Map<String, Object>) fieldObj;
						Map<String, Object> params = (Map<String, Object>) field.get("params");
						
						IFieldEditor fieldEditor = Globals.getInstance().getFieldEditor(field, this);
						this.fieldEditors.add(fieldEditor);
						
						Widget fieldEditorWidget = fieldEditor.toScreen(item);
						fieldGroupPanel.add(fieldEditorWidget);
					}
					
					this.getParent().add(new PanelLabel(groupPanel, null));
				}
				
				HorizontalPanel hPanel = new HorizontalPanel();
	
				Button deleteButton = new Button("Delete");
				deleteButton.setStyleName("grayButton");
				deleteButton.addClickListener(new ClickListener() {
				      public void onClick(Widget w) {
				    	  onDelete();
				    	  refresh();
				      }
				    });
				hPanel.add(deleteButton);
	
				Button saveButton = new Button("Save");
				saveButton.setStyleName("grayButton");
				saveButton.addClickListener(new ClickListener() {
				      public void onClick(Widget w) {
				    	  onSave();
				    	  refresh();
				      }
				    });
				hPanel.add(saveButton);
				
				this.parent.add(new PanelLabel(hPanel, null));
			}
		}
	}

	public void setDataMap(Map<String, IDataCollectionPager> dataMap) {

		this.dataMap = dataMap;
	}

	protected IDataCollectionPager getPager() {
		
		IDataCollectionPager pager = null;
		
		//get the first dataquery by default
		if (null != this.dataMap && this.dataMap.size()>0) {
			pager = this.dataMap.values().iterator().next();
		}
		
		return pager;
	}

	public IDisplayHost getParent() {
		return parent;
	}
	
}

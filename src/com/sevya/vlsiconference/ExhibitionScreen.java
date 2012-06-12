package com.sevya.vlsiconference;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.table.RichList;
import net.rim.device.api.ui.component.table.TableController;
import net.rim.device.api.ui.component.table.TableModel;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class ExhibitionScreen extends MainScreen {

	public ExhibitionScreen() {
		// TODO Auto-generated constructor stub
		 super(Manager.NO_VERTICAL_SCROLL);
	        setTitle("Vlsi Conference");
	        add(new LabelField("Exhibition", LabelField.FIELD_HCENTER));
	        add(new SeparatorField());
	      
	        Manager mainManager = getMainManager();
	  /*      Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
	        mainManager.setBackground(bg);
	        XYEdges edges = new XYEdges(20, 20, 20, 20);
	        Border border = BorderFactory.createBevelBorder(edges);
	        mainManager.setBorder(border);*/
	        
	        final RichList list = new RichList(mainManager, true, 1, 1);
	        Bitmap bitmap1 = Bitmap.getBitmapResource("extiming.png"); 
	        Bitmap bitmap2 = Bitmap.getBitmapResource("layout.png");
	        Bitmap bitmap3 = Bitmap.getBitmapResource("sponsorsicon.png");
	        list.setFocusPolicy(TableController.ROW_FOCUS);
	        try
	        {
	       	 
	       		 add(new SeparatorField());
	       		 list.add(new Object[] {bitmap1,"Exhibition Timings",new SeparatorField()});
		       	 list.add(new Object[] {bitmap2,"Exhibit Layout",new SeparatorField()});
		       	 list.add(new Object[] {bitmap3,"Sponsors List",new SeparatorField()});
	       	 }
	       	
	        catch(IllegalArgumentException e)
	        {
	       	 Dialog.alert(e.getMessage());
	        }
	        list.setCommand(new Command(new CommandHandler() 
	        {
	            public void execute(ReadOnlyCommandMetadata metadata, Object context) 
	            {      
	            	
	                TableModel tableModel = list.getModel();
	                Object[] objArray = (Object[])tableModel.getRow(list.getFocusRow());
	                if(objArray[1]=="Exhibition Timings")
	                {
	                	UiApplication.getUiApplication().pushScreen(new ExhibitTimingScreen());
	                }
	                if(objArray[1]=="Exhibit Layout")
	                {
	                	UiApplication.getUiApplication().pushScreen(new ImageScreen("exhibitlayout.jpg","Exhibit Layout"));
	                }
	 
	                if(objArray[1]=="Sponsors List")
	                {
	                	
	                	UiApplication.getUiApplication().pushScreen(new ImageScreen("sponsors.png","Sponsors List"));
	                }
	            }
	        }));
	}


}

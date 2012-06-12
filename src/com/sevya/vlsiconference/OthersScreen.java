package com.sevya.vlsiconference;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.CameraArguments;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.invoke.SearchArguments;
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
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
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

public class OthersScreen extends MainScreen {

	public OthersScreen() {
		// TODO Auto-generated constructor stub
		 super(Manager.NO_VERTICAL_SCROLL);
	        setTitle("Vlsi Conference");
	        add(new LabelField("Conference", LabelField.FIELD_HCENTER));
	        add(new SeparatorField());
	      
	        Manager mainManager = getMainManager();
	 /*       Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
	        mainManager.setBackground(bg);
	        XYEdges edges = new XYEdges(20, 20, 20, 20);
	        Border border = BorderFactory.createBevelBorder(edges);
	        mainManager.setBorder(border);*/
	        
	        final RichList list = new RichList(mainManager, true, 1, 1);
	        Bitmap bitmap1 = Bitmap.getBitmapResource("map.png"); 
	        Bitmap bitmap2 = Bitmap.getBitmapResource("layout.png");
	        Bitmap bitmap3 = Bitmap.getBitmapResource("mail.png");
	        Bitmap bitmap4 = Bitmap.getBitmapResource("website.png");
	        list.setFocusPolicy(TableController.ROW_FOCUS);
	        try
	        {
	       	 
	       		
	       		 list.add(new Object[] {bitmap1,"Direction To HICC",new SeparatorField()});
		       	 list.add(new Object[] {bitmap2,"Conference Layout",new SeparatorField()});
		       	 list.add(new Object[] {bitmap3,"Mail My Schedule",new SeparatorField()});
		       	 list.add(new Object[] {bitmap4,"Conference Website",new SeparatorField()});
	       	 }
	       	
	        catch(IllegalArgumentException e)
	        {
	       	 Dialog.alert(e.getMessage());
	        }
	        list.setCommand(new Command(new CommandHandler() 
	        {
	            public void execute(ReadOnlyCommandMetadata metadata, Object context) 
	            {      
	            	
	            	//UiApplication.getUiApplication().pushScreen(new TutorialsScreen());
	                TableModel tableModel = list.getModel();
	                Object[] objArray = (Object[])tableModel.getRow(list.getFocusRow());
	                if(objArray[1]=="Direction To HICC")
	                {
	                	BrowserSession bro = Browser.getDefaultSession();
	                	bro.displayPage("http://maps.google.co.in/maps?f=q&source=embed&hl=en&q=Hyderabad+International+Convention+Centre++and+Novotel+Complex&spn=0.23765,0.08106");
	                }
	                if(objArray[1]=="Conference Layout")
	                {
	                	UiApplication.getUiApplication().pushScreen(new ImageScreen("hiccmap.jpg","Conference Layout"));
	                }
	                if(objArray[1]=="Mail My Schedule")
	                {
	                	UiApplication.getUiApplication().pushScreen(new MailScreen());
	                	
	                }
	                if(objArray[1]=="Conference Website")
	                {
	                	
	                	BrowserSession bro = Browser.getDefaultSession();
	                	bro.displayPage("http://www.vlsiconference.com/vlsi2012/");
	                }
	            }
	        }));
	       
	}

}

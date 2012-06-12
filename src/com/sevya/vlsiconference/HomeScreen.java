package com.sevya.vlsiconference;

import org.json.me.JSONException;

import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
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

public class HomeScreen extends MainScreen 
{
	public HomeScreen(){
		
	        super(Manager.NO_VERTICAL_SCROLL);
	        setTitle("Vlsi Conference");
	        add(new LabelField("Home", LabelField.FIELD_HCENTER));
	        add(new SeparatorField());
	        Manager mainManager = getMainManager();
	/*        Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
	        mainManager.setBackground(bg);
	        XYEdges edges = new XYEdges(20, 20, 20, 20);
	        Border border = BorderFactory.createBevelBorder(edges);
	        mainManager.setBorder(border);*/
	        final RichList list = new RichList(mainManager, true, 1, 1);
	        Bitmap bitmap1 = Bitmap.getBitmapResource("conference.png");
	        Bitmap bitmap2 = Bitmap.getBitmapResource("tutorial.png");
	        Bitmap bitmap3 = Bitmap.getBitmapResource("exhibition.png"); 
	        Bitmap bitmap4 = Bitmap.getBitmapResource("others.png"); 
	     //   Bitmap bitmap5 = Bitmap.getBitmapResource("right.png"); 
	        list.setFocusPolicy(TableController.ROW_FOCUS);
	        try
	        {
	        	new JsonSqliteParse();
	       	 	list.add(new Object[] {bitmap1,"Conference",new SeparatorField()});
	       	 	list.add(new Object[] {bitmap2,"Tutorials",new SeparatorField()});
	       	 	list.add(new Object[] {bitmap3,"Exhibition",new SeparatorField()});
	       	 	list.add(new Object[] {bitmap4,"Others",new SeparatorField()});
	       	
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
	                if(objArray[1]=="Conference")
	                {
	                	UiApplication.getUiApplication().pushScreen(new ConferenceScreen());
	                }
	                if(objArray[1]=="Tutorials")
	                {
	                	UiApplication.getUiApplication().pushScreen(new TutorialsScreen());
	                }
	                if(objArray[1]=="Exhibition")
	                {
	                	UiApplication.getUiApplication().pushScreen(new ExhibitionScreen());
	                }
	                if(objArray[1]=="Others")
	                {
	                	UiApplication.getUiApplication().pushScreen(new OthersScreen());
	                }
	            }
	        }));
	    }
}
package com.sevya.vlsiconference;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.ApplicationManagerException;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RadioButtonField;
import net.rim.device.api.ui.component.RadioButtonGroup;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.table.RichList;
import net.rim.device.api.ui.component.table.TableController;
import net.rim.device.api.ui.component.table.TableModel;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class TutorialsScreen extends MainScreen {

	String jsonString=new String(new FileClass().readfile());
	Vector v=new Vector();
	public TutorialsScreen() {
		 super(Manager.NO_VERTICAL_SCROLL);
	        setTitle("Vlsi Conference");
	        add(new LabelField("Tutorials", LabelField.FIELD_HCENTER));
	        add(new SeparatorField());
	        Manager mainManager = getMainManager();
	  /*      Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
	        mainManager.setBackground(bg);
	        XYEdges edges = new XYEdges(20, 20, 20, 20);
	        Border border = BorderFactory.createBevelBorder(edges);
	        mainManager.setBorder(border);*/
	        final RichList list1 = new RichList(mainManager, false, 1, 1);
	        Bitmap bitmap1 = Bitmap.getBitmapResource("icon.png"); 
	        list1.setFocusPolicy(TableController.ROW_FOCUS);
	        try
	        {
	        	URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
	    		Database d = DatabaseFactory.open(myURI);
	    		Statement st = d.createStatement("SELECT Distinct day from tut ");
	    		st.prepare();
	    		 Cursor c = st.getCursor();
	    		 Row r;
	             int i = 0;
	             while(c.next())
	             {
	                 r = c.getRow();
	                 i++;
	                 v.addElement(r.getString(0));
	             }
	             st.close();
	             for(i=0;i<v.size();i++)
	             {
	            	 st = d.createStatement("SELECT checked,sessionid,venue,time,sessiontitle from tut,tutschedule where day='"+(String)v.elementAt(i)+"' and tut.uniquekey=tutschedule.uniquekey");
	            	 st.prepare();
	            	 c=st.getCursor();
	            	 list1.add(new Object[] {new LabelField((String)v.elementAt(i)),new SeparatorField()});
	            	 while(c.next())
	            	 {
	            		 r=c.getRow();
	            		 if(r.getString(0).equals("true"))
	            		 list1.add(new Object[] {new CheckboxField(r.getString(1)+"("+r.getString(2)+")"+" : "+r.getString(3)+"\n"+r.getString(4), true),new SeparatorField()});
	            		 else if(r.getString(0).equals("false"))
	            		 list1.add(new Object[] {new CheckboxField(r.getString(1)+"("+r.getString(2)+")"+" : "+r.getString(3)+"\n"+r.getString(4), false),new SeparatorField()});

	            	 }
	            	 st.close();
	             }
	             
	             d.close();
	        	
	        }
	        	catch(Exception e)
	        	{
	        		Dialog.alert(e.getMessage());
	        	}
	     /*   	  try
	                {
	                 int x=UiApplication.getUiApplication().getScreenCount();
	                 if(x==3)
	                 	UiApplication.getUiApplication().popScreen(getScreenBelow());
	                }
	                catch (Exception e) {
						// TODO: handle exception
	                	Dialog.alert(e.getMessage());
					}*/ 
	        	
	        list1.setCommand(new Command(new CommandHandler() 
	        {
	            public void execute(ReadOnlyCommandMetadata metadata, Object context) 
	            {        
	            	String temp,sessionid,time,start="",end="";
	                TableModel tableModel = list1.getModel();
	                Object[] objArray = (Object[])tableModel.getRow(list1.getFocusRow());
	                temp=((CheckboxField)(objArray[0])).getLabel();
	                sessionid=temp.substring(0, temp.indexOf("("));
	                time=temp.substring(temp.indexOf(":")+1, temp.indexOf("\n"));
	                time=time.trim();
	                start=time.substring(0,time.indexOf("-"));
	                end=time.substring(time.indexOf("-")+1);
	                try
	                {
	                 int x=UiApplication.getUiApplication().getScreenCount();
	                 if(x==3)
	                	 UiApplication.getUiApplication().popScreen(getScreenBelow());
	                }
	                catch (Exception e) {
						// TODO: handle exception
					}
	               
	                try
	                {

	        			URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
	        			Database d = DatabaseFactory.open(myURI);
	        			Statement st = d.createStatement("update tutschedule set checked='false' where uniquekey in (select uniquekey from tut where " +
	        					"time like '"+start+"%'  and day=(select day from tut where sessionid='"+sessionid+"'))");
	        			st.prepare();
	        			 st.execute(); 
	        			 st.close();
	        			 st = d.createStatement("update tutschedule set checked='false' where uniquekey in (select uniquekey from tut where " +
		        					"time like '%"+end+"' and day=(select day from tut where sessionid='"+sessionid+"'))");
	        			 st.prepare();
	        			 st.execute(); 
	        			 st.close();
	        			d.close();
	                }
	                
	                catch (Exception e) {
						// TODO: handle exception
	                	Dialog.alert(e.getMessage());
					}
	                if(((CheckboxField)(objArray[0])).getChecked())
	                {
	                	UiApplication.getUiApplication().pushScreen(new ScheduleTutorial(sessionid,"Remove from My Schedule"));
	                }
	                else
	                {
	                	UiApplication.getUiApplication().pushScreen(new ScheduleTutorial(sessionid,"Add to My Schedule"));
	                	
	                }
	            }
	        }));
	}
	public boolean onClose() {
		// TODO Auto-generated method stub
		int x=UiApplication.getUiApplication().getScreenCount();
		if(x>2)
		 UiApplication.getUiApplication().popScreen(getScreenBelow());
		//UiApplication.getUiApplication().popScreen(new HomeScreen());
		return super.onClose();
	}
}
/*
 * RichListScreen.java
 *
 * Copyright © 1998-2011 Research In Motion Ltd.
 * 
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

package com.sevya.vlsiconference;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import net.rim.device.api.command.*;
import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.system.*;
import net.rim.device.api.ui.component.table.*;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;


/**
 * Screen demonstrating the use of the RichList object. Displays a list of
 * BlackBerry Smartphone devices with complex formatting and accompanying images.
 * Clicking or tapping on a row displays selected device in a pop up dialog.
 */
public final class ConferenceScreen extends MainScreen
{
	
//	String[] days=new String[]{"Day 1","Day 2","Day 3","Day 4"};
	String []days;
	String s1="";
	String jsonString=new String(new FileClass().readfile());
    public ConferenceScreen()
    {
        super(Manager.NO_VERTICAL_SCROLL);
        setTitle("Conference");
        add(new LabelField("Conference", LabelField.FIELD_HCENTER));
        add(new SeparatorField());
        Manager mainManager = getMainManager();
    /*    Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
        mainManager.setBackground(bg);
        XYEdges edges = new XYEdges(20, 20, 20, 20);
        Border border = BorderFactory.createBevelBorder(edges);
        mainManager.setBorder(border);*/
        
        final RichList list = new RichList(mainManager, true, 1, 1);
        Bitmap bitmap1 = Bitmap.getBitmapResource("calender.png"); 
        list.setFocusPolicy(TableController.ROW_FOCUS);
        try
        {
        	/*JSONObject outer,inner,conf;
        	JSONArray jdays;
        	outer = new JSONObject(jsonString);
        	inner=outer.getJSONObject("vlsi2012");
        	conf=inner.getJSONObject("conference");
        	jdays=conf.getJSONArray("days");
        	days=new String[jdays.length()];
        	for(int i=0;i<jdays.length();i++)
        	{
        		days[i]=((JSONObject)jdays.get(i)).getString("day");
        		list.add(new Object[] {bitmap1,days[i]});
        	}*/
    		URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
    		Database d = DatabaseFactory.open(myURI);
    		Statement st = d.createStatement("SELECT * FROM days");
    		st.prepare();
    		 Cursor c = st.getCursor();
    		 Row r;
             int i = 0;
             while(c.next()) 
             {
                 r = c.getRow();
                 i++;
                 list.add(new Object[] {bitmap1,r.getString(0),new SeparatorField()});
                 
             }
             d.close();
        }
        catch(Exception e)
        {
        	Dialog.alert(e.getMessage());
        }
       /* catch(IllegalArgumentException e)
        {
       	 Dialog.alert(e.getMessage());
        }*/ 
     /*   catch (JSONException e) {
			// TODO Auto-generated catch block
        	Dialog.alert(e.getMessage());
		}*/
        list.setCommand(new Command(new CommandHandler() 
        {
            public void execute(ReadOnlyCommandMetadata metadata, Object context) 
            {        
            	
                TableModel tableModel = list.getModel();
                Object[] objArray = (Object[])tableModel.getRow(list.getFocusRow());
              UiApplication.getUiApplication().pushScreen(new DayScreen((String)objArray[1]));
            }
        }));
        
     //  EditField ef=new EditField();
      // ef.setText(s1);
      // add(ef);
    }
}

package com.sevya.vlsiconference;

import java.util.Vector;

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
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Ui;
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

public class DayScreen extends MainScreen {
	String jsonString=new String(new FileClass().readfile());
	String data[];
	String day="";
	EditField ef=new EditField();
	Vector v=new Vector();
	public DayScreen(final String day) {
	
		
		super(Manager.NO_VERTICAL_SCROLL);
        setTitle("Vlsi Conference");
        add(new LabelField(day, LabelField.FIELD_HCENTER));
        add(new SeparatorField());
        this.day=day;
     
       
        Manager mainManager = getMainManager();
    /*    Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
        mainManager.setBackground(bg);
        XYEdges edges = new XYEdges(20, 20, 20, 20);
        Border border = BorderFactory.createBevelBorder(edges);
        mainManager.setBorder(border);*/
        
        final RichList list = new RichList(mainManager, false, 1, 1); 
        list.setFocusPolicy(TableController.ROW_FOCUS);
   //     list.add(new Object[] {ef});
        try
        {
      /*  	JSONObject outer,inner,conf;
        	JSONArray jdata;
        	outer = new JSONObject(jsonString);
        	inner=outer.getJSONObject("vlsi2012");
        	conf=inner.getJSONObject("conference");
        	jdata=conf.getJSONArray("data");
        	data=new String[jdata.length()];
        	for(int i=0;i<jdata.length();i++)
        	{
        		data[i]=((JSONObject)jdata.get(i)).getString("day");
        		//list.add(new Object[] {bitmap1,data[i]});
        		if(data[i].equals(day))
        		{
        			list.add(new Object[] {new CheckboxField(((JSONObject)jdata.get(i)).getString("chair"), false),((JSONObject)jdata.get(i)).getString("sessiontitle")});
        		}
        	}*/
        	URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
    		Database d = DatabaseFactory.open(myURI);
    		Statement st = d.createStatement("SELECT Distinct time from conference where day='"+day+"'" );
    		st.prepare();
    		 Cursor c = st.getCursor();
    		 Row r;
             int i = 0;
             while(c.next())
             {
                 r = c.getRow();
                 i++;
                 v.addElement(r.getString(0));
             //    list.add(new Object[] {new CheckboxField(),r.getString(0)});
             }
             st.close();
       //      RadioButtonGroup rd[]=new RadioButtonGroup[v.size()];
             for(i=0;i<v.size();i++)
             {
            	 st = d.createStatement("SELECT sessiontitle,checked,sessionsubtitle from conference,schedule where day='"+day+"' and time='"+(String)v.elementAt(i)+"' and conference.uniquekey=schedule.uniquekey");
            	 st.prepare();
            	 c=st.getCursor();
           // 	 add(new LabelField((String)v.elementAt(i)));
            	 list.add(new Object[] {new LabelField((String)v.elementAt(i)),new SeparatorField()});
            	 while(c.next())
            	 {
            		 r=c.getRow();
            //		 add(new CheckboxField(r.getString(0),false));
            	//	 add(new RadioButtonField("Test", rd[i], false));
            	//	 rd[i].add(new RadioButtonField(r.getString(0))); 		
            		 if(r.getString(1).equals("true"))
            		 list.add(new Object[] {new CheckboxField(r.getString(0)+"\n"+r.getString(2), true),new SeparatorField()});
            		 else if(r.getString(1).equals("false"))
            			 list.add(new Object[] {new CheckboxField(r.getString(0)+"\n"+r.getString(2), false),new SeparatorField()});
            		 
            	 }
         //   	 rd[i].setSelectedIndex(0);
            	 st.close();
             }
             
             d.close();
        }
        catch (Exception e)
        {
        	Dialog.alert(e.getMessage());
        }
        list.setCommand(new Command(new CommandHandler() 
        {
            public void execute(ReadOnlyCommandMetadata metadata, Object context) 
            {   
            	
            //	UiApplication.getUiApplication().popScreen(getScreenBelow());
            	String sessiontitle,temp;
                TableModel tableModel = list.getModel();
                Object[] objArray = (Object[])tableModel.getRow(list.getFocusRow());
                temp=((CheckboxField)(objArray[0])).getLabel();
                sessiontitle=temp.substring(0,temp.indexOf("\n"));
                try
                {
                 int x=UiApplication.getUiApplication().getScreenCount();
                 if(x==3)
                 	 UiApplication.getUiApplication().popScreen(getScreenBelow());
             	ef.setText(Integer.toString(x));
                 add(ef);
                }
                catch (Exception e) {
         		// TODO: handle exception
            // 	   Dialog.alert(e.getMessage());
         	}
                try
                {

        			URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
        			Database d = DatabaseFactory.open(myURI);
        			Statement st = d.createStatement("update schedule set checked='false' where uniquekey in (select uniquekey from conference where date=" +
        					"(select date from conference where sessiontitle='"+sessiontitle+"' and day='"+day+"'))");
        			st.prepare();
        			 st.execute(); 
        			 st.close();
        			d.close();
                }
                
                catch (Exception e) {
					// TODO: handle exception
				}
                if(((CheckboxField)(objArray[0])).getChecked())
                {
          //      	((CheckboxField)(objArray[0])).setChecked(false);
                	UiApplication.getUiApplication().pushScreen(new ScheduleScreen(sessiontitle,"Remove from My Schedule",day));
                }
                else
                {
            //       	((CheckboxField)(objArray[0])).setChecked(true);
                	UiApplication.getUiApplication().pushScreen(new ScheduleScreen(sessiontitle,"Add to My Schedule",day));
                	
                }
              
            }
        }));
	}
	protected void onExposed() {
		// TODO Auto-generated method stub
		//UiApplication.getUiApplication().getActiveScreen().invalidate();
	//	Dialog.alert("Akash");
	//	invalidate();
		//doPaint();
	//	updateDisplay();	
		super.onExposed();
	}
	
	public boolean onClose() {
		// TODO Auto-generated method stub
		UiApplication.getUiApplication().popScreen(getScreenBelow());
		return super.onClose();
	}

}

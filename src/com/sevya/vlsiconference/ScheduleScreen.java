package com.sevya.vlsiconference;

import javax.microedition.lcdui.Display;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class ScheduleScreen extends MainScreen implements FieldChangeListener{
	
	String sessiontitle="",day="";
	LabelField ef=new LabelField();
	public ScheduleScreen(String sessiontitle,String btText,String day) {
		
		ButtonField bf;
		
		String s1="";
		this.sessiontitle=sessiontitle;
		this.day=day;
		setTitle("Vlsi Conference");
		 Manager mainManager = getMainManager();
	/*	 Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
	        mainManager.setBackground(bg);
	        XYEdges edges = new XYEdges(20, 20, 20, 20);
	        Border border = BorderFactory.createBevelBorder(edges);
	        mainManager.setBorder(border);*/
	       
		try
		{
		bf= new ButtonField(btText,ButtonField.CONSUME_CLICK) {
		public int getPreferredWidth() {
			// TODO Auto-generated method stub
			net.rim.device.api.system.Display.getWidth();
			return net.rim.device.api.system.Display.getWidth();
		}
		};
		bf.setChangeListener(this);
		add(bf);
		URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
		Database d = DatabaseFactory.open(myURI);
		Statement st = d.createStatement("select * from conference where sessiontitle='"+sessiontitle+"' and day='"+day+"'");
		st.prepare();
		 Cursor c = st.getCursor();
		 Row r;
         int i = 0;
         while(c.next())
         {
             r = c.getRow();
             i++;
             s1= s1+"Date & Time : "+r.getString(1)+" : "+r.getString(3)+"\n\n"+r.getString(4)+"("+r.getString(8)+") : "+ r.getString(5)+"\n\nChair : "+r.getString(7)+"\n\n";
         }
         st.close();
		st = d.createStatement("SELECT * from confsub where uniquekey in(select uniquekey from conference where sessiontitle='"+sessiontitle+"'and day='"+day+"')");
		st.prepare();
		 c = st.getCursor();
         i = 0;
         while(c.next())
         {
             r = c.getRow();
             i++;
             s1= s1+r.getString(1)+"\n"+r.getString(2)+"\n"+r.getString(3)+"\n\n\n";
         }
         ef.setText(s1);
         st.close();
         d.close();
         add(ef);
		}
		catch (Exception e) {
			Dialog.alert(e.getMessage());
		}
	}

	public void fieldChanged(Field field, int context) {
		try
		{
			URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
			Database d = DatabaseFactory.open(myURI);
			if(((ButtonField)field).getLabel().equals("Add to My Schedule"));
			Statement st = d.createStatement("update schedule set checked='true' where uniquekey =(select uniquekey from conference where sessiontitle='"+sessiontitle+"' and day='"+day+"')"); 
			if(((ButtonField)field).getLabel().equals("Remove from My Schedule"))
			st = d.createStatement("update schedule set checked='false' where uniquekey =(select uniquekey from conference where sessiontitle='"+sessiontitle+"' and day='"+day+"')");
			st.prepare();
			 st.execute(); 
			 st.close();
			 
		/*	 st = d.createStatement("SELECT * from schedule");
			st.prepare();
			String s1="";
			Cursor c = st.getCursor();
			Row r;
		     int i = 0;
		     while(c.next())
		         {
		             r = c.getRow();
		             i++;
		             s1= s1+r.getString(0)+"	"+r.getString(1)+"\n";
		         }
		         ef.setText(s1);
			 st.close();*/
			 d.close();
	         
	   //      UiApplication.getUiApplication().pushScreen(new DayScreen("Day 1: Jan 09"));
	//		 UiApplication.getUiApplication().popScreen(this);
			// UiApplication.getUiApplication().popScreen(this);
			 UiApplication.getUiApplication().popScreen(getScreenBelow());
			 UiApplication.getUiApplication().pushScreen(new DayScreen(day));
			
		//	 UiApplication.getUiApplication().popScreen(this);
			
		}
		catch (Exception e) {
			Dialog.alert(e.getMessage());
		}
	}
protected void onExposed() {
	// TODO Auto-generated method stub
	super.onExposed();
//	UiApplication.getUiApplication().getActiveScreen();
}
protected void onUndisplay() {
	// TODO Auto-generated method stub
	//UiApplication.getUiApplication().popScreen(getScreen());
	//UiApplication.getUiApplication().pushScreen(new DayScreen(day));
	super.onUndisplay();
}
}


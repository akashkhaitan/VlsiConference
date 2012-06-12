package com.sevya.vlsiconference;

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
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class ScheduleTutorial extends MainScreen implements FieldChangeListener{

	String sessionid="";
	LabelField lf=new LabelField();

	public ScheduleTutorial(String sessionid,String btText) {
		// TODO Auto-generated constructor stub
		
			
			ButtonField bf;			
			String s1="";
			this.sessionid=sessionid;
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
			Statement st = d.createStatement("select * from tut where sessionid='"+sessionid+"'");
			st.prepare();
			 Cursor c = st.getCursor();
			 Row r;
	         int i = 0;
	         while(c.next())
	         {
	             r = c.getRow();
	             i++;
	             s1= s1+r.getString(3)+"("+r.getString(7)+")"+" : "+r.getString(2)+" : "+r.getString(1)+"\n\n"+r.getString(4)+"\n\nSpeakers : "+r.getString(5)+"\n\nPlace : "+r.getString(6)+"\n\nAbstract : "+r.getString(8)+"\n\n";
	         }
			st.close();
			d.close();
			lf.setText(s1);
			add(lf);
			}
			catch (Exception e) {
				// TODO: handle exception
				Dialog.alert(e.getMessage());
			}
}

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		try
		{
			URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
			Database d = DatabaseFactory.open(myURI);
			if(((ButtonField)field).getLabel().equals("Add to My Schedule"));
			Statement st = d.createStatement("update tutschedule set checked='true' where uniquekey =(select uniquekey from tut where sessionid='"+sessionid+"')"); 
			if(((ButtonField)field).getLabel().equals("Remove from My Schedule"))
			st = d.createStatement("update tutschedule set checked='false' where uniquekey =(select uniquekey from tut where sessionid='"+sessionid+"')");
			st.prepare();
			 st.execute(); 
			 st.close();
			 d.close();
			 UiApplication.getUiApplication().popScreen(getScreenBelow());
			 UiApplication.getUiApplication().pushScreen(new TutorialsScreen());
		//	 UiApplication.getUiApplication().popScreen(this);
		}
		catch (Exception e) {
			// TODO: handle exception
			Dialog.alert(e.getMessage());
		}
	}
	public boolean onClose() {
		// TODO Auto-generated method stub
		UiApplication.getUiApplication().popScreen(getScreenBelow());
		UiApplication.getUiApplication().pushScreen(new TutorialsScreen());
		return super.onClose();
	}
}
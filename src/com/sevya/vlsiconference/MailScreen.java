package com.sevya.vlsiconference;

import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class MailScreen extends MainScreen implements FieldChangeListener{
ButtonField but;
String s1="",to="",sub="",body="";
EditField deligate = new EditField();
EditField emails;
	public MailScreen() {
		// TODO Auto-generated constructor stub
		setTitle("Vlsi Conference");
		add(new LabelField("Deligate's Name : "));
		Manager mainManager = getMainManager();
   /*     Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
        mainManager.setBackground(bg);
        XYEdges edges = new XYEdges(20, 20, 20, 20);
        Border border = BorderFactory.createBevelBorder(edges);
        mainManager.setBorder(border);*/
        but=new ButtonField("Done", ButtonField.CONSUME_CLICK){
        		public int getPreferredWidth() {
        			// TODO Auto-generated method stub
        			net.rim.device.api.system.Display.getWidth();
        			return net.rim.device.api.system.Display.getWidth();
        		}
        };   
      XYEdges thickPadding = new XYEdges(10, 10, 10, 10);
    
     Border roundedBorder = BorderFactory.createRoundedBorder(thickPadding, Border.STYLE_SOLID);
     deligate.setBorder(roundedBorder);
      add(deligate);
      add(new LabelField("Recipient Email IDs : "));
      emails = new EditField(EditField.FILTER_EMAIL){
    	protected void layout(int width, int height) {
    		super.layout(Display.getWidth()-60,Display.getHeight()-200-but.getHeight()-deligate.getHeight());
            setExtent(Display.getWidth()-60,Display.getHeight()-200-but.getHeight()-deligate.getHeight());
    	}
      };
      emails.setBorder(roundedBorder);
      add(emails);
      but.setChangeListener(this);  
      add(but);
     
	}

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		 body="Delgate Name : "+deligate.getText()+"\n\nConference\n\n";
		  	try
				{
		  		
				URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
				Database d = DatabaseFactory.open(myURI);
				Statement st = d.createStatement("SELECT * from conference where uniquekey in(select uniquekey from schedule where checked='true')");
				st.prepare();
				 Cursor c = st.getCursor();
				 Row r;
		       int i = 0;
		       while(c.next())
		       {
		           r = c.getRow();
		           i++;
		           body= body+"Date & Time : "+r.getString(1)+":\n"+r.getString(3)+"\nTitle : "+r.getString(5)+"\nChair : "+r.getString(7)+"\n\n";
		       }
		       
		       st.close();
		       d.close();
		  	}
				catch (Exception e) {
					Dialog.alert(e.getMessage());
				}
				body=body+"\nTutorials"+"\n\n";
				try
				{	
				URI myURI = URI.create("file:///SDCard/databases/" +"vlsi2012.db"); 
				Database d = DatabaseFactory.open(myURI);
				Statement st = d.createStatement("SELECT * from tut where uniquekey in(select uniquekey from tutschedule where checked='true')");
				st.prepare();
				 Cursor c = st.getCursor();
				 Row r;
		       int i = 0;
		       while(c.next())
		       {
		           r = c.getRow();
		           i++;
		           body= body+"Date & Time : "+r.getString(1)+":\n"+r.getString(2)+"\nTitle : "+r.getString(4)+"\nSpeakers : "+r.getString(5)+"\nPlace : "+r.getString(6)+"\n\n";
		       }
		       
		       st.close();
		       d.close();
		  	}
				catch (Exception e) {
					Dialog.alert(e.getMessage());
				}
		      to=emails.getText();
		      sub="Vlsi Conference 2012<Schedule Of "+deligate.getText()+">";
		  MessageArguments me=new MessageArguments( MessageArguments.ARG_NEW, to, sub, body);
	    	Invoke.invokeApplication(Invoke.APP_TYPE_MESSAGES,me);
	}

}

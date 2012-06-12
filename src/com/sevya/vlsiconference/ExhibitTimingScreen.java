package com.sevya.vlsiconference;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.table.RichList;
import net.rim.device.api.ui.component.table.TableController;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class ExhibitTimingScreen extends MainScreen {

	public ExhibitTimingScreen() {
		// TODO Auto-generated constructor stub
		 super(Manager.NO_VERTICAL_SCROLL);
	        setTitle("Exhibition Timings");
	        add(new LabelField("Exhibitions Timings", LabelField.FIELD_HCENTER));
	        add(new SeparatorField());
	      
	        Manager mainManager = getMainManager();
	   /*     Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
	        mainManager.setBackground(bg);
	        XYEdges edges = new XYEdges(20, 20, 20, 20);
	        Border border = BorderFactory.createBevelBorder(edges);
	        mainManager.setBorder(border);*/
	        
	        final RichList list = new RichList(mainManager, true, 1, 1);
	        Bitmap bitmap1 = Bitmap.getBitmapResource("calender.png"); 
	        list.setFocusPolicy(TableController.ROW_FOCUS);
	        try
	        {
	       	 
	       		 add(new SeparatorField());
	       		 list.add(new Object[] {bitmap1,"Day 1:Jan 09: 11:00-18:30",new SeparatorField()});
		       	 list.add(new Object[] {bitmap1,"Day 2:Jan 10: 11:00-18:30",new SeparatorField()});
		       	 list.add(new Object[] {bitmap1,"Day 3:Jan 11: 11:00-18:30",new SeparatorField()});
	       	 }
	       	
	        catch(IllegalArgumentException e)
	        {
	       	 Dialog.alert(e.getMessage());
	        }
	}


}

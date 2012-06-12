package com.sevya.vlsiconference;

import javax.microedition.media.Manager;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class ImageScreen extends MainScreen {
	Bitmap bmPackt;
	public ImageScreen(String imgname,String title) {
		// TODO Auto-generated constructor stub
		 setTitle(title);
		HorizontalFieldManager hm=new HorizontalFieldManager(HORIZONTAL_SCROLL);
		 net.rim.device.api.ui.Manager mainManager = getMainManager();
	/*        Background bg = BackgroundFactory.createSolidBackground(Color.LIGHTGRAY);
	        mainManager.setBackground(bg);
	        XYEdges edges = new XYEdges(20, 20, 20, 20);
	        Border border = BorderFactory.createBevelBorder(edges);
	        mainManager.setBorder(border); */
		bmPackt = Bitmap.getBitmapResource(imgname); 
		hm.add(new BitmapField(bmPackt));
		add(hm);
	//	add(new BitmapField(bmPackt));
	}
protected boolean invokeAction(int action) {
	// TODO Auto-generated method stub
	return super.invokeAction(action);
}

}

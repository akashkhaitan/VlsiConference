package com.sevya.vlsiconference;

import net.rim.device.api.ui.UiApplication;

public class VlsiConference extends UiApplication {

	public VlsiConference() {
		// TODO Auto-generated constructor stub
		pushScreen(new HomeScreen());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VlsiConference app=new VlsiConference();
		app.enterEventDispatcher();

	}

}

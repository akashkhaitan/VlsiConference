package com.sevya.vlsiconference;

import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.io.IOUtilities;

public class FileClass {

	public FileClass() {
		// TODO Auto-generated constructor stub
	}
	public String readfile()
	{
		String s="";
		try
		{
			FileConnection fc = (FileConnection)Connector.open("file:///SDCard/jsonfinal.json");
			 if (!fc.exists())
			 {
				 System.exit(0);
			 }
			 DataInputStream in=fc.openDataInputStream();
			 byte[] data=IOUtilities.streamToBytes(in);
			 s=new String(data);	 
		}
		catch (IOException e)
		{
			System.out.print(e.getMessage());
		}
		return s;
	}

}

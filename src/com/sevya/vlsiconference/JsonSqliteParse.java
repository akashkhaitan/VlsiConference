package com.sevya.vlsiconference;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.ui.component.Dialog;

public class JsonSqliteParse{

	Database d;
	URI myURI;
	String jsonString=new String(new FileClass().readfile());
	public JsonSqliteParse() {
		// TODO Auto-generated constructor stub
		try
		{
		FileConnection fcon=(FileConnection)Connector.open("file:///SDCard/databases/vlsi2012.db");
		 if (!fcon.exists())
		 {
			 concre();
			 insert();
		 }
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}

	public void concre()
	{
		
		 try
	        {
			 if(myURI==null)
	            myURI = URI.create("file:///SDCard/databases/" +
	                                   "vlsi2012.db");
	            d = DatabaseFactory.openOrCreate(myURI);
	            Statement st = d.createStatement( "CREATE TABLE if not exists days ( " +
	                                           		"day TEXT)" );
	            
	            
	           st.prepare();
	           st.execute();
	           st.close(); 
	       //    d.close();
	           st = d.createStatement( "CREATE TABLE if not exists conference ( "+
          		"uniquekey TEXT,day TEXT,date TEXT,time TEXT,sessionid TEXT," +
          		"sessiontitle TEXT," +
          		"sessionsubtitle TEXT,chair TEXT,venue TEXT)" );
	           st.prepare();
	           st.execute();
	           st.close(); 
	           st = d.createStatement( "CREATE TABLE if not exists confsub(uniquekey TEXT," +
	           		"eventid TEXT," +"eventdescription TEXT," +
	           				"speakers TEXT,place TEXT)");
	           st.prepare();
	           st.execute();
	           st.close(); 
	          st = d.createStatement( "CREATE TABLE if not exists tut ( "+
	             		"uniquekey TEXT,day TEXT,time TEXT,sessionid TEXT," +
	             		"sessiontitle TEXT," +
	             		"speakers TEXT,place TEXT,venue TEXT,abs TEXT)" );
	   	           st.prepare();
	   	           st.execute(); 
	   	           st.close(); 
	   	        st = d.createStatement( "CREATE TABLE if not exists schedule ( "+
	             		"uniquekey TEXT,checked TEXT)" );
	   	           st.prepare();
	   	           st.execute(); 
	   	           st.close();
	   	        st = d.createStatement( "CREATE TABLE if not exists tutschedule ( "+
         		"uniquekey TEXT,checked TEXT)" );
	           st.prepare();
	           st.execute(); 
	           st.close();
	   	           
	        }
	        catch ( Exception e ) 
	        {         
	        	Dialog.alert(e.getMessage());
	            System.out.println( e.getMessage() );
	            e.printStackTrace();
	        }
	}
	
	public void insert()
	{
		try
		{
			JSONObject outer,inner,conf,tut;
        	JSONArray jdays,jdata,jsub,tdata;
        	String days[];
        	outer = new JSONObject(jsonString);
        	inner=outer.getJSONObject("vlsi2012");
        	conf=inner.getJSONObject("conference");
        	jdays=conf.getJSONArray("days");
        	jdata=conf.getJSONArray("data");
        	days=new String[jdays.length()];
            for(int i=0;i<jdays.length();i++)
        	{
        		days[i]=((JSONObject)jdays.get(i)).getString("day");
        		Statement st = d.createStatement( "INSERT INTO days VALUES('"+days[i]+"')");
        		st.prepare();
        		st.execute();
        		st.close();
        	}
        	for(int i=0;i<jdata.length();i++)
        	{ 
        		Statement st = d.createStatement( "INSERT " +
        		"INTO conference " +
        		"VALUES('"+(String)(((JSONObject)jdata.get(i)).getString("uniquekey"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("day"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("date"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("time"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("sessionid"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("sessiontitle"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("sessionsubtitle"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("chair"))+"','"
        		+(String)(((JSONObject)jdata.get(i)).getString("venue"))+"')");
        		st.prepare();
        		st.execute();
        		st.close();     	
        		st = d.createStatement( "INSERT " +
                		"INTO schedule " +
                		"VALUES('"+(String)(((JSONObject)jdata.get(i)).getString("uniquekey"))+"','false')");
                		st.prepare();
                		st.execute();
                		st.close();     	
        		jsub=((JSONObject)jdata.get(i)).getJSONArray("subtopics");
        		for(int j=0;j<jsub.length();j++)
        		{
        		st = d.createStatement( "INSERT " +
        	     "INTO confsub " +
        	     "VALUES('"+((JSONObject)jdata.get(i)).getString("uniquekey")+"','"
        	     +((JSONObject)jsub.get(j)).getString("eventid")+"','"
        	     +((JSONObject)jsub.get(j)).getString("eventdescription")+"','"
        	     +((JSONObject)jsub.get(j)).getString("speakers")+"','"
        	     +((JSONObject)jsub.get(j)).getString("place")+"')");
        		st.prepare();
         		st.execute();
         		st.close();
        		}	 	
        	}
        	tut=inner.getJSONObject("tutorial");
        	tdata=tut.getJSONArray("data");
        	for(int i=0;i<tdata.length();i++)
        	{ 
        		Statement st = d.createStatement( "INSERT " +
        		"INTO tut " +
        		"VALUES('"+(String)(((JSONObject)tdata.get(i)).getString("uniquekey"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("day"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("time"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("sessionid"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("sessiontitle"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("speakers"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("place"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("venue"))+"','"
        		+(String)(((JSONObject)tdata.get(i)).getString("abs"))+"')");
        		st.prepare();
        		st.execute();
        		st.close();     	
        		st = d.createStatement( "INSERT " +
                		"INTO tutschedule " +
                		"VALUES('"+(String)(((JSONObject)tdata.get(i)).getString("uniquekey"))+"','false')");
                		st.prepare();
                		st.execute();
                		st.close();     	
        	}
        	
        	d.close();
        	
		}
		catch(JSONException e) 
		{
			
		}
		catch (Exception e) {
			// TODO: handle exception
			Dialog.alert(e.getMessage());
		}
	}
}
	

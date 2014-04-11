package com.wingoku.docseek;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


public class Parse_InsertData extends AsyncTask<Void, String, Integer>
{
	boolean noInternet = false;
	
	public Context context;
	
	public int listView_ID;
	
	public String docList[];
	
	public ListView myList;
	
	public Parse_InsertData(Context con, int listView_ID, ListView listView)
	{
		this.context = con;
		this.listView_ID = listView_ID;
		
		myList = listView;
	}
	

	@Override
	protected Integer doInBackground(Void... params) {
		
		try
		{
			readSubjectsFromInternet();
		}
		catch(Exception e)
		{			
			e.printStackTrace();
		}
		return null;
	}
	
	public void readSubjectsFromInternet() throws ClientProtocolException, IOException, URISyntaxException, JSONException
	{
		if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(context))
		{

			DownloadData internet = new DownloadData(new URI("http://wingoku.bugs3.com/readData.php"), context); 
			String sb = internet.divideStrings();
			
			
			JSONArray array = new JSONArray(sb);

			System.out.println("Data "+sb);
			
			docList = new String[array.length()];
			
			System.out.println("length is "+array.length());
			
			int length = array.length();
			
			for(int i=0;i < length; i++)
			{
				JSONObject temp = array.getJSONObject(i);
				
				docList[i]= temp.getString("FullName"); // json tag
				
			}
		}
		else
			noInternet=true;

	}

	@Override
	protected void onPostExecute(Integer result) 
	{
		super.onPostExecute(result);
		
		if(noInternet)
			Toast.makeText(context, "Phone is not connected to internet\nPlease turn on wifi or mobile Internet", Toast.LENGTH_SHORT).show();	
		
		if(docList != null)
		{	
			CustomArrayAdapter myAdapter = new CustomArrayAdapter(context, listView_ID ,docList); 
			
			myList.setAdapter(myAdapter);
		
		}
		else
		{
			Toast.makeText(context, "No Data Found!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}

}

package com.wingoku.docseek;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

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
	
	public static String docList[], hospList[];
	
	public ListView myList;
	
	private boolean userReviews;
	
	public Parse_InsertData(Context con, int listView_ID, ListView listView, boolean userReviews)
	{
		this.context = con;
		this.listView_ID = listView_ID;
		
		myList = listView;
		
		this.userReviews = userReviews;
	}
	

	@Override
	protected Integer doInBackground(Void... params) {
		
		try
		{
			if(true)
				readSubjectsFromInternet();
			else
				readUserReviews();
		}
		catch(Exception e)
		{
			//Toast.makeText(ListOfSuListOfDeptsAndSemesters.thise to connected to internet", Toast.LENGTH_SHORT).show();
			
			e.printStackTrace();
		}
		return null;
	}
	
	public void readSubjectsFromInternet() throws ClientProtocolException, IOException, URISyntaxException, JSONException
	{
		Log.e("doc Seek", "Inside");
		if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(context))
		{
			//System.out.println(phpScriptAddress);
			
			// encoding for special characters, without encoding android HTTP won't be able to send proper data to php script
			String encodedParams = URLEncoder.encode(Search_Frag.selectedProfession, "UTF-8");
			
			DownloadData internet = new DownloadData(new URI("http://wingoku.bugs3.com/docAndHospList.php?userSelection="+ encodedParams), context); //"http://wingoku.bugs3.com/uetapp/getassignmentslist.php"
			
			String sb = internet.divideStrings();
			
			
			JSONArray array = new JSONArray(sb);
			

			System.out.println("Data "+sb);
			
			docList = new String[array.length()];
			
			hospList = new String[array.length()];
			

			
			int length = array.length();
			
			for(int i=0;i < length; i++)
			{
				JSONObject temp = array.getJSONObject(i);
				
				docList[i]= temp.getString("FullName"); // title is the tag in JsonFormat data which contains a value. using temp.getString("title") we get the 
				// the value stored in title tag in jsonFormat data
			
				hospList[i]= temp.getString("PlaceOfCurrentPosting");
				

				Log.e("DocSeek", docList[i]);
				
				
			}
		}
		else
			noInternet=true;

	}
	
	
	
	public void readUserReviews() throws ClientProtocolException, IOException, URISyntaxException, JSONException
	{
		Log.e("doc Seek", "Inside");
		if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(context))
		{
			//System.out.println(phpScriptAddress);
			
			// encoding for special characters, without encoding android HTTP won't be able to send proper data to php script
			String encodedParams = URLEncoder.encode(Search_Frag.selectedProfession, "UTF-8");
			
			DownloadData internet = new DownloadData(new URI("php script"+ encodedParams), context); //"http://wingoku.bugs3.com/uetapp/getassignmentslist.php"
			
			String sb = internet.divideStrings();
			
			
			JSONArray array = new JSONArray(sb);
			
			docList = new String[array.length()];
			
			hospList = new String[array.length()];
			
			
			int length = array.length();
			
			for(int i=0;i < length; i++)
			{
				JSONObject temp = array.getJSONObject(i);
				
				docList[i]= temp.getString("userreviews"); // title is the tag in JsonFormat data which contains a value. using temp.getString("title") we get the 
				// the value stored in title tag in jsonFormat data
			
				

				Log.e("DocSeek", docList[i]);
				
				
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
			CustomAdapter_ForDocList myAdapter = new CustomAdapter_ForDocList(context, listView_ID ,docList,hospList); 
			
			myList.setAdapter(myAdapter);
		}
		else
		{
			CustomAdapter_ForDocList myAdapter = new CustomAdapter_ForDocList(context, listView_ID ,new String[]{"No Data Found!"},null); 
			
			myList.setAdapter(myAdapter);
			
			Toast.makeText(context, "No Data Found!", Toast.LENGTH_LONG).show();
			//finish();
		}
		

	}
	
}

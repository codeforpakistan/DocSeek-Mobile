package com.wingoku.docseek;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class DocList extends FragmentSuperClass{
	
	
	String docList[], hospList[];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		visibleFragmentName("DocListFrag");
		
		StartingPoint.slideMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		
		
		// create a layout file having a list and assign data to list by calling parse_insert data.
		
		View view = inflater.inflate(R.layout.doc_list, container,false);
		
		final ListView myList = (ListView) view.findViewById(R.id.doc_listView);
		
		Parse_InsertData getData = new Parse_InsertData(getActivity().getApplicationContext(), R.id.doc_listView, myList, true);
		
		getData.execute();
		
		
		myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				// replace with frag that displays details about selected doctor
				
				//getFragmentManager().beginTransaction().addToBackStack("docDetail").replace(getId(), new DocDetails_and_MapFrag()).commit();
				
				
				Bundle bund = new Bundle();
				
				
				bund.putString("Name",((TextView) arg1.findViewById(R.id.doc_mainText)).getText().toString());
				bund.putString("Hospital", ((TextView) arg1.findViewById(R.id.doc_subText)).getText().toString());
				
				Intent intent;
				
				if(isScreenBig())
				{
					// if screen size if > 5 inches display patient review list below doc detail in doc&map frag
					intent = new Intent(getActivity(), DocDetails_and_MapFrag.class);
					
				}
				else
					intent = new Intent(getActivity(), ViewPager_DocAndMap.class);
				
				intent.putExtras(bund);
				startActivity(intent);
			}
		});
		
		return view;
	}
	
	
	
	private boolean isScreenBig()
	{
		DisplayMetrics dm = getResources().getDisplayMetrics();
		
		double x = Math.pow(dm.widthPixels/dm.xdpi,2);
	    double y = Math.pow(dm.heightPixels/dm.ydpi,2);
	    double screenInches = Math.sqrt(x+y);
	    
	    if(screenInches > 5.0f)
	    	return true;
	    
	    return false;
	}
	
	// not required
	public void download_DoctorsAndHospList(Context context, String text) throws ClientProtocolException, IOException, URISyntaxException, JSONException
	{
				if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(context))
				{
					//System.out.println(phpScriptAddress);
					DownloadData internet = new DownloadData(new URI("http://wingoku.bugs3.com/suggestions.php?suggestion="+text), context); //"http://wingoku.bugs3.com/uetapp/getassignmentslist.php"
					
					String sb = internet.divideStrings();
					
					JSONArray array = new JSONArray(sb);

					System.out.println("Data "+sb);
					
					docList = new String[array.length()];
					hospList = new String[array.length()];
					
					int length = array.length();
					
					for(int i=0;i < length; i++)
					{
						JSONObject temp = array.getJSONObject(i);
						
						docList[i]= temp.getString("Cadre"); // title is the tag in JsonFormat data which contains a value. using temp.getString("title") we get the 
						// the value stored in title tag in jsonFormat data
						
						hospList[i]= temp.getString("Cadre");
						
					}
					
				}
				
		}
	

	
}

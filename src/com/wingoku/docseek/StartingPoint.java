package com.wingoku.docseek;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;



public class StartingPoint extends SherlockFragmentActivity {

	
	
	String[] names = {"Search", "All categories", "About", "Developers"};
	
	ListView list;
	DrawerLayout slideMenu;
	ActionBarDrawerToggle drawerToggel;
	
	Button searchBut;
	
	View lowerTab;
	
	AutoCompleteTextView searchBox;
	
	GoogleMap googleMap = null;
	
	Handler handler;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starting_point);
		
		getSupportActionBar().setTitle("Doc Seek");
		getSupportActionBar().setSubtitle("Find Your Doctor");
		
		// for splash screen
		getSupportActionBar().hide();
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5DC4EB")));
		
		slideMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		final FragmentManager fragManager = getSupportFragmentManager();
		FragmentTransaction fragTranscation= fragManager.beginTransaction();
		
		fragTranscation.replace(R.id.placeHolderFrag, new SplashScreen_Frag()).commit();
		
		// disabling slideMenu for the splash screen
		slideMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		
		
		if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(this))
		{
			Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
		
			
			getSupportActionBar().show();
			
			// enabling slideMenu opening closing 
			slideMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			
			fragManager.beginTransaction().replace(R.id.placeHolderFrag, new AboutDevelopers()).commit();
		}
		else
		{
			//Toast.makeText(this, "Phone is not connected to internet\nYou can't use our services", Toast.LENGTH_LONG).show();
			
			
			AlertDialog.Builder adBuilder = new AlertDialog.Builder(this).setTitle("Connectivity Issue")
					.setMessage("Internet is not available\nPlease connect to the internet and try again!")
					.setCancelable(false).setPositiveButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
					finish();
				}
			});
			
			try {
				
				// delaying alert dialog popup for a few seconds for professional look :D
				Thread.sleep(3000);
				
				adBuilder.create().show();
				
			} 
			catch (InterruptedException e) 
			{
				
				e.printStackTrace();
			}
			
		}
		
		handler = new Handler();

		searchBox = (AutoCompleteTextView) findViewById(R.id.mSearchBox);
		
		searchBox.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				new Thread(new BackgroundTask()).start();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		

		
		// changing drag sensitivity area width using reflections
		try
		{
			Field mDragger = slideMenu.getClass().getDeclaredField(
			        "mLeftDragger");
			mDragger.setAccessible(true);
			ViewDragHelper draggerObj = (ViewDragHelper) mDragger
			        .get(slideMenu);
	
			Field mEdgeSize = draggerObj.getClass().getDeclaredField(
			        "mEdgeSize");
			mEdgeSize.setAccessible(true);
			int edge = mEdgeSize.getInt(draggerObj);
	
			mEdgeSize.setInt(draggerObj, edge * 5);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		list = (ListView) findViewById(R.id.slideMenuList);
		
		list.setAdapter(new CustomArrayAdapter(StartingPoint.this, R.layout.custom_list, names));
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				

			if(names[position].equals("Search"))
					fragManager.beginTransaction().replace(R.id.placeHolderFrag, new DocList()).commit();
			else
				if(names[position].equals("Developers"))
					fragManager.beginTransaction().replace(R.id.placeHolderFrag, new AboutDevelopers()).commit();
				else
					if(names[position].equals("About"))
					{
						fragManager.beginTransaction().replace(R.id.placeHolderFrag, new DocList_and_MapFrag()).commit();
						
						GoogleMap googleMap = null;
						
						 if (googleMap == null) {
							 
							 if(getSupportFragmentManager().findFragmentById(R.id.mapFrag) == null)
							 {
								 Toast.makeText(getApplicationContext(),
					                        "Null pointer", Toast.LENGTH_SHORT)
					                        .show();
								 if(((SupportMapFragment) getSupportFragmentManager().findFragmentById(
					                    R.id.mapFrag)) == null)
									 Toast.makeText(getApplicationContext(),
						                        "Null pointer 2", Toast.LENGTH_SHORT)
						                        .show();
								 
								 try
								 {
									 if(((SupportMapFragment) getSupportFragmentManager().findFragmentById(
							                    R.id.mapFrag)).getMap() == null)
											 Toast.makeText(getApplicationContext(),
								                        "Null pointer 33", Toast.LENGTH_SHORT)
								                        .show();
								 }
								 catch (Exception e) {
									// TODO: handle exception
								}
									 
							 }
							 
					       
					            // check if map is created successfully or not
					            if (googleMap == null) {
					                Toast.makeText(getApplicationContext(),
					                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
					                        .show();
					            }
						 }
					}

				
				slideMenu.closeDrawer(Gravity.LEFT);
			}
		});

		

		

		slideMenu.setDrawerListener(drawerToggel);
		
	}
	
	public String[] readSubjectsFromInternet(Context context, String text) throws ClientProtocolException, IOException, URISyntaxException, JSONException
	{
		if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(context))
		{
			DownloadData internet = new DownloadData(new URI("http://wingoku.bugs3.com/suggestions.php?suggestion="+text), context);
			
			String sb = internet.divideStrings();
			
			JSONArray array = new JSONArray(sb);

			System.out.println("Data "+sb);
			
			String[] dataList = new String[array.length()];
			
			System.out.println("length is "+array.length());
			
			int length = array.length();
			
			// getting json data
			for(int i=0;i < length; i++)
			{
				JSONObject temp = array.getJSONObject(i);
				
				dataList[i]= temp.getString("Cadre"); 
				
			}
			
			return dataList;
		}
		
		return null;

	}
	
	
	class BackgroundTask implements Runnable {
		
		String[] suggestionList;
		
		@Override
		public void run() {
			
			try {
				
				suggestionList = readSubjectsFromInternet(getApplicationContext(), searchBox.getText().toString());
				
				System.out.println("String "+ suggestionList[0]);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
		
				handler.post(new Runnable() {
					@Override
					public void run() {
						
						if(suggestionList != null)
						{
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,suggestionList);
							searchBox.setAdapter(adapter);
						}
					}
				});
			}
		}

}

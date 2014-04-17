package com.wingoku.docseek;

import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;





public class StartingPoint extends SherlockFragmentActivity {


	// for itboard doc seek app
	
	String[] names = {"Search", "About", "Developers"};
	
	ListView list;
	DrawerLayout slideMenu;
	ActionBarDrawerToggle drawerToggel;
	
	Button searchBut;
	
	View lowerTab;
	
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
			Toast.makeText(this, "Connection Successful!", Toast.LENGTH_LONG).show();
		
			
			getSupportActionBar().show();
			
			// enabling slideMenu opening closing 
			slideMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			
			fragManager.beginTransaction().replace(R.id.placeHolderFrag, new Search_Frag()).commit();
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
			
		
			// delaying alert dialog popup for a few seconds for professional look :D
			final AlertDialog myDialog = adBuilder.create();

			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					
					myDialog.show();
				}
			},3000);
		}
		
		
		handler = new Handler();
		

		
		try {
			Field mDragger = slideMenu.getClass().getDeclaredField(
			        "mLeftDragger");//mRightDragger for right obviously
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
		
		
		list.setAdapter(new CustomAdapter_ForSideMenu(StartingPoint.this, R.layout.custom_list_side_menu, names));
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
			if(names[position].equals("Search"))
					fragManager.beginTransaction().replace(R.id.placeHolderFrag, new Search_Frag()).commit();
			else
				if(names[position].equals("Developers"))
					fragManager.beginTransaction().addToBackStack("aboutDevs").replace(R.id.placeHolderFrag, new AboutDevelopers()).commit();
				else
					if(names[position].equals("About"))
					{
						fragManager.beginTransaction().addToBackStack("aboutApp").replace(R.id.placeHolderFrag, new About_DocSeek_Frag()).commit();
						
						try
						{
							//docAndMaps(fragManager);
						}
						catch(Exception e)
						{
							Log.e("DocSeek",e.toString());
						}
					}

				
				slideMenu.closeDrawer(Gravity.LEFT);
			}
		});

		
		drawerToggel = new ActionBarDrawerToggle(this, slideMenu, R.drawable.ic_launcher, R.string.open, R.string.close)
		{

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				
			}
 
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				

			}

		};
		

		slideMenu.setDrawerListener(drawerToggel);
		
	}
	
	
	// not required
	public void docAndMaps(FragmentManager fragManager)
	{
		fragManager.beginTransaction().replace(R.id.placeHolderFrag, new DocDetails_and_MapFrag()).commit();
		
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
	
	

}

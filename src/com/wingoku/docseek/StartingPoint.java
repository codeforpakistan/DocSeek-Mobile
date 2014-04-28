package com.wingoku.docseek;

import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;





public class StartingPoint extends SherlockFragmentActivity {


	
	String[] names = {"Search", "Bookmarked Doctors", "About", "Developers"};
	
	ListView list;
	static DrawerLayout slideMenu;
	ActionBarDrawerToggle drawerToggel;
	
	
	Button searchBut;
	
	View lowerTab;
	
	GoogleMap googleMap = null;
	
	Handler handler;
	
	FragmentManager fragManager;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starting_point);
		
		//getSupportActionBar().setSubtitle("Find Your Doctor");
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		// for splash screen
		getSupportActionBar().hide();
		
		SpannableString spannable = new SpannableString("DocSeek");
		spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F0F0F0")), 0, "DocSeek".length(), 0);
		
		getSupportActionBar().setTitle(spannable);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5"))); //#5DC4EB  // 6BB9F0
		
		slideMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		fragManager = getSupportFragmentManager();
		FragmentTransaction fragTranscation= fragManager.beginTransaction();
		
		fragTranscation.replace(R.id.placeHolderFrag, new SplashScreen_Frag()).commit();
		
		// disabling slideMenu for the splash screen
		slideMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		
		
		if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(this))
		{	
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					
					Toast.makeText(StartingPoint.this, "Connection Successful!", Toast.LENGTH_LONG).show();
						
					getSupportActionBar().show();
					
					// enabling slideMenu opening closing 
					slideMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
					
					fragManager.beginTransaction().replace(R.id.placeHolderFrag, new Search_Frag()).commit();
					
				}
			}, 3000);
			
			
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
		

		
		try
		{
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
					fragManager.beginTransaction().replace(R.id.placeHolderFrag, new AboutDevelopers()).commit();
				else
					if(names[position].equals("About"))
					{
						FragmentTransaction fragTrans = fragManager.beginTransaction();
						
						//fragTrans.setCustomAnimations(R.anim.slide_fragment_in, R.anim.slide_fragment_out);
						
						fragTrans.replace(R.id.placeHolderFrag, new About_DocSeek_Frag());
						
						//fragTrans.addToBackStack(null);
						
						fragTrans.commit();
					}
					else
						if(names[position].equals("Bookmarked Doctors"))
							fragManager.beginTransaction().replace(R.id.placeHolderFrag, new BookmarkedDoctors()).commit();
//					
				
				slideMenu.closeDrawer(Gravity.LEFT);
			}
		});
		
		drawerToggel = new ActionBarDrawerToggle(this, slideMenu, R.drawable.ic_launcher, R.string.open, R.string.close)
		{

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				
				//lowerTab.setVisibility(View.VISIBLE);
			}
 
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				
				//lowerTab.setVisibility(View.GONE);
			}

		};
		

		slideMenu.setDrawerListener(drawerToggel);
				
	}
	
	
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		getSupportFragmentManager().popBackStack();
	
	}
	

		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Pass the event to ActionBarDrawerToggle, if it returns
	        // true, then it has handled the app icon touch event
			
			 if (item.getItemId() == android.R.id.home) {
				
				 if(!FragmentSuperClass.name.isEmpty() && FragmentSuperClass.name.equals("SearchFrag"))
					 if(slideMenu.isDrawerOpen(GravityCompat.START))
					 {
						 slideMenu.closeDrawer(GravityCompat.START); 
					 }
					 else
					 	slideMenu.openDrawer(GravityCompat.START);
				 else
					 onBackPressed();
			 }
	        return super.onOptionsItemSelected(item);
	    }
	 
}

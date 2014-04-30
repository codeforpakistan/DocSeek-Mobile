package com.wingoku.docseek;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class ViewPager_DocAndMap_Frag extends SherlockFragment{


    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";

    private GoogleMap googleMap;
   
    CheckBox bookmarkDoc;
    
    String docName, hospName, phoneNum;
    
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.viewpager_docdetail_map, container, false);
		
		docName = getActivity().getIntent().getExtras().getString("Name");
		hospName = getActivity().getIntent().getExtras().getString("Hospital");
		
		TextView doctName = (TextView) view.findViewById(R.id.docName_detailActivity);
		TextView HosptName = (TextView) view.findViewById(R.id.hospName_detailActivity);
		
		doctName.setText(docName);
		HosptName.setText(hospName);
		
		
		bookmarkDoc = (CheckBox) view.findViewById(R.id.bookmark);
		
		bookmarkDoc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				// save docName, HospName & Phone number on disk
				
				saveBookmark();
				
			}
		});
		
    	 SlidingUpPanelLayout layout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
         layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
         layout.setAnchorPoint(0.3f);
         layout.setPanelSlideListener(new PanelSlideListener() {
             @Override
             public void onPanelSlide(View panel, float slideOffset) {
                 Log.i("DocSeek", "onPanelSlide, offset " + slideOffset);
//                 if (slideOffset < 0.2) {
//                     if (getSherlockActivity().getSupportActionBar().isShowing()) {
//                    	 getSherlockActivity().getSupportActionBar().hide();
//                     }
//                 } else {
//                     if (!getSherlockActivity().getSupportActionBar().isShowing()) {
//                    	 getSherlockActivity().getSupportActionBar().show();
//                     }
//                 }
             }

             @Override
             public void onPanelExpanded(View panel) {
                 Log.i("DocSeek", "onPanelExpanded");

                 
             }

             @Override
             public void onPanelCollapsed(View panel) {
                 Log.i("DocSeek", "onPanelCollapsed");

             }

             @Override
             public void onPanelAnchored(View panel) {
                 Log.i("DocSeek", "onPanelAnchored");

             }
         });
         

//         TextView t = (TextView) findViewById(R.id.main);
//         t.setOnClickListener(new OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 Intent i = new Intent(Intent.ACTION_VIEW);
//                 i.setData(Uri.parse("http://www.umanoapp.com"));
//                 startActivity(i);
//             }
//         });
 //+
         //TextView t = (TextView) findViewById(R.id.name);
         
//         Button f = (Button) parentView.findViewById(R.id.follow);
//         f.setMovementMethod(LinkMovementMethod.getInstance());
//         f.setOnClickListener(new OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 Intent i = new Intent(Intent.ACTION_VIEW);
//                 i.setData(Uri.parse("http://www.twitter.com/umanoapp"));
//                 startActivity(i);
//             }
//         });

//
//         boolean actionBarHidden = savedInstanceState != null ?
//                 savedInstanceState.getBoolean(SAVED_STATE_ACTION_BAR_HIDDEN, false): false;
//         if (actionBarHidden) {
//             //getActionBar().hide();
//         }

//         userReviewsList = (ListView) findViewById(R.id.patientReviewsList);
//         
//         Parse_InsertData getData = new Parse_InsertData(getApplicationContext(), R.id.patientReviewsList, userReviewsList, false);
// 		
// 		 getData.execute();
 		
         
         setMapFrag();
         

		
		return view;
	}
	
	
    
    
    private void setMapFrag()
    {
    	 //try
         {
        	 // dynamically creating Map Fragment. Because if we create/inflate xml fragment inside a fragment, it causes null Pointer
        	 // exception if we relaunch that xml inflated Map fragment
        	 
        	 SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
             googleMap = mMapFragment.getMap();
             
   

             
             if(mMapFragment == null)
            	 Toast.makeText(getActivity(), "NULL mapFragment", Toast.LENGTH_SHORT).show();
        	 
	         // filling map fragment 
	         getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mapFragPlaceHolder, new MapsFragment()).commit();
	         
	        
	         
	         /*
	         if(googleMap == null)
	        	 Toast.makeText(this, "NULL map", Toast.LENGTH_SHORT).show();
	         
	    	
	    	  if(googleMap!= null)
		         {
			         
		        	 googleMap.setMyLocationEnabled(true);
		        	 googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		        	 
			         Device_GPS_Coords mGPS = new Device_GPS_Coords(this);
		             
			         mGPS.locationSingleUpdate();
			         
		             double latitude = mGPS.getUserLat();
		             double longitude = mGPS.getUserLongt();
		             
		             Toast.makeText(this, "lat "+ latitude+ " longt "+ longitude, Toast.LENGTH_SHORT).show();
		             
		             // create marker
		             //Marker marker = 
		              
		             // adding marker
		            // googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(new LatLng(60, 120)).title("Your Location"));
		             
		             Marker marker1 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(new LatLng(31.1, 69.2)).title("Your Location"));
		             
		            //if(!marker.isVisible())
		            	//Toast.makeText(getActivity(), "Not visible", Toast.LENGTH_SHORT).show();
		            
		            marker1.setPosition(new LatLng(31.1, 69.2));
		            marker1.setVisible(true);
		            
		            */
		            
		         }
	        
	         
	       
         }
         //catch (Exception e)
         {
			//Log.e("DocSeek", e.toString());
		}
         
         
        private void saveBookmark()
     	{
     		try
     		{
     		    String filePath = Environment.getExternalStorageDirectory().getPath()+"/DocSeek/Bookmarks/";
     		    File file = new File(filePath);
     		    if (!file.exists()) {
     		        file.mkdirs();
     		    }
     		   
     		    
     		    if(docName.isEmpty())
     		    {
     		    	Toast.makeText(getActivity(), "No information exists to be saved", Toast.LENGTH_SHORT).show();
     		    	
     		    	return;
     		    }
     	
     		    String fileName =filePath.concat(docName+".txt");

     		    FileOutputStream fos = new FileOutputStream(fileName, false);
     		    
     		    if(phoneNum == null ||phoneNum.isEmpty())
     		    	phoneNum = "115"; // if number not avaialable, I have assigned Eidhi number for emergency calls
     		   
     		    String data = docName+"\n"+hospName+"\n"+phoneNum;
     		    
     		    fos.write(data.getBytes());
     		    fos.close();
     		    
     		    
     		    Toast.makeText(getActivity(), "Bookmark Saved", Toast.LENGTH_SHORT).show();
     		}
     		catch(Exception e)
     		{
     			Log.e("DocSeek", e.toString());

     		}
     		
     	}
        
       
    	 
}
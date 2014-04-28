package com.wingoku.docseek;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


// This class is deprecated for now
public class MapsFragment extends FragmentSuperClass{


	GoogleMap googleMap = null;
	
	final int mapZoomLevel = 15;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		visibleFragmentName("MapsFrag");
		
		View maps = inflater.inflate(R.layout.map_fragment, container,false);
			
	     if (googleMap == null) 
	     {
	    	// Toast.makeText(getActivity().getApplicationContext(),"working", Toast.LENGTH_SHORT).show();
	    	 
	    	 
	    	 SupportMapFragment temp = (SupportMapFragment) getFragmentManager().findFragmentById(
	    			 R.id.mapFrag);
	    	 
	    	 
	    	 if(temp!=null)
	    		 googleMap = temp.getMap();

                
                //Toast.makeText(getActivity(), "here", Toast.LENGTH_SHORT).show();   

   	    	  if(googleMap!= null)
   		         {
   	    		//Toast.makeText(getActivity(), "Inside", Toast.LENGTH_SHORT).show();
   		        	 googleMap.setMyLocationEnabled(true);
   		        	 googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
   		        	 
   			         final Device_GPS_Coords mGPS = new Device_GPS_Coords(getActivity());
   		             
   			         mGPS.locationSingleUpdate();
   			         
   			         new Handler().postDelayed(new  Runnable() {
						public void run() {
						
							double latitude = mGPS.getUserLat();
		   		             double longitude = mGPS.getUserLongt();
		   		             
		   		             //Toast.makeText(getActivity(), "lat "+ latitude+ " longt "+ longitude, Toast.LENGTH_SHORT).show();
		   		             
		   		             
		   		             LatLng curMapLocation = new LatLng(latitude, longitude);
		   		             
		   		             googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curMapLocation, mapZoomLevel));
		   		             
		   		             // adding marker
		   		            // Toast.makeText(getActivity(), "Setting marker", Toast.LENGTH_SHORT).show();
		   		             Marker marker1 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).position(curMapLocation).title("Your Location"));
		   		             

						}
					}, 2000);
   		             
   		            
            }
   	
   	
            
	     }
	     Toast.makeText(getActivity(), "returning", Toast.LENGTH_SHORT).show();
		return maps;
	}
	

	/*
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		SherlockFragment frag = (SherlockFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
		
		if(frag != null)
		{
			getFragmentManager().beginTransaction().remove(frag).commit();
			
			Toast.makeText(getActivity(), "removing map frag", Toast.LENGTH_SHORT).show();
		}
	}
	*/
}

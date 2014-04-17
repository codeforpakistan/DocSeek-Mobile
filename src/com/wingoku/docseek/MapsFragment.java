package com.wingoku.docseek;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;


// This class is deprecated for now
public class MapsFragment extends SherlockFragment{


	GoogleMap googleMap = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View maps = inflater.inflate(R.layout.map_fragment, container,false);
			
	     if (googleMap == null) {
	    	 Toast.makeText(getActivity().getApplicationContext(),
                    "workin", Toast.LENGTH_SHORT)
                    .show();
	    	 
	    	 
	    	 SupportMapFragment temp = (SupportMapFragment) getFragmentManager().findFragmentById(
	    			 R.id.mapFrag);
	    	 
	    	 
	    	 if(temp!=null)
	    		 googleMap = temp.getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
   	
   	
        }
		return maps;
	}
	
	
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
}

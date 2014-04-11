package com.wingoku.docseek;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class DocList_and_MapFrag extends SherlockFragment {
    private static final String TAG = "DockSeek";

    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";
    
    Button myImage;
    private GoogleMap googleMap;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	
    	 View parentView = inflater.inflate(R.layout.map_doclist, container,false);
    	 SlidingUpPanelLayout layout = (SlidingUpPanelLayout) parentView.findViewById(R.id.sliding_layout);
         layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
         layout.setAnchorPoint(0.3f);
         layout.setPanelSlideListener(new PanelSlideListener() {
             @Override
             public void onPanelSlide(View panel, float slideOffset) {
                 Log.i(TAG, "onPanelSlide, offset " + slideOffset);

             }

             @Override
             public void onPanelExpanded(View panel) {
                 Log.i(TAG, "onPanelExpanded");

             }

             @Override
             public void onPanelCollapsed(View panel) {
                 Log.i(TAG, "onPanelCollapsed");

             }

             @Override
             public void onPanelAnchored(View panel) {
                 Log.i(TAG, "onPanelAnchored");

             }
         });
         
         myImage = (Button) parentView.findViewById(R.id.button2);
         
         myImage.setOnClickListener(new OnClickListener() {
 			
     			@Override
     			public void onClick(View arg0) {
     				
     				Toast.makeText(getActivity().getApplicationContext(), "ImageView touched", Toast.LENGTH_SHORT).show();
     				
     			}
     		});


         TextView t = (TextView) parentView.findViewById(R.id.name);
         Button f = (Button) parentView.findViewById(R.id.follow);
         f.setMovementMethod(LinkMovementMethod.getInstance());
         f.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse("http://www.wingoku.com"));
                 startActivity(i);
             }
         });


         boolean actionBarHidden = savedInstanceState != null ?
                 savedInstanceState.getBoolean(SAVED_STATE_ACTION_BAR_HIDDEN, false): false;
        

    
         // filling map fragment 
         getFragmentManager().beginTransaction().replace(R.id.mapFragPlaceHolder, new MapsFragment()).commit();

         return parentView;
    }
}

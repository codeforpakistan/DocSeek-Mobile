package com.wingoku.docseek;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;


public class DocDetails_and_MapFrag extends SherlockFragmentActivity {
    private static final String TAG = "DemoActivity";

    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";

    private GoogleMap googleMap, mMap;

    ListView userReviewsList;
   
    CheckBox bookmarkDoc;
    
    String docName, hospName, phoneNum;
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);  
		
    	// View parentView = inflater.inflate(R.layout.map_and_doc_detail, container,false);
		setContentView(R.layout.map_and_doc_detail);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		SpannableString spannable = new SpannableString("DocSeek");
		spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F0F0F0")), 0, "DocSeek".length(), 0);
		
		getSupportActionBar().setTitle(spannable);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5"))); //#5DC4EB  // 6BB9F0
		
		
		docName = getIntent().getExtras().getString("Name");
		hospName = getIntent().getExtras().getString("Hospital");
		
		TextView doctName = (TextView) findViewById(R.id.docName_detailActivity);
		TextView HosptName = (TextView) findViewById(R.id.hospName_detailActivity);
		
		doctName.setText(docName);
		HosptName.setText(hospName);
		
		
		bookmarkDoc = (CheckBox) findViewById(R.id.bookmark);
		
		bookmarkDoc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				// save docName, HospName & Phone number on disk
				
				saveBookmark();
				
			}
		});
		
    	 SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
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
         TextView t = (TextView) findViewById(R.id.name);
         
         setMapFrag();
         

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
            	 Toast.makeText(this, "NULL mapFragment", Toast.LENGTH_SHORT).show();
        	 
	         // filling map fragment 
	         getSupportFragmentManager().beginTransaction().replace(R.id.mapFragPlaceHolder, new MapsFragment()).commit();
	         
	        

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
     		    	Toast.makeText(getApplicationContext(), "No information exists to be saved", Toast.LENGTH_SHORT).show();
     		    	
     		    	return;
     		    }
     	
     		    String fileName =filePath.concat(docName+".txt");

     		    FileOutputStream fos = new FileOutputStream(fileName, false);
     		    
     		    if(phoneNum == null ||phoneNum.isEmpty())
     		    	phoneNum = "115"; // if number not avaialable, I have assigned Eidhi number for emergency calls
     		   
     		    String data = docName+"\n"+hospName+"\n"+phoneNum;
     		    
     		    fos.write(data.getBytes());
     		    fos.close();
     		    
     		    
     		    Toast.makeText(getApplicationContext(), "Bookmark Saved", Toast.LENGTH_SHORT).show();
     		}
     		catch(Exception e)
     		{
     			Log.e("DocSeek", e.toString());

     		}
     		
     	}
        
        @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        
			 onBackPressed();
			
			return super.onOptionsItemSelected(item);
	    }
    	 
}
    
    
    

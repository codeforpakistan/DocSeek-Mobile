package com.wingoku.docseek;

import java.util.List;
import java.util.Vector;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.internal.v;
import com.google.android.gms.maps.GoogleMap;

public class ViewPager_DocAndMap extends SherlockFragmentActivity {

	private static final String TAG = "DemoActivity";

    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";

    private GoogleMap googleMap, mMap;

    ListView userReviewsList;
   
    CheckBox bookmarkDoc;
    
    String docName, hospName, phoneNum;
    
    ViewPager vPager;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);  
		
    	// View parentView = inflater.inflate(R.layout.map_and_doc_detail, container,false);
		setContentView(R.layout.viewpager_layout);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		SpannableString spannable = new SpannableString("DocSeek");
		spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#F0F0F0")), 0, "DocSeek".length(), 0);
		
		getSupportActionBar().setTitle(spannable);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5"))); //#5DC4EB  // 6BB9F0
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
	
		
		initViewPager();
		
		createTabs();
	}
	
	
	
	private void createTabs()
	{
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				
				// change fragment based on selected tab
				vPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				
				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				
				
			}
		};
		
		// Create first Tab
		Tab tab = getSupportActionBar().newTab().setText("Doc Info").setTabListener(tabListener);
		getSupportActionBar().addTab(tab);

		// Create second Tab
		tab = getSupportActionBar().newTab().setText("Reviews").setTabListener(tabListener);
		getSupportActionBar().addTab(tab);		
	}
	
	
	private void initViewPager()
	{
		List<SherlockFragment> fragments = new Vector<SherlockFragment>();
		
		fragments.add(new ViewPager_DocAndMap_Frag());

		fragments.add(new ViewPager_PatientReview_Frag());
		
		com.wingoku.docseek.PagerAdapter pagerAdapter = new com.wingoku.docseek.PagerAdapter(getSupportFragmentManager(), fragments);
	
		vPager = (ViewPager)findViewById(R.id.viewpager);
		vPager.setAdapter(pagerAdapter);
		
		vPager.setPageTransformer(true, new ZoomOutPageTransformer());
	}
	
	
	
	
	private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
	    private static final float MIN_SCALE = 0.85f;
	    private static final float MIN_ALPHA = 0.5f;

	    public void transformPage(View view, float position) {
	        int pageWidth = view.getWidth();
	        int pageHeight = view.getHeight();

	        if (position < -1) { // [-Infinity,-1)
	            // This page is way off-screen to the left.
	            view.setAlpha(0);

	        } else if (position <= 1) { // [-1,1]
	            // Modify the default slide transition to shrink the page as well
	            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
	            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
	            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
	            if (position < 0) {
	                view.setTranslationX(horzMargin - vertMargin / 2);
	            } else {
	                view.setTranslationX(-horzMargin + vertMargin / 2);
	            }

	            // Scale the page down (between MIN_SCALE and 1)
	            view.setScaleX(scaleFactor);
	            view.setScaleY(scaleFactor);

	            // Fade the page relative to its size.
	            view.setAlpha(MIN_ALPHA +
	                    (scaleFactor - MIN_SCALE) /
	                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

	        } else { // (1,+Infinity]
	            // This page is way off-screen to the right.
	            view.setAlpha(0);
	        }
	    }
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		onBackPressed();

		return super.onOptionsItemSelected(item);
	}
}

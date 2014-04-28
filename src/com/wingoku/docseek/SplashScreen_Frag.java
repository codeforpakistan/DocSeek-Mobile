package com.wingoku.docseek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class SplashScreen_Frag extends FragmentSuperClass{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.splash_screen_frag, container, false);
	}
	
	@Override
	public void visibleFragmentName(String name) {
		super.visibleFragmentName("SplashScreen");
	}
	
}

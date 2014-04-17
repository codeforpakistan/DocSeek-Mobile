package com.wingoku.docseek;

import java.io.IOException;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckAvailabilityOfInternet {

	public boolean checkAvailabilityOfInternet(Context con)
	{
		ConnectivityManager man = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo wifiInfo = man.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 

		NetworkInfo mobileInternetInfo = man.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		
			if(wifiInfo.getState() == NetworkInfo.State.CONNECTED || mobileInternetInfo.getState() == NetworkInfo.State.CONNECTED)
				return true;


		return false;
	}
}

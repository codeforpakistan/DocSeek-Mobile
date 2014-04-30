package com.wingoku.docseek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ViewPager_PatientReview_Frag extends SherlockFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.viewpager_patient_review_list, container,false);
		
		ListView patientReviewsList = (ListView) view.findViewById(R.id.patientReviewsList);
        
        Parse_InsertData getData = new Parse_InsertData(getActivity(), R.id.patientReviewsList, patientReviewsList, false);
		
        getData.execute();
		
		return view;
	}
}

package com.wingoku.docseek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class DocList extends SherlockFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View view = inflater.inflate(R.layout.doc_list, container,false);
		
		ListView myList = (ListView) view.findViewById(R.id.doc_listView);
		
		Parse_InsertData getData = new Parse_InsertData(getActivity().getApplicationContext(), R.id.doc_listView, myList);
		
		getData.execute();
		
		return view;
	}
}

package com.wingoku.docseek;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class Search_Frag extends SherlockFragment{
	
	ArrayAdapter<String> adapter;
	AutoCompleteTextView searchBox;
	
	Button searchButton;
	
	public static String selectedProfession;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.searchbox_frag, container, false);
		
		setListeners(view);
		
		return view;
	}
	
	
	public void setListeners(View view)
	{
		searchButton = (Button) view.findViewById(R.id.searchBut);
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				startSearch();
			}
		});
		
		searchBox = (AutoCompleteTextView) view.findViewById(R.id.autoCom_Frag);
		
		searchBox.setThreshold(1); 
		
		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.custom_autocomplete);
		searchBox.setAdapter(adapter);
		
				
		searchBox.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						
						new Thread(new Task()).start();
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
		
		
		searchBox.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				completeInputOperation();
				
			}
		});
		
		searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		        	
					
		        	completeInputOperation();
					startSearch();
					
		            return true;
		        }
		        return false;
		    }
		});

	}
	
		
	class Task implements Runnable {
			
			String[] suggestionList;
			
			Handler handler = new Handler();
			
			Context con = getActivity().getApplicationContext();
			
			@Override
			public void run() {
				
				try {
					
					suggestionList = forSuggestions(con, searchBox.getText().toString());
					
					System.out.println("String "+ suggestionList[0]);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				} 
			
				handler.post(new Runnable() {
						@Override
						public void run() {
							
							if(suggestionList != null)
							{
								Toast.makeText(con, "Contains Data", Toast.LENGTH_SHORT).show();
								
								adapter.clear();
								adapter.addAll(suggestionList);
								searchBox.invalidate();
								
								for(int i=0; i<suggestionList.length;i++)
								{
									System.out.println(suggestionList[i]);
									
									//adapter.add(suggestionList[i]);
								}
							}
						}
					});
				}
			}
	
		
	// for autoCompleteTextView suggestiosn
	public String[] forSuggestions(Context context, String text) throws ClientProtocolException, IOException, URISyntaxException, JSONException
	{
		if(new CheckAvailabilityOfInternet().checkAvailabilityOfInternet(context))
		{
			//System.out.println(phpScriptAddress);
			DownloadData internet = new DownloadData(new URI("http://wingoku.bugs3.com/suggestions.php?suggestion="+text), context); //"http://wingoku.bugs3.com/uetapp/getassignmentslist.php"
			
			String sb = internet.divideStrings();
			
			JSONArray array = new JSONArray(sb);
			
			String[] dataList = new String[array.length()];
			
			int length = array.length();
			
			for(int i=0;i < length; i++)
			{
				JSONObject temp = array.getJSONObject(i);
				
				dataList[i]= temp.getString("Cadre"); // title is the tag in JsonFormat data which contains a value. using temp.getString("title") we get the 
				// the value stored in title tag in jsonFormat data
				
			}
			
			return dataList;
		}
		
		return null;
	
	}

	
	public void completeInputOperation()
	{
		searchBox.performCompletion();
    	
    	// for hiding keyboard
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0); 
	}
	
	public void startSearch()
	{
		selectedProfession = searchBox.getText().toString();
		
		// removing space from the end of the string selected by user because for some reason space at the end causes DB php script to return nothing
		for(int i = selectedProfession.length()-1; i>=0; i--)
		{
			if(!Character.isWhitespace(selectedProfession.charAt(i)))
			{
				selectedProfession = selectedProfession.substring(0,i);
				break;
			}
		}

		
		getFragmentManager().beginTransaction().addToBackStack("docList").replace(getId(), new DocList()).commit();
		
		Toast.makeText(getActivity(), selectedProfession, Toast.LENGTH_SHORT).show();
	}
	
}

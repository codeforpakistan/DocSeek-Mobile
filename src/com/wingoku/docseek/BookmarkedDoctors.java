package com.wingoku.docseek;

import java.io.File;
import java.io.FileInputStream;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.internal.bo;

public class BookmarkedDoctors extends FragmentSuperClass{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		visibleFragmentName("BookmarkFrag");
		
		View view = inflater.inflate(R.layout.doc_list, container,false);
		
		final ListView myList = (ListView) view.findViewById(R.id.doc_listView);
		
		final String filePath = Environment.getExternalStorageDirectory().getPath()+"/DocSeek/Bookmarks/";
		
		File file = new File(filePath);
		
		if(!file.exists())
		{
			myList.setAdapter( new CustomAdapter_ForSideMenu(getActivity(), R.id.doc_listView, new String[]{"No Bookmarks Found!"}));
		}
		else
		{
			populateDocBookmarkList(myList, file, filePath);
		}
		
		
		
		return view;
	}
	
	
	private void populateDocBookmarkList(ListView myList, File file, final String filePath)
	{
		final String listOfBookmarks[] = file.list();
		
		for(int i=0; i< listOfBookmarks.length; i++)
		{
			listOfBookmarks[i] = listOfBookmarks[i].replace(".txt", "");
		}
		
		myList.setAdapter( new CustomAdapter_ForSideMenu(getActivity(), R.id.doc_listView, listOfBookmarks));
		
		myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				Dialog bookmarkDocInfo = new Dialog(getActivity());
				
				bookmarkDocInfo.setContentView(R.layout.bookmarked_doc_info);
				
				bookmarkDocInfo.setTitle("Doctor Information");
				
				TextView docName = (TextView) bookmarkDocInfo.findViewById(R.id.bookmarked_docName);
				TextView hospName = (TextView) bookmarkDocInfo.findViewById(R.id.bookmarked_HospName);
				TextView phoneNum = (TextView) bookmarkDocInfo.findViewById(R.id.bookmarked_phoneNum);
				
				
				String data = readData(filePath, listOfBookmarks, position);
				
				if(data != null)
				{					
					String[] subStrings = data.split("\n");

					docName.setText(subStrings[0]);
					hospName.setText(subStrings[1]);
	
					phoneNum.setText(subStrings[2]);
				
					bookmarkDocInfo.show();
				}
				else
					Toast.makeText(getActivity(), "Bookmark empty", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	
	private String readData(String filePath, String[] listOfBookmarks, int position)
	{
		try
		{					
			String docInfoNote = filePath+ listOfBookmarks[position]+".txt"; // appending .txt because I removed it before passing listOfBookmarks to customArrayAdatper
			
			File temp = new File(docInfoNote);
			
			byte buff[] = new byte[(int) temp.length()]; // creating byte[] of length equal to length of file that is number of characaters in bytes
			
			FileInputStream fis = new FileInputStream(temp);
			
			
			fis.read(buff, 0, (int) temp.length());
			
			String data = new String(buff);
			
			fis.close();
			
			return data;
			
		}
		catch(Exception e)
		{
			Log.e("DocSeek", e.toString());
		}
		
		return null;
	}
	
	
}

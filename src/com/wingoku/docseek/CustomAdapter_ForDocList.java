package com.wingoku.docseek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wingoku.docseek.CustomAdapter_ForSideMenu.ViewHolder;

public class CustomAdapter_ForDocList extends ArrayAdapter<String>{

	public class ViewHolder{
		
		TextView text,subText;
		ImageView image;
	}
	
	
	Context context;
	String[] docList, hospList;
	
	public CustomAdapter_ForDocList(Context context, int textViewResourceId, String[] objects, String[] subText) {
		super(context, textViewResourceId, objects);
	
		this.context = context;
		docList = objects;
		hospList = subText;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			viewHolder = new ViewHolder();
			
			convertView = (View) inflater.inflate(R.layout.custom_list_doc_list, parent, false);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.doc_listImage);
			viewHolder.text = (TextView) convertView.findViewById(R.id.doc_mainText);
			viewHolder.subText = (TextView) convertView.findViewById(R.id.doc_subText);
			
			viewHolder.image.setImageResource(R.drawable.wingoku_icon);
			
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.text.setText(docList[position]);
		viewHolder.subText.setText(hospList[position]);
		
		return convertView;
	}

}

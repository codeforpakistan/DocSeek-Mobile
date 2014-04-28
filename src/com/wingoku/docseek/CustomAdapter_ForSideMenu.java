package com.wingoku.docseek;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter_ForSideMenu extends ArrayAdapter<String>{

	public class ViewHolder{
		
		TextView text;
		//ImageView image;
	}
	
	
	Context context;
	String[] data;
	
	public CustomAdapter_ForSideMenu(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
	
		this.context = context;
		data = objects;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			viewHolder = new ViewHolder();
			
			convertView = (View) inflater.inflate(R.layout.custom_list_side_menu, parent, false);
			//viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView1);
			viewHolder.text = (TextView) convertView.findViewById(R.id.textView1);
			
			//viewHolder.image.setImageResource(R.drawable.ic_launcher);
			
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.text.setText(data[position]);
		
		return convertView;
	}

}

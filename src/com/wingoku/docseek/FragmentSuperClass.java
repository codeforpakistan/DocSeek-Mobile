package com.wingoku.docseek;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentSuperClass extends SherlockFragment{

	static String name;
	
	public void visibleFragmentName(String name)
	{
		this.name = name;
	}
}

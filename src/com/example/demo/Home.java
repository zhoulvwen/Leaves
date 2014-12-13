package com.example.demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class Home extends Fragment implements SearchView.OnQueryTextListener{
	private ImageView iv;
	private TextView tv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
			Bundle savedInstanceState) {  
		View view = inflater.inflate(R.layout.home, container, false);  
		iv = (ImageView) view.findViewById(R.id.logo);
		iv.setImageDrawable(getResources().getDrawable(R.drawable.logo));
		return view;
	}

	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


}

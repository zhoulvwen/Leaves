package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Collections extends ListFragment {

	private String TAG = Collections.class.getName();  
	private String[] name;
	private ListView list;  
	private SimpleAdapter adapter;
	private CallBacks mCallBacks;
	public static final String EXTRA_MESSAGE_Collection = "com.example.demo.Collections";
	private int[] imgId;
	private TypedArray ar;
	private String[] infos;
	private String[] names;
	
	public interface CallBacks {
		public void showDetails(int position, int ImgID, String name, String info);
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		mCallBacks=(CallBacks)activity;
	}
	
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
			Bundle savedInstanceState) {  
		View view = inflater.inflate(R.layout.collections, container, false);   
		return view;
	}  

	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		Resources resName = getActivity().getBaseContext().getResources();
		names = resName.getStringArray(R.array.lfsNameArray);
		infos = resName.getStringArray(R.array.lfsInfoArray);
		ar = getActivity().getBaseContext().getResources().obtainTypedArray(R.array.lfsPic);
//		int[] img = {R.drawable.l1, R.drawable.l2, 
//				R.drawable.l3,R.drawable.l4,
//				R.drawable.l5,R.drawable.l6,
//				R.drawable.l7,R.drawable.l8,
//				R.drawable.l9,R.drawable.l10,
//				R.drawable.l11,R.drawable.l12,
//				R.drawable.l13,R.drawable.l14,
//				R.drawable.l15};
		imgId = new int[ar.length()];
		for(int i=0; i<imgId.length; i++){
			imgId[i] =ar.getResourceId(i, 0);
		}
		ar.recycle();
		adapter = new SimpleAdapter(getActivity(), getData(names, imgId, infos), R.layout.item, new String[]{"title", "img", "info"}, new int[]{R.id.lfsNameV, R.id.lfsImgV, R.id.lfsInfoV});  
		setListAdapter(adapter);      
	}

	private List<? extends Map<String, ?>> getData(String[] strs, int[] img, String[] strs2) {  
		List<Map<String ,Object>> list = new ArrayList<Map<String,Object>>();  

		for (int i = 0; i < strs.length; i++) {  
			Map<String, Object> map = new HashMap<String, Object>();  
			map.put("title", strs[i]);  
			map.put("img", img[i]);
			map.put("info", strs2[i]);  
			list.add(map);  
		}  
		return list;  
	}
	
	@Override  
    public void onListItemClick(ListView l, View v, int position, long id) {  
        super.onListItemClick(l, v, position, id);   
        HashMap<String, Object> view= (HashMap<String, Object>) l.getItemAtPosition(position);   
        mCallBacks.showDetails(position, imgId[position], names[position], infos[position]);
    }

}

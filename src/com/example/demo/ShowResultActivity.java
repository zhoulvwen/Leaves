package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.demo.Collections.CallBacks;

public class ShowResultActivity extends Activity implements OnItemClickListener{
	private ImageView imgRGB = null;
	private ImageView imgBW = null;
	private Bitmap bitmap = null;
	private Bitmap bitmapBW = null;

	private String[] names;
	private String[] infos;
	private TypedArray ar;
	private int[] imgId;
	private Resources resName;
	private int[] order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import_pic);
		bitmap = MainActivity.getBitmap();
		bitmapBW = ImageProcess.binarization(bitmap);
		imgRGB=(ImageView) findViewById(R.id.lfsImgSelected);
		imgBW=(ImageView) findViewById(R.id.lfsImgSelectedBW);
		imgRGB.setImageBitmap(bitmap);
		imgBW.setImageBitmap(bitmapBW);

		resName = getResources();
		names = resName.getStringArray(R.array.lfsNameArray);
		infos = resName.getStringArray(R.array.lfsInfoArray);
		ar = getResources().obtainTypedArray(R.array.lfsPic);
		imgId = new int[ar.length()];
		for(int i=0; i<imgId.length; i++){
			imgId[i] =ar.getResourceId(i, 0);
		}
		ar.recycle();
		order = new int[15];
		order = getorder();

		SimpleAdapter simAdapter = new SimpleAdapter(this, getData(names, imgId, infos, order), 
				R.layout.item, new String[]{"title", "img", "info"}, 
				new int[]{R.id.lfsNameV, R.id.lfsImgV, R.id.lfsInfoV});      
		ListView list = (ListView) findViewById(android.R.id.list);
		list.setAdapter(simAdapter);
		list.setOnItemClickListener(this);
	}

	private int[] getorder() {
		// TODO Auto-generated method stub
		int len = names.length;
		int[] order1 = new int [len];
		int[] variance = new int [len];
		Bitmap bitmap1;
		Bitmap bitBW1;
		for(int i=0; i<len; i++){
			bitmap1 = BitmapFactory.decodeResource(resName, imgId[i]);
			bitBW1 = ImageProcess.binarization(bitmap1);
			variance[i] = getVariance(bitBW1);
		}

		HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
		for(int i=0; i<len; i++){
			map.put(variance[i],i);  //将值和下标存入Map
		}
		Arrays.sort(variance);  //升序排列
		for(int i=0; i<len; i++)  {
			order1[i] = (Integer) map.get(variance[i]);
		}
		return order1;
	}

	private int getVariance(Bitmap bitBW1) {
		// TODO Auto-generated method stub
		int width = bitBW1.getWidth();  
		int height = bitBW1.getHeight();  
		int width1 = bitmapBW.getWidth(); 
		int height1 = bitmapBW.getHeight();
		double scale =width1/(float)width;
		int diff=0;
		int variance=0;
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++){
				if(i*scale<width1 && j*scale<height1){
					diff = bitBW1.getPixel(i, j) - bitmapBW.getPixel((int)(i*scale), (int)(j*scale));				
				}

				if(diff!=0) variance++;
			}
		return variance;
	}

	private List<? extends Map<String, ?>> getData(String[] strs, int[] img, String[] strs2, int[] order) {  
		List<Map<String ,Object>> list = new ArrayList<Map<String,Object>>();  

		for (int i = 0; i < strs.length; i++) {  
			Map<String, Object> map = new HashMap<String, Object>();  
			map.put("title", strs[order[i]]);  
			map.put("img", img[order[i]]);
			map.put("info", strs2[order[i]]);  
			list.add(map);  
		}  
		return list;  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.import_pic, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//CallBacks mCallBacks = (CallBacks)getApplication();
		//mCallBacks.showDetails(position, imgId[position], names[position], infos[position]);
		Intent intent = new Intent(this, DetailsActivity.class);
		Bundle b = new Bundle();
		b.putString("name", names[order[position]]);
		b.putString("info", infos[order[position]]);
		b.putInt("img", imgId[order[position]]);
		intent.putExtra(MainActivity.EXTRA_MESSAGE, b);
		Log.v("tag", "mainsendposition");
		startActivity(intent);
	}


}

package com.example.demo;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.demo.Collections.CallBacks;

public class MainActivity extends ActionBarActivity implements CallBacks{

	public static final String EXTRA_MESSAGE = "com.example.demo.MESSAGE";
	private ActionBar actionbar = null;
	public static Bitmap bitmap=null;
	
	public static Bitmap getBitmap(){
		return bitmap;
		}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("tag", "main");
		//setContentView(R.layout.activity_main);

		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		setOverflowShowingAlways();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		Tab tab = actionbar  
				.newTab()  
				.setText(R.string.Home) 
				.setTabListener(  
						new TabListener<Home>(this, "Home",
								Home.class));  
		actionbar.addTab(tab);
		tab = actionbar  
				.newTab()  
				.setText(R.string.Collections)  
				.setTabListener(  
						new TabListener<Collections>(this, "Collections",
								Collections.class));  
		actionbar.addTab(tab);
	}

	public void dialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this)
		.setTitle(R.string.optionAlterTitle).setItems(new String[]{"拍照","从相册导入"}, new DialogInterface.OnClickListener(){
			@Override  
			public void onClick(DialogInterface dialog, int position) {  
				Intent intent=null;
				if(position==0){
					intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					startActivityForResult(intent, 0);
				}
				else {
					intent = new Intent(Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  
					startActivityForResult(intent, 1);
				}
			}
		});
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Intent intent = new Intent(getApplicationContext(), ShowResultActivity.class);
		if(requestCode==1){
			Uri selectedImage = data.getData();
			ContentResolver cr = this.getContentResolver(); 
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(selectedImage));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startActivity(intent);
		}
		else if(requestCode==0){
			Bundle bundle = data.getExtras();
			bitmap = (Bitmap) bundle.get("data"); //get bitmap 
			startActivity(intent);
		}
	}


	@Override
	public void showDetails(int position, int ImgID, String name, String info) {
		Intent intent = new Intent(this, DetailsActivity.class);
		Bundle b = new Bundle();
		b.putString("name", name);
		b.putString("info", info);
		b.putInt("img", ImgID);
		String message = "you choos item "+position;
		intent.putExtra(EXTRA_MESSAGE, b);
		Log.v("tag", "mainsendposition");
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
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
		else if(id == R.id.action_photo){
			dialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

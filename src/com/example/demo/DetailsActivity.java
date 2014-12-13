package com.example.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {
	private TextView rName = null;
	private TextView rInfo = null;
	private ImageView rImg = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("show", "showaactivity");
		setContentView(R.layout.activity_details);
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra(MainActivity.EXTRA_MESSAGE);
		String name = b.getString("name");
		String info = b.getString("info");
		int imgID = b.getInt("img");
		rName=(TextView) findViewById(R.id.lfsNameD);
		rInfo=(TextView) findViewById(R.id.lfsInfoD);
		rImg=(ImageView) findViewById(R.id.lfsImgD);
		rName.setText(name);
		rInfo.setText(info);
		rImg.setImageDrawable(getResources().getDrawable(imgID));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
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
}

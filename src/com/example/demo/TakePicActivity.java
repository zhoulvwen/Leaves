package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TakePicActivity extends Activity {
	private TextView receiveMsg = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_pic);
		Log.v("show", "showaactivity");
		setContentView(R.layout.activity_take_pic);
		Intent intent = getIntent();
//		Bundle bundle=intent.getExtras();
//		String name=bundle.getString(Collections.EXTRA_MESSAGE_Collection);
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		receiveMsg=(TextView) findViewById(R.id.takePic);
		receiveMsg.setText(message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_pic, menu);
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

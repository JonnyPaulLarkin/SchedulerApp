package com.marcom.scheduler;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
// import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class EventActivity extends Activity {
	
	public final static String CLUB_ID_EVENT = "com.marcom.scheduler.CLUBNAME";
	ListView eventListView;
	DatabaseHandler db;
	ArrayList<String> events;
	ArrayAdapter<String> adapter;
	String clubid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// change title text view
		Intent intent = getIntent();
		String title = intent.getStringExtra(ClubActivity.CLUB_NAME);
		TextView tv = (TextView) findViewById(R.id.clubNameField);
		tv.setText(title);
		clubid = title;
		
		// refresh the list
		eventListView = (ListView) findViewById(R.id.eventList);
		db = new DatabaseHandler(this);
				
		events = db.getEventsById(clubid);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				events);
		eventListView.setAdapter(adapter);
		
		eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				String eventToRemove = (String) parent.getItemAtPosition(pos);
				//Log.v("event", eventToRemove);
				
				db.deleteEvent(clubid, eventToRemove);
				
				onResume();
				return true;
			}
		});
		
		db.close();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// refresh the list
		eventListView = (ListView) findViewById(R.id.eventList);
		db = new DatabaseHandler(this);
		
		events = db.getEventsById(clubid);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				events);
		eventListView.setAdapter(adapter);
		
		db.close();
	}
	
	public void addEvent(View view) {
		Intent intent = new Intent(this, AddEventActivity.class);
		intent.putExtra(CLUB_ID_EVENT, clubid);
		startActivity(intent);
	}

}

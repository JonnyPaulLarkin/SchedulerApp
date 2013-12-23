package com.marcom.scheduler;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ClubActivity extends Activity {
	
	ListView clubListView;
	DatabaseHandler db;
	ArrayList<String> clubs;
	ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        
        clubListView = (ListView) findViewById(R.id.clubListView);
        db = new DatabaseHandler(this);
        clubs = db.getAllClubs();
        
        adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1,
        		clubs);
        clubListView.setAdapter(adapter);
        
        clubListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				String clubToRemove = (String) parent.getItemAtPosition(pos);
				db.removeClub(clubToRemove);
				
				// update
				onResume();
			}
        	
		});
        
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.club, menu);
        return true;
    }
    
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	clubListView = (ListView) findViewById(R.id.clubListView);
        db = new DatabaseHandler(this);
        clubs = db.getAllClubs();
        db.close();
        
        adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1,
        		clubs);
        clubListView.setAdapter(adapter);
    }
    
    public void addClass(View view) {
    	Intent intent = new Intent(this, AddClubActivity.class);
    	startActivity(intent);
    }
}
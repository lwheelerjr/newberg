package com.spiritflightapps.newberg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;


public class TestListActivity extends Activity {

    ListView list;
    TestListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        Intent intent = getIntent();
        String patientId = intent.getStringExtra("patientId");

        list = (ListView)findViewById(R.id.list);

        adapter = new TestListAdapter(this, Long.parseLong(patientId));

        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_new:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = (int) info.id;
                adapter.addTest("New Test");
                return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        adapter.saveTests();
    }



}
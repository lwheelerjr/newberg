package com.spiritflightapps.newberg;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CustomizedListView extends Activity {

    EditText inputNewStock;
    ListView list;
    LazyListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.list);

        adapter = new LazyListAdapter(this, list);
        adapter.readData();

        list.setAdapter(adapter);

        // Click event for single gridView row
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO: GOTO STOCK DETAILS ACTIVITY
            }
        });

        list.requestFocus();

        inputNewStock = (EditText) findViewById(R.id.addStockSymbol);
        inputNewStock.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    EditText inputNewStock = (EditText) findViewById(R.id.addStockSymbol);
                    adapter.addNewStock(inputNewStock.getText().toString().toUpperCase());
                    inputNewStock.setText("");
                    handled = true;
                }
                return handled;
            }
        });

        registerForContextMenu(list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_item:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = (int) info.id;
                adapter.deleteStock(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        adapter.saveData();
    }



}
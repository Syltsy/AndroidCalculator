package com.example.metropolia.calculator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int ACTIVITY_SELECT;
    private MainActivityFragment fragment;
    private DBAdapter db;
    private Cursor c;
    private final static String DEBUG_TAG = "myCalculator";

    public MainActivity() {
        ACTIVITY_SELECT = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fragment = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    protected void onactivitycreated(Bundle savedinstancestate){
        db = new DBAdapter( this);

        /*initDatabase();
        readDatabase(rowId 1);*/

        db.openForReading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onCalculate(View view){
        if (fragment != null)
            fragment.onCalculate(view); //Kun nappia painetaan
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.action_list:
                Toast.makeText(this, "Just select", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, ListView.class); // create intent object for starting an activity
                startActivityForResult(i, ACTIVITY_SELECT); // start an activity
                return true;
            case R.id.action_add:
                Toast.makeText(this, "Just add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.clear:
                Toast.makeText(this, "Just clear", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_update:
                Toast.makeText(this, "Just update", Toast.LENGTH_SHORT.show();

            int id = item.getItemId();

            if (id == R.id.action_settings) {
                return true;
            } else if (id == R.id.action_list) {

            }
        }return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode==ACTIVITY_SELECT)
            if (resultCode==RESULT_OK) {
                Toast.makeText(this, "Selection made", Toast.LENGTH_SHORT).show();
            }
    }
}

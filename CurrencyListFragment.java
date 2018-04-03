package com.example.metropolia.calculator;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class CurrencyListFragment extends ListFragment {
	
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        String[] Headlines = {
            "USD",
            "SEK",
            "GBP",
            "DKK"
        };
        
        // Create an array adapter for the list view, using the Headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Headlines));
    }

	@Override
	public void onStart(){
		super.onStart();
			
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        
        Toast.makeText(getActivity().getApplicationContext(), "Selected: "+position, Toast.LENGTH_LONG).show(); 
        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }
}

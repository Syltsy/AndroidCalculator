package com.example.fragmentfromscratch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class FormFragment extends Fragment {
	
	private Button mChange;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculate_form, container, false);
        
    }
	
	@Override
	public void onStart(){
		super.onStart();
		// During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
		
		mChange = (Button) getView().findViewById(R.id.button2);
		mChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// move to another fragment
				CurrencyListFragment firstFragment = new CurrencyListFragment();
		        // Add the fragment to the 'fragment_container' FrameLayout
		        FragmentTransaction transaction = getFragmentManager().beginTransaction();
		        //transaction.addToBackStack(null);
		        transaction.replace(R.id.fragment_container, firstFragment).commit(); 
		        
				
			}
		});
	}
	

}

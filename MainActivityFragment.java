package com.example.metropolia.calculator;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//A placeholder fragment containing a simple view.

public class MainActivityFragment extends Fragment {

    Button mChange;
    EditText mUpperText;
    EditText mLowerText;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container,false);

        mUpperText = v.findViewById(R.id.editText2);
        mLowerText = v.findViewById(R.id.editText3);
        return v;
    }

    public void onCalculate(View view) {
        Toast.makeText(getActivity(), "Let's Calculate!", Toast.LENGTH_LONG).show();
    }

    //if (findViewById(R.id.fragment_container) != null)

    {
        // However, if we're being restored from a previous state, then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.

        // create Calculator-fragment programmatically...
        CalculatorFragment newFragment = new CalculatorFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.add(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null); // to get back from ListFragment
        // Commit the transaction
        transaction.commit();
    }
}

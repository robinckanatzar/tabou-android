package com.robinkanatzar.android.rck.tabou;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SwitchFragment extends Fragment {

    // ---------------------------------------------------------------------------------------------
    // onCreateView Method for Fragment
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.switch_team_view, container, false);
    }
}

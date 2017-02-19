package com.robinkanatzar.android.rck.tabou;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EndGameFragment extends Fragment {

    TextView mWinnerName;

    // ---------------------------------------------------------------------------------------------
    // Creates fragment at the end of the game
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.end_game_view, container, false);

        mWinnerName = (TextView) view.findViewById(R.id.textViewTaboo1);

        return view;
    }


    public void setWinnerName(String winner) {
        mWinnerName.setText(winner);
    }

}
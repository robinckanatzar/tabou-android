package com.robinkanatzar.android.rck.tabou;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardFragment extends android.app.Fragment{

    // ---------------------------------------------------------------------------------------------
    // Declarations
    // ---------------------------------------------------------------------------------------------
    TextView mGuessWord;
    TextView mTaboo1;
    TextView mTaboo2;
    TextView mTaboo3;
    TextView mTaboo4;
    TextView mTaboo5;
    DataManager dm;
    int mLevel = 3;

    // ---------------------------------------------------------------------------------------------
    // onCreate Method for creating a card fragment
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_view, container, false);

        // -----------------------------------------------------------------------------------------
        // Link to widgets on view
        // -----------------------------------------------------------------------------------------
        mGuessWord = (TextView) view.findViewById(R.id.textViewFrenchWord);
        mTaboo1 = (TextView) view.findViewById(R.id.textViewTaboo1);
        mTaboo2 = (TextView) view.findViewById(R.id.textViewTaboo2);
        mTaboo3 = (TextView) view.findViewById(R.id.textViewTaboo3);
        mTaboo4 = (TextView) view.findViewById(R.id.textViewTaboo4);
        mTaboo5 = (TextView) view.findViewById(R.id.textViewTaboo5);
        dm = new DataManager(getActivity());
        mLevel = GameActivity.getLevel();

        // -----------------------------------------------------------------------------------------
        // Set values of text views to a default value ""
        // -----------------------------------------------------------------------------------------
        mGuessWord.setText("");
        mTaboo1.setText("");
        mTaboo2.setText("");
        mTaboo3.setText("");
        mTaboo4.setText("");
        mTaboo5.setText("");

        // -----------------------------------------------------------------------------------------
        // Call method to get a random word and set of taboo words for the card
        // -----------------------------------------------------------------------------------------
        showNewWord(dm.getNewWord());

        return view;
    }

    // ---------------------------------------------------------------------------------------------
    // Method to show a set of words from a db cursor value
    // ---------------------------------------------------------------------------------------------
    public void showNewWord(Cursor c) {

        // -----------------------------------------------------------------------------------------
        // Declare and set default value for strings
        // -----------------------------------------------------------------------------------------
        String c1 = "";
        String c2 = "";
        String c3 = "";
        String c4 = "";
        String c5 = "";
        String c6 = "";

        // -----------------------------------------------------------------------------------------
        // Set string values from the cursor
        // -----------------------------------------------------------------------------------------
        c1 = c.getString(1);
        c2 = c.getString(2);
        c3 = c.getString(3);
        c4 = c.getString(4);
        c5 = c.getString(5);
        c6 = c.getString(6);

        // -----------------------------------------------------------------------------------------
        // Set values of text fields to the cursor values above
        // -----------------------------------------------------------------------------------------
        mGuessWord.setText(c1);
        mTaboo1.setText(c2);
        mTaboo2.setText(c3);
        mTaboo3.setText(c4);
        mTaboo4.setText(c5);
        mTaboo5.setText(c6);

        // -----------------------------------------------------------------------------------------
        // Send the main word to GameActivity to use in the translate dialog
        // -----------------------------------------------------------------------------------------
        GameActivity.setWordToTranslate(c1);

        // -----------------------------------------------------------------------------------------
        // Based on the level, clear a few of the taboo words
        // -----------------------------------------------------------------------------------------
        if (mLevel == 1) {
            mTaboo4.setText("");
            mTaboo5.setText("");
        } else if (mLevel == 2) {
            mTaboo5.setText("");
        }
    }
}
package com.robinkanatzar.android.rck.tabou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class StartGameActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    // Declare Variables
    // ---------------------------------------------------------------------------------------------
    Button mButtonStartGame;
    Spinner spinner;
    RadioGroup mRadioGroup;
    int buttonIDSelected;

    // ---------------------------------------------------------------------------------------------
    // onCreateView Method for Activity
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        // -----------------------------------------------------------------------------------------
        // Link items to the widgets on the screen
        // -----------------------------------------------------------------------------------------
        mButtonStartGame = (Button) findViewById(R.id.buttonStartGame);
        spinner = (Spinner) findViewById(R.id.spinnerNumPlayers);
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        // -----------------------------------------------------------------------------------------
        // onClickListener for "Start Game" button
        // -----------------------------------------------------------------------------------------
        mButtonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int numPlayers = 3;
                int level = 0;

                String text = findSpinnerValue();
                numPlayers = Integer.parseInt(text);

                buttonIDSelected = mRadioGroup.getCheckedRadioButtonId();

                if (buttonIDSelected == (R.id.radioButtonEasy)) {
                    level = 1;
                } else if (buttonIDSelected == (R.id.radioButtonMedium)) {
                    level = 2;
                } else if (buttonIDSelected == (R.id.radioButtonHard)) {
                    level = 3;
                }

                Intent intent = new Intent(StartGameActivity.this, GameActivity.class);
                intent.putExtra("numPlayers", numPlayers);
                intent.putExtra("level", level);
                startActivity(intent);
            }
        });

        // -----------------------------------------------------------------------------------------
        // Add values for the spinner options
        // -----------------------------------------------------------------------------------------
        List<String> numPlayers = new ArrayList<String>();
        numPlayers.add("4");
        numPlayers.add("6");
        numPlayers.add("8");
        numPlayers.add("10");
        numPlayers.add("12");

        // -----------------------------------------------------------------------------------------
        // Listener for items selected on spinner
        // -----------------------------------------------------------------------------------------
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // -----------------------------------------------------------------------------------------
        // Final commands to set up spinner
        // -----------------------------------------------------------------------------------------
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numPlayers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    // ---------------------------------------------------------------------------------------------
    // Method that finds the current value of the spinner (number of players)
    // ---------------------------------------------------------------------------------------------
    public String findSpinnerValue() {
        return spinner.getSelectedItem().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // ---------------------------------------------------------------------------------------------
    // Method called when an item on the menu is selected
    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // -----------------------------------------------------------------------------------------
        // If "Settings" selected from menu
        // -----------------------------------------------------------------------------------------
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        // -----------------------------------------------------------------------------------------
        // If "About" selected from menu
        // -----------------------------------------------------------------------------------------
        if (id == R.id.about_settings) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

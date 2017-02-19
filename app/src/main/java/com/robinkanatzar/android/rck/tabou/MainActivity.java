package com.robinkanatzar.android.rck.tabou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

public class MainActivity extends AppCompatActivity {
/*
    Final Project Requirements:
        Best Practices
        MVC - Model View Controller Design Pattern
        Fragments
        Action Bar Menu (think settings, or about section of your app)
        Log In Screen (Data saved to shared preferences)
        Save user data in some way...even if it’s just login information.
        Some type of graphics (ie. ImageView, gallery, etc.)
        Pull some type of data down from a database/API/etc. (TRANSLATE)
        Use at least 2 - 3rd party libraries.  (Butter Knife, RxJava, ect., etc.)
        Bonus: Add support for Portrait and Landscape devices.
*/

    // ---------------------------------------------------------------------------------------------
    // Declare variables
    // ---------------------------------------------------------------------------------------------
    Button mButtonLogin;
    Button mButtonPlayTabou;
    TextView mTextViewTitle;
    TextView mTextViewTagLine;

    // ---------------------------------------------------------------------------------------------
    // onCreate method for the main/intro screen
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -----------------------------------------------------------------------------------------
        // Link widgets on screen
        // -----------------------------------------------------------------------------------------
        mButtonLogin = (Button) findViewById(R.id.buttonLogin);
        mButtonPlayTabou = (Button) findViewById(R.id.buttonPlayTabou);
        mTextViewTitle = (TextView) findViewById(R.id.textView);
        mTextViewTagLine = (TextView) findViewById(R.id.textView2);

        // TODO change more fonts like this

        mTextViewTitle.setTypeface(EasyFonts.robotoRegular(this));
        mTextViewTagLine.setTypeface(EasyFonts.robotoRegular(this));
        mButtonLogin.setTypeface(EasyFonts.robotoRegular(this));
        mButtonPlayTabou.setTypeface(EasyFonts.robotoRegular(this));

        // -----------------------------------------------------------------------------------------
        // OnClickListener for the "Login" button
        // -----------------------------------------------------------------------------------------
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FBMainActivity.class);
                startActivity(intent);
            }
        });

        // -----------------------------------------------------------------------------------------
        // OnClickListener for the "Play" button
        // -----------------------------------------------------------------------------------------
        mButtonPlayTabou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartGameActivity.class);
                startActivity(intent);
            }
        });
    }

    // ---------------------------------------------------------------------------------------------
    // Method called to display the menu on the action bar
    // ---------------------------------------------------------------------------------------------
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

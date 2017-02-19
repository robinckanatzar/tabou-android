package com.robinkanatzar.android.rck.tabou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView mDescription;
    // ---------------------------------------------------------------------------------------------
    // onCreate Method
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mDescription = (TextView) findViewById(R.id.textViewAbout);
        mDescription.setMovementMethod(new ScrollingMovementMethod());


    }
}

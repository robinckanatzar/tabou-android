package com.robinkanatzar.android.rck.tabou;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public static final int ONE = 1;
    public static final int TWO = 2;
    //public static final int THREE = 3;

    private int mBackgroundOption;

    Button mButtonSaveSettings;

    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mButtonSaveSettings = (Button) findViewById(R.id.buttonSaveSettings);

        // set up shared preferences file and editor
        mPrefs = getSharedPreferences("Tabou", MODE_PRIVATE);
        mEditor = mPrefs.edit();

        // Now for the radio buttons
        mBackgroundOption = mPrefs.getInt("background option", ONE);

        // Deselect all buttons
        mRadioGroup.clearCheck();

        // Which radio button should be selected
        switch(mBackgroundOption){
            case ONE:
                mRadioGroup.check(R.id.radioButtonColor1);
                break;
            case TWO:
                mRadioGroup.check(R.id.radioButtonColor2);
                break;
            //case THREE:
                //mRadioGroup.check(R.id.radioButtonColor3);
                //break;
        }

        //Toast.makeText(SettingsActivity.this, "mBackgroundOptions = " + mBackgroundOption, Toast.LENGTH_SHORT).show();

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    switch (rb.getId()){
                        case R.id.radioButtonColor1:
                            mBackgroundOption = ONE;
                            break;
                        case R.id.radioButtonColor2:
                            mBackgroundOption = TWO;
                            break;
                        //case R.id.radioButtonColor3:
                            //mBackgroundOption = THREE;
                            //break;
                    }
                    // End switch block
                    mEditor.putInt("background option", mBackgroundOption);
                }
            }
        });

        mButtonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mEditor.commit();

                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the settings here
        mEditor.commit();
    }
}

package com.robinkanatzar.android.rck.tabou;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.Utils;
import com.github.jinatonic.confetti.confetto.Confetto;
import com.github.jinatonic.confetti.confetto.ShimmeringConfetto;

import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements ConfettoGenerator {

    // ---------------------------------------------------------------------------------------------
    // declare variables
    // ---------------------------------------------------------------------------------------------
    int mNumTurns;
    int mNumPlayers;
    static int mLevel;
    CardFragment mCardFragment = new CardFragment();
    int mTeam1PointsInt = 0;
    int mTeam2PointsInt = 0;
    TextView mTeam1Points;
    TextView mTeam2Points;
    TextView mCurrentTeam;
    Button mButtonYes;
    Button mButtonNo;
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public TextView textTimer;
    private long startTime = 60 * 1000;
    private final long interval = 1 * 1000;
    static String mEnglishWord = "";
    String mWinner;
    private SharedPreferences mPrefs;
    private int mBackgroundOption;
    TextView mTeam1Title;
    TextView mTeam2Title;

    ViewGroup rootViewGroup;
    private int size;
    private int velocitySlow, velocityNormal;
    private List<Bitmap> confettoBitmaps;
    protected int goldDark, goldMed, gold, goldLight;
    protected int[] colors;

    // ---------------------------------------------------------------------------------------------
    // onCreate Method
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // -----------------------------------------------------------------------------------------
        // Link items to the widgets on the screen and set values
        // -----------------------------------------------------------------------------------------
        textTimer = (TextView) findViewById(R.id.timer);
        mTeam1Points = (TextView) findViewById(R.id.team1score);
        mTeam2Points = (TextView) findViewById(R.id.team2score);
        mTeam1Points.setText(mTeam1PointsInt + "");
        mTeam2Points.setText(mTeam2PointsInt + "");
        mCurrentTeam = (TextView) findViewById(R.id.textViewCurrentTeam);
        mCurrentTeam.setText("TEAM 1");
        mButtonYes = (Button) findViewById(R.id.buttonYes);
        mButtonNo = (Button) findViewById(R.id.buttonNo);


        mNumTurns = 1;
        mNumPlayers = getIntent().getExtras().getInt("numPlayers", -1);
        mLevel = getIntent().getExtras().getInt("level", -1);
        mTeam1Title = (TextView) findViewById(R.id.team1Title);
        mTeam2Title = (TextView) findViewById(R.id.team2Title);

        rootViewGroup = (ViewGroup) findViewById(R.id.gameFrame).getRootView();
        final Resources res = getResources();
        size = res.getDimensionPixelSize(R.dimen.default_confetti_size);
        velocitySlow = res.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        velocityNormal = res.getDimensionPixelOffset(R.dimen.default_velocity_normal);
        goldDark = res.getColor(R.color.gold_dark);
        goldMed = res.getColor(R.color.gold_med);
        gold = res.getColor(R.color.gold);
        goldLight = res.getColor(R.color.gold_light);
        colors = new int[] { goldDark, goldMed, gold, goldLight };

        final int[] colors = { Color.BLACK };

        // set background based on settings activity
        mPrefs = getSharedPreferences("Tabou", MODE_PRIVATE);
        mBackgroundOption = mPrefs.getInt("background option", SettingsActivity.ONE);

        View someView = findViewById(R.id.gameFrame);

        //Toast.makeText(GameActivity.this, "mBackgroundOption = " + mBackgroundOption, Toast.LENGTH_SHORT).show();

        // Find the root view
        View root = someView.getRootView();

        // Set the rate of flash based on settings
        if(mBackgroundOption == SettingsActivity.ONE){
            // set background to color 1
            //root.setBackgroundColor(getResources().getColor(yellow_dark));
            startTime = 30 * 1000;
        }else if(mBackgroundOption == SettingsActivity.TWO){
            // set background to color 2
            //root.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            //root.setBackgroundColor(Color.parseColor("#000000"));

        } //else if (mBackgroundOption == SettingsActivity.THREE) {
        // set background to color 3
        //root.setBackgroundColor(getResources().getColor(R.color.grey));
        //}

        // -----------------------------------------------------------------------------------------
        // Start the countdown timer
        // -----------------------------------------------------------------------------------------
        countDownTimer = new MyCountDownTimer(startTime, interval);
        textTimer.setText(String.valueOf(startTime / 1000));

        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
        }

        // -----------------------------------------------------------------------------------------
        // Start the activity with a fresh card (new card fragment)
        // -----------------------------------------------------------------------------------------
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new CardFragment();

        fragmentTransaction.replace(R.id.gameFrame, fragment);
        fragmentTransaction.commit();

        mTeam1Points.setVisibility(View.VISIBLE);
        mTeam2Points.setVisibility(View.VISIBLE);
        mTeam1Title.setVisibility(View.VISIBLE);
        mTeam2Title.setVisibility(View.VISIBLE);

        mButtonNo.setVisibility(View.VISIBLE);
        mButtonYes.setVisibility(View.VISIBLE);

        // -----------------------------------------------------------------------------------------
        // onClickListener for "Yes" button (when player gets it right)
        // -----------------------------------------------------------------------------------------
        mButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ---------------------------------------------------------------------------------
                // Create a card fragment, and replace current fragment with it
                // ---------------------------------------------------------------------------------
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment cardFragment = new CardFragment();
                fragmentTransaction.replace(R.id.gameFrame, cardFragment);
                fragmentTransaction.commit();

                // ---------------------------------------------------------------------------------
                // Add a point to the team (based on the title)
                // ---------------------------------------------------------------------------------
                if (mCurrentTeam.getText().toString() == "TEAM 1") {
                    mTeam1PointsInt = mTeam1PointsInt + 1;
                    mTeam1Points.setText(mTeam1PointsInt + "");
                } else if (mCurrentTeam.getText().toString() == "TEAM 2") {
                    mTeam2PointsInt = mTeam2PointsInt + 1;
                    mTeam2Points.setText(mTeam2PointsInt + "");
                }
            }
        });

        // -----------------------------------------------------------------------------------------
        // onClickListener for "No" button (when player gets it wrong)
        // -----------------------------------------------------------------------------------------
        mButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ---------------------------------------------------------------------------------
                // Create a card fragment, and replace current fragment with it
                // ---------------------------------------------------------------------------------
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment cardFragment = new CardFragment();
                fragmentTransaction.replace(R.id.gameFrame, cardFragment);
                fragmentTransaction.commit();
            }
        });
    }

    public static int getLevel() {
        return mLevel;
    }

    // ---------------------------------------------------------------------------------------------
    // Method called when the team switch is complete (New team clicks "ready" button)
    // ---------------------------------------------------------------------------------------------
    public void clickSwitchComplete(View v) {

        // -----------------------------------------------------------------------------------------
        // Create a new card fragment
        // -----------------------------------------------------------------------------------------
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment cardFragment = new CardFragment();
        fragmentTransaction.replace(R.id.gameFrame, cardFragment);
        fragmentTransaction.commit();

        mTeam1Points.setVisibility(View.VISIBLE);
        mTeam2Points.setVisibility(View.VISIBLE);
        mTeam1Title.setVisibility(View.VISIBLE);
        mTeam2Title.setVisibility(View.VISIBLE);

        // -----------------------------------------------------------------------------------------
        // Reset timer
        // -----------------------------------------------------------------------------------------
        countDownTimer.cancel();
        countDownTimer.start();
        timerHasStarted = true;

        // enable buttons again
        mButtonNo.setEnabled(true);
        mButtonYes.setEnabled(true);
        mButtonNo.setVisibility(View.VISIBLE);
        mButtonYes.setVisibility(View.VISIBLE);

        // -----------------------------------------------------------------------------------------
        // Change team name
        // -----------------------------------------------------------------------------------------
        if (mCurrentTeam.getText().toString() == "TEAM 1") {
            mCurrentTeam.setText("TEAM 2");
        } else if (mCurrentTeam.getText().toString() == "TEAM 2") {
            mCurrentTeam.setText("TEAM 1");
        }

        // -----------------------------------------------------------------------------------------
        // Increment the turn counter
        // -----------------------------------------------------------------------------------------
        mNumTurns = mNumTurns + 1;

        // -----------------------------------------------------------------------------------------
        // Set the team title back visible
        // -----------------------------------------------------------------------------------------
        mCurrentTeam.setVisibility(View.VISIBLE);
    }

    // ---------------------------------------------------------------------------------------------
    // Method that sets the english word to be translated from the card fragment
    // ---------------------------------------------------------------------------------------------
    public static void setWordToTranslate(String word) {
        mEnglishWord = word;
    }

    // ---------------------------------------------------------------------------------------------
    // Method called when the user hits the translate button
    // ---------------------------------------------------------------------------------------------
    public void translateWord(View v) {

        // -----------------------------------------------------------------------------------------
        // Create a new translation fragment (dialog popup)
        // -----------------------------------------------------------------------------------------
        TranslationFragment dialogTranslate = new TranslationFragment();

        // -----------------------------------------------------------------------------------------
        // Send word to be translated into the dialog fragment
        // -----------------------------------------------------------------------------------------
        dialogTranslate.setFrenchWordToTranslate(mEnglishWord);

        // -----------------------------------------------------------------------------------------
        // Detect network connection and send that info to the dialog translate fragment
        // -----------------------------------------------------------------------------------------
        dialogTranslate.setConnectionBool(haveNetworkConnection());

        // -----------------------------------------------------------------------------------------
        // Show the dialog window
        // -----------------------------------------------------------------------------------------
        dialogTranslate.show(getFragmentManager(), "");

        // -----------------------------------------------------------------------------------------
        // Subtract a point from the current team's score
        // -----------------------------------------------------------------------------------------
        if (mCurrentTeam.getText().toString() == "TEAM 1") {
            mTeam1PointsInt = mTeam1PointsInt - 1;
            mTeam1Points.setText(mTeam1PointsInt + "");
        } else if (mCurrentTeam.getText().toString() == "TEAM 2") {
            mTeam2PointsInt = mTeam2PointsInt - 1;
            mTeam2Points.setText(mTeam2PointsInt + "");
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Method called when user wants to start a new game
    // ---------------------------------------------------------------------------------------------
    public void clickNewGame(View v) {

        Intent intent = new Intent(GameActivity.this, StartGameActivity.class);
        startActivity(intent);
    }

    // ---------------------------------------------------------------------------------------------
    // Class for the countdown timer
    // ---------------------------------------------------------------------------------------------
    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            textTimer.setText("");

            // numPlayers = 4   numTurns = 1
            // team 1
            // switch           numTurns = 2
            // team 2
            // switch           numTurns = 3
            // team 1
            // swtich           numTurns = 4
            // team 2
            // end
            if (mNumTurns == mNumPlayers) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment endGameFragment = new EndGameFragment();
                fragmentTransaction.replace(R.id.gameFrame, endGameFragment);
                fragmentTransaction.commit();

                // set the winning team at the top
                if (mTeam1PointsInt > mTeam2PointsInt) {
                    // team 1 wins
                    mWinner = "Team 1 wins!";
                } else if (mTeam1PointsInt < mTeam2PointsInt) {
                    // team 2 wins
                    mWinner = "Team 2 wins!";
                } else if (mTeam1PointsInt == mTeam2PointsInt) {
                    // tie
                    mWinner = "It's a tie!";
                }
                mCurrentTeam.setText(mWinner);
                mCurrentTeam.setVisibility(View.VISIBLE);

                mTeam1Points.setVisibility(View.INVISIBLE);
                mTeam2Points.setVisibility(View.INVISIBLE);
                mTeam1Title.setVisibility(View.INVISIBLE);
                mTeam2Title.setVisibility(View.INVISIBLE);
                mButtonNo.setVisibility(View.INVISIBLE);
                mButtonYes.setVisibility(View.INVISIBLE);


                confettoBitmaps = Utils.generateConfettiBitmaps(colors, size);

                getConfettiManager().setNumInitialCount(0)
                        .setEmissionDuration(ConfettiManager.INFINITE_DURATION)
                        .setEmissionRate(50)
                        .animate();

            } else {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment switchFragment = new SwitchFragment();
                fragmentTransaction.replace(R.id.gameFrame, switchFragment);
                fragmentTransaction.commit();

                //disable got it/not it buttons while on switch
                mButtonNo.setEnabled(false);
                mButtonYes.setEnabled(false);
                mButtonNo.setVisibility(View.INVISIBLE);
                mButtonYes.setVisibility(View.INVISIBLE);

                // hide team name
                mCurrentTeam.setVisibility(View.INVISIBLE);


                mTeam1Points.setVisibility(View.INVISIBLE);
                mTeam2Points.setVisibility(View.INVISIBLE);
                mTeam1Title.setVisibility(View.INVISIBLE);
                mTeam2Title.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        public void onTick(long millisUntilFinished) {
            textTimer.setText("" + millisUntilFinished / 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();

    }

    @Override
    protected void onResume() {
        super.onResume();

        countDownTimer.start();


    }

    // ---------------------------------------------------------------------------------------------
    // Method to check for network/wifi connection
    // ---------------------------------------------------------------------------------------------
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private ConfettiManager getConfettiManager() {
        final ConfettiSource confettiSource = new ConfettiSource(0, -size, rootViewGroup.getWidth(),
                -size);
        return new ConfettiManager(this, this, confettiSource, rootViewGroup)
                .setVelocityX(0, velocitySlow)
                .setVelocityY(velocityNormal, velocitySlow)
                .setInitialRotation(180, 180)
                .setRotationalAcceleration(360, 180)
                .setTargetRotationalVelocity(360);
    }

    @Override
    public Confetto generateConfetto(Random random) {
        return new ShimmeringConfetto(
                confettoBitmaps.get(random.nextInt(confettoBitmaps.size())),
                goldLight, goldDark, 1000, random);
    }


}

package com.robinkanatzar.android.rck.tabou;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TranslationFragment extends DialogFragment {

    // ---------------------------------------------------------------------------------------------
    // Declare Variables
    // ---------------------------------------------------------------------------------------------
    String mFrenchWordToTranslate = "";
    GoogleTranslateActivity translator;
    TextView frenchWord;
    Boolean mConnection;
    String API_KEY = BuildConfig.API_KEY;

    // ---------------------------------------------------------------------------------------------
    // onCreateDialog Method
    // ---------------------------------------------------------------------------------------------
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // -----------------------------------------------------------------------------------------
        // Commands to create the dialog and inflate a layout
        // -----------------------------------------------------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.translation_view, null);

        // -----------------------------------------------------------------------------------------
        // Link items to the widgets on the dialog layout
        // -----------------------------------------------------------------------------------------
        Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
        frenchWord = (TextView) dialogView.findViewById(R.id.textViewTranslatedWord);

        // -----------------------------------------------------------------------------------------
        // Set current view to the dialog view
        // -----------------------------------------------------------------------------------------
        builder.setView(dialogView);

        // -----------------------------------------------------------------------------------------
        // Set onClickListener for the OK button
        // -----------------------------------------------------------------------------------------
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // -----------------------------------------------------------------------------------------
        // Set thread policy
        // -----------------------------------------------------------------------------------------
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // -----------------------------------------------------------------------------------------
        // Execute translation IF you have a network connection
        // -----------------------------------------------------------------------------------------
        if (!mConnection) {
            frenchWord.setText("ERROR: No network/internet connection");
        } else {
            new FrenchToEnglish().execute();
        }
        // -----------------------------------------------------------------------------------------
        // Final command to create the dialog popup
        // -----------------------------------------------------------------------------------------
        return builder.create();
    }

    // ---------------------------------------------------------------------------------------------
    // Method to set the word that needs to be translated (called by card fragment)
    // ---------------------------------------------------------------------------------------------
    public void setFrenchWordToTranslate(String frenchWord) {
        mFrenchWordToTranslate = frenchWord;
    }

    // ---------------------------------------------------------------------------------------------
    // Translation class
    // ---------------------------------------------------------------------------------------------
    private class FrenchToEnglish extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        protected void onError(Exception ex) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                translator = new GoogleTranslateActivity(API_KEY);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            //start the progress dialog
            progress = ProgressDialog.show(getActivity(), null, "Translating...");
            //progress = ProgressDialog.show(MainActivity.this, null, "Translating...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            super.onPostExecute(result);
            translated();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Method that sends the string to be translated
    // ---------------------------------------------------------------------------------------------
    public void translated(){
        String translateToEnglish = mFrenchWordToTranslate;

        // -----------------------------------------------------------------------------------------
        // Call function that translates from FR to EN
        // -----------------------------------------------------------------------------------------
        String text = translator.translte(translateToEnglish, "fr", "en");

        // -----------------------------------------------------------------------------------------
        // Set the text in the popup dialog to the translated English word
        // -----------------------------------------------------------------------------------------
        frenchWord.setText(text);
    }

    // ---------------------------------------------------------------------------------------------
    // Method that captures the connection detected in game activity
    // ---------------------------------------------------------------------------------------------
    public void setConnectionBool(Boolean bool) {
        mConnection = bool;
    }
}
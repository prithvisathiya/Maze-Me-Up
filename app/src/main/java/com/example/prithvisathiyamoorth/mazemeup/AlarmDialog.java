package com.example.prithvisathiyamoorth.mazemeup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;


public class AlarmDialog extends Activity {

    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DragBallTest test = new DragBallTest(this);
        setContentView(test);

        //turn screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        //vibrate and show dialog
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{0, 200, 400},0);
        showDialog();

    }



    private void showDialog() {
        //customize pop up dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Your Title");
        alertDialogBuilder
                .setMessage("ALARMMMM!")
                .setCancelable(false)
                .setPositiveButton("Play",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                        vibrator.cancel();
                        //finish();
                    }
                })
                .setNegativeButton("Snooze",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //Set alarm again (Snooze) time from now
                        dialog.dismiss();
                        vibrator.cancel();
                        finish();
                    }
                });
        // create alert dialog and show
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

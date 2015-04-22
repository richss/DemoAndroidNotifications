/*
 *  @author Richard S. Stansbury
 *  @project Demonstration of Android Notifications
 *  @date 22.April.2015
 *
 */

package com.richardstansbury.demoandroidnotifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    EditText time_limit;
    boolean clickAvailable;

    /**
     * Creates a new activity.
     * @param savedInstanceState - parameters passed to an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time_limit = (EditText) findViewById(R.id.time_limit);

        if (savedInstanceState != null) {
            time_limit.setText(savedInstanceState.getInt("time_limit"));
        }

        clickAvailable = true;
    }

    /**
     * Called prior to view being destroyed to preserve state.
     * @param state - Bundle to save state into
     */
    @Override
    public void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        state.putInt("time_limit", Integer.parseInt(time_limit.getText().toString()));
    }


    /**
     * onClick listener for the activity's button.  Grabs the requested time.
     * Starts the service.  Creates a countdown timer.
     * @param v
     */
    public void startTimer(View v) {

        if (!clickAvailable) return;
        clickAvailable = false;

        //Get the time limit for the timer
        int limit = Integer.parseInt(time_limit.getText().toString());

        //Create an intent to call the timer service.
        Intent timerIntent = new Intent(this, TimerService.class);
        timerIntent.putExtra("timeSec",limit);
        Log.i("Time Start", "Timer Started with " + limit + " limit");
        this.startService(timerIntent);

        //Create countdown clock to update UI
        new CountDownTimer(limit * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                time_limit.setText("" + (int) (millisUntilFinished / 1000));
            }

            public void onFinish() {

                time_limit.setText("0");
                clickAvailable = true;

                //This would be a perfect place to launch the notification; however I did it in a
                //service for demonstration purpose.
            }

        }.start(); //start the timer.

    }

}

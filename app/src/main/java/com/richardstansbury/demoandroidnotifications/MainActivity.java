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

    @Override
    public void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        state.putInt("time_limit", Integer.parseInt(time_limit.getText().toString()));
    }

    public void startTimer(View v) {

        if (!clickAvailable) return;
        clickAvailable = false;

        int limit = Integer.parseInt(time_limit.getText().toString());

        Intent timerIntent = new Intent(this, TimerService.class);
        timerIntent.putExtra("timeSec",limit);
        Log.i("Time Start", "Timer Started with " + limit + " limit");
        this.startService(timerIntent);

        new CountDownTimer(limit * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                time_limit.setText("" + (int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                //Do nothing.  You really could launch your notification here, but I wanted to show a service.
                time_limit.setText("0");
                clickAvailable = true;
            }

        }.start();

    }

}

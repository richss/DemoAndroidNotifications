/**
 * Implements a timer service that triggers an alert given a
 * target elapsed time.
 *
 * @author Richard S. Stansbury
 * @date 22.April 2015
 */


package com.richardstansbury.demoandroidnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    public static final int NOTIFIER_ID = 1;

    Timer timer;


    /**
     * Starts the service.
     *
     * @param intent - calling intent
     * @param flags - flags
     * @param startID - ID of the service
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {

        //Get duration from intent.
        final int timeSec = intent.getIntExtra("timeSec",60);


        //Create the countdown timer service.
        timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run()
            {
                Log.i("Timer", "Time limit has expired");

                //Launch Notification
                sendNotification(timeSec);
            }
        }, timeSec*1000);

        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    /**
     * Sends a notification that the timer has expired.
     * @param timeSec
     */
    public void sendNotification(int timeSec)
    {

        //Create action intent for when notification is clicked
        Intent actionIntent = new Intent(this, MainActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent,0);

        //Creates a Notification Builder.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setAutoCancel(true);
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setContentTitle("Timer Expired");
        b.setContentText("Your timer for " + timeSec + " seconds has expired.");
        b.setContentIntent(actionPendingIntent);
        b.setSmallIcon(R.drawable.clock);


        //Launch Notification
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFIER_ID, b.build());
    }
}

package br.com.softblue.android;

import android.os.SystemClock;
import android.util.Log;

public class TimeWorker implements Runnable {

    public static final String TAG = "App";

    private volatile boolean running;
    private int seconds;

    @Override
    public void run() {
        running = true;

        while (running) {
            seconds++;
            Log.i(TAG, "Segundos = " + seconds);
            SystemClock.sleep(1000);
        }
    }

    public void stop() {
        running = false;
    }
}

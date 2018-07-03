package br.com.softblue.android;

import android.os.SystemClock;
import android.util.Log;

public class TimeWorker implements Runnable {
    public static final String TAG = "App";

	private int seconds;
	private volatile boolean running;
	
	@Override
	public void run() {
		running = true;
		
		while(running) {
			incrementSeconds();
			Log.i(TAG, "Segundos = " + seconds);
			SystemClock.sleep(1000);
		}
	}

	public void stop() {
		running = false;
	}
	
	private synchronized void incrementSeconds() {
		seconds++;
	}
	
	public synchronized int getSeconds() {
		return seconds;
	}
}

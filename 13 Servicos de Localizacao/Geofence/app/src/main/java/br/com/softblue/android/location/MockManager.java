package br.com.softblue.android.location;

import android.location.Location;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MockManager {

	private GoogleApiClient apiClient;
	private boolean mockMode;
	private transient boolean running;

	public MockManager(GoogleApiClient apiClient) {
		this.apiClient = apiClient;
	}
	
	public void setLocation(double latitude, double longitude) throws SecurityException {
		if (!mockMode) {
            LocationServices.FusedLocationApi.setMockMode(apiClient, true);
			mockMode = true;
		}
		
		Location mockLocation = new Location("MockProvider");
		mockLocation.setLatitude(latitude);
		mockLocation.setLongitude(longitude);
		mockLocation.setAccuracy(1.0f);
		mockLocation.setTime(System.currentTimeMillis());
		mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        LocationServices.FusedLocationApi.setMockLocation(apiClient, mockLocation);
	}
	
	public void startThread(final double latitude, final double longitude, final double amount) {
        running = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				double currentLatitude = latitude;
				double currentLongitude = longitude;
				
				while (running) {
					setLocation(currentLatitude, currentLongitude);
					currentLatitude += amount;
					currentLongitude += amount;
					SystemClock.sleep(500);
				}
			}
		}).start();
	}

	public void startThread(final double[][] locations) {
		running = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (running) {
					double[] location = locations[i];
					setLocation(location[0], location[1]);
					Log.i("App", String.format("Location: (%.4f, %.4f)", location[0], location[1]));
					SystemClock.sleep(1000);
					i = (i + 1) % locations.length;
				}
			}
		}).start();
	}

	public void dispose() {
        if (running) {
            running = false;
            SystemClock.sleep(1000);
        }
	}
}

package br.com.softblue.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TimeService extends Service {
	
	private TimeWorker worker;
    private TimeServiceBinder binder = new TimeServiceBinder();

	@Override
	public void onCreate() {
		super.onCreate();
		
		worker = new TimeWorker();
		Log.i(TimeWorker.TAG, "Serviço criado");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		worker.stop();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(worker).start();
		return START_STICKY;
	}
	
	public int getSeconds() {
		return worker.getSeconds();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

    public class TimeServiceBinder extends Binder {
        public TimeService getService() {
            return TimeService.this;
        }
    }
}

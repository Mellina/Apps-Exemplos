package br.com.softblue.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TimeService service;
    private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txt_seconds);
	}

	public void start(View view) {
		Intent intent = new Intent(getApplicationContext(), TimeService.class);
		startService(intent);

        TimeServiceConnection conn = new TimeServiceConnection();
        bindService(intent, conn, 0);
	}

	public void read(View view) {
        int seconds = service.getSeconds();
        textView.setText(String.valueOf(seconds));
	}

    private class TimeServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimeService.TimeServiceBinder binder = (TimeService.TimeServiceBinder) service;
            MainActivity.this.service = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}

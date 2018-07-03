package br.com.softblue.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void start(View view) {
        intent = new Intent(getApplicationContext(), TimeService.class);
        startService(intent);
	}

	public void stop(View view) {
        if (intent != null) {
            stopService(intent);
        }
	}
}

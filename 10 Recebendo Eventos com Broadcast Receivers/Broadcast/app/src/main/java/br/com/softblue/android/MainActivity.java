package br.com.softblue.android;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private ToastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

        receiver = new ToastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("br.com.softblue.android.broadcast.TOAST");
        registerReceiver(receiver, intentFilter);
	}
	
	public void send(View view) {
        Intent intent = new Intent("br.com.softblue.android.broadcast.TOAST");
        intent.putExtra("msg", "Este toast é exibido pelo BroadcastReceiver");
        sendBroadcast(intent);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

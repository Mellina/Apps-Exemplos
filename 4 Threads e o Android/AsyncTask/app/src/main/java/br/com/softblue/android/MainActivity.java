package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Button button;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		button = (Button) findViewById(R.id.btn_iniciar);
		textView = (TextView) findViewById(R.id.txt_counter);
	}
	
	public void iniciar(View v) {
        CounterTask task = new CounterTask(button, textView);
        task.execute(10);
	}
}

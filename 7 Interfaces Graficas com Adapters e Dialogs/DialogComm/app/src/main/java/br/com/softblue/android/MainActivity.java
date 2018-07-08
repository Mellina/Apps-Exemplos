package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements ExitDialog.ExitListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
	}

    @Override
    public void onBackPressed() {
        ExitDialog dialog = new ExitDialog();
        dialog.show(getFragmentManager(), "exitDialog");
    }

    @Override
    public void onExit() {
        finish();
    }
}

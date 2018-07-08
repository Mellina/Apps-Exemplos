package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnLongClickListener, ActionMode.Callback {

	private TextView view;
	private int count;
    private boolean actionModeActive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		view = findViewById(R.id.txt_number);
		view.setText("0");
        view.setOnLongClickListener(this);
	}

    @Override
    public boolean onLongClick(View v) {
        if (!actionModeActive) {
            startActionMode(this);
            actionModeActive = true;
        }
        return true;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        if (item.getItemId() == R.id.act_add) {
            count++;
            view.setText(String.valueOf(count));
            mode.finish();
            return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionModeActive = false;
    }
}

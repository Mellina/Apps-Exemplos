package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
	}

	public void showPopup(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.popup);
        popup.setOnMenuItemClickListener(this);
        popup.show();
	}

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.act_next) {
            Toast.makeText(this, "Próximo", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.act_previous) {
            Toast.makeText(this, "Anterior", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}

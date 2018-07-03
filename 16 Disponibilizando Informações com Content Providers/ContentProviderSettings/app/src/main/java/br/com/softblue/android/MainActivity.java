package br.com.softblue.android;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        String[] columns = {
                Settings.System.NAME,
                Settings.System.VALUE
        };

        int[] resources = {
                android.R.id.text1,
                android.R.id.text2
        };

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, columns, resources, 0);
        setListAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
	}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] columns = {
                Settings.System._ID,
                Settings.System.NAME,
                Settings.System.VALUE
        };

        return new CursorLoader(this, Settings.System.CONTENT_URI, columns, null, null, Settings.System.NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, "ID: " + id, Toast.LENGTH_SHORT).show();
    }
}

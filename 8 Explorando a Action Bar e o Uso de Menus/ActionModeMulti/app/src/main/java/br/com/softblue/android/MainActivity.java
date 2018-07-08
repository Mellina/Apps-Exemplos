package br.com.softblue.android;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements AbsListView.MultiChoiceModeListener {

	private ArrayAdapter<String> adapter;
    private ListView listView;
    private List<String> selected = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
		adapter.add("A");
		adapter.add("B");
		adapter.add("C");
		adapter.add("D");
		adapter.add("E");
		adapter.add("F");

		setListAdapter(adapter);

        listView = getListView();

        listView.setMultiChoiceModeListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
	}

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        String s = adapter.getItem(position);
        View view = listView.getChildAt(position);

        if (checked) {
            view.setBackgroundColor(Color.GREEN);
            selected.add(s);

        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
            selected.remove(s);
        }
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

        if (item.getItemId() == R.id.act_delete) {
            for (String s : selected) {
                adapter.remove(s);
            }

            mode.finish();
            return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        int count = listView.getChildCount();

        for (int i = 0; i < count; i++) {
            View view = listView.getChildAt(i);
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        selected.clear();
    }
}

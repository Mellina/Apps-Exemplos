package br.com.softblue.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment {

    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("fragmentPos");
        }
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        int layoutsRes = 0;

        if (position == 0) {
            layoutsRes = R.layout.fragment_page1;
        } else if (position == 1) {
            layoutsRes = R.layout.fragment_page2;
        } else if (position == 2) {
            layoutsRes = R.layout.fragment_page3;
        }
		
		return inflater.inflate(layoutsRes, container, false);
	}
	
	public static PageFragment newInstance(int position) {
		PageFragment f = new PageFragment();
        f.position = position;
		return f;
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragmentPos", position);
    }
}

package br.com.softblue.android;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);

        PageAdaper adapter = new PageAdaper(getSupportFragmentManager());
        pager.setAdapter(adapter);

        if (savedInstanceState != null) {
            int tab = savedInstanceState.getInt("tab");
            pager.setCurrentItem(tab);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("tab", pager.getCurrentItem());
    }
}

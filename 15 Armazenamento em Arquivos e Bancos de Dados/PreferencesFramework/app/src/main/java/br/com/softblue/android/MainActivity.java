package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConfigFragment configFragment = new ConfigFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, configFragment, "configFragment")
                .commit();
    }
}
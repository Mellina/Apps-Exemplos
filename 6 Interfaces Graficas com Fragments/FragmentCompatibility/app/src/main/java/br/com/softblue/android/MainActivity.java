package br.com.softblue.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NATIVO
        //FragmentManager fm = getFragmentManager();
        //Fragment f = fm.findFragmentById(R.id.fragment_left);

        // SUPPORT API
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment f = fm.findFragmentById(R.id.fragment_left);

    }
}

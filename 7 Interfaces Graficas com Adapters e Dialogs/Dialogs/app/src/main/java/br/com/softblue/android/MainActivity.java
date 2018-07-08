package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void open(View view) {

        MyDialogFragment dialog = new MyDialogFragment();
        dialog.show(getFragmentManager(), "dialog");
    }

    public void openSimple(View view) {
        SimpleDialogFragment dialog = new SimpleDialogFragment();
        dialog.show(getFragmentManager(), "dialogSimple");
    }

    public void openRadio(View view) {
        RadioDialogFragment dialog = new RadioDialogFragment();
        dialog.show(getFragmentManager(), "dialogRadio");
    }

    public void openMultiple(View view) {
        MultipleDialogFragment dialog = new MultipleDialogFragment();
        dialog.show(getFragmentManager(), "dialogMultiple");
    }
}

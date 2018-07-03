package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {

    private static final String TAG = "MeuApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Mensagem de INFO");
        Log.d(TAG, "Mensagem de DEBUG");
        Log.w(TAG, "Mensagem de WARN");
        Log.e(TAG, "Mensagem de ERROR");
        Log.v(TAG, "Mensagem de VERBOSE");
    }
}

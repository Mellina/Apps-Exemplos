package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView t = new TextView(this);

        //t.setText("Aplicativo Android");
        t.setText(R.string.texto_tela);

        setContentView(t);
    }
}

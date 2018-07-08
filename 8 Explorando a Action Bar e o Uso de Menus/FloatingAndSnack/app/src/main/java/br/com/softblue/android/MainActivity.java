package br.com.softblue.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Clicou no FAB", Toast.LENGTH_SHORT).show();

                final Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "Clicou no FAB", Snackbar.LENGTH_LONG);
                snackbar.setAction("Fechar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        });
    }
}

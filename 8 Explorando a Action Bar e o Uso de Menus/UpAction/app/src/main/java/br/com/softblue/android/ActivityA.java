package br.com.softblue.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

public class ActivityA extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
    }

    public void next(View view) {
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }
}

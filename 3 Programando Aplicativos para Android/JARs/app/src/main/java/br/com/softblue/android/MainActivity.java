package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;

import org.apache.commons.lang.StringUtils;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringUtils.isEmpty("aaa");
    }
}

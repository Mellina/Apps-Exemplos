package br.com.softblue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SegundaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        String mensagem = getIntent().getStringExtra("texto");
        TextView textView = findViewById(R.id.txt);

        textView.setText(mensagem);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("num", 1000);

        setResult(RESULT_OK, intent);
        finish();
    }
}

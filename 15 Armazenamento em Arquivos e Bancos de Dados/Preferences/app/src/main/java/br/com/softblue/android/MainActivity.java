package br.com.softblue.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText edtNome;
    private EditText edtIdade;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        edtNome = findViewById(R.id.edt_nome);
        edtIdade = findViewById(R.id.edt_idade);

        prefs = getSharedPreferences("app", MODE_PRIVATE);

        String nome = prefs.getString("nome", null);
        if (nome != null) {
            edtNome.setText(nome);
        }

        int idade = prefs.getInt("idade", -1);
        if (idade != -1) {
            edtIdade.setText(String.valueOf(idade));
        }
    }

    public void save(View view) {
        String nome = edtNome.getText().toString();
        int idade = Integer.parseInt(edtIdade.getText().toString());

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nome", nome);
        editor.putInt("idade", idade);
        editor.apply();

        Toast.makeText(this, "Dados gravados!", Toast.LENGTH_SHORT).show();
    }
}
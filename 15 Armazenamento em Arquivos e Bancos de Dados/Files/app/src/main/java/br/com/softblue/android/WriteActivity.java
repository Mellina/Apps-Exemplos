package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteActivity extends Activity {

    private Constants.Type type;

    private EditText edtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_write);

        edtText = findViewById(R.id.edt_text);

        type = (Constants.Type) getIntent().getSerializableExtra(Constants.STORAGE_TYPE);
    }

    public void write(View view) {
        try {
            String text = edtText.getText().toString();
            String path;
            if (type == Constants.Type.INTERNAL) {
                path = writeInternal(text);
            } else {
                path = writeExternal(text);
            }

            Toast.makeText(this, "Arquivo gravado em " + path, Toast.LENGTH_LONG).show();
            finish();

        } catch (IOException e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String writeInternal(String text) throws IOException {
        FileOutputStream out = openFileOutput(Constants.FILE_NAME, MODE_PRIVATE);

        try (PrintWriter pw = new PrintWriter(out)) {
            pw.print(text);

            return getFilesDir().getPath() + File.separator + Constants.FILE_NAME;
        }
    }

    private String writeExternal(String text) throws IOException {
        String status = Environment.getExternalStorageState();

        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("A área de armazenamento não está pronta");
        }

        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, Constants.FILE_NAME);

        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print(text);

            return file.getPath();
        }
    }
}

package br.com.softblue.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ReadActivity extends Activity {

    private TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_read);

        txtText = findViewById(R.id.txt_text);

        Constants.Type type = (Constants.Type) getIntent().getSerializableExtra(Constants.STORAGE_TYPE);

        try {
            if (type == Constants.Type.INTERNAL) {
                readInternal();
            } else {
                readExternal();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void readInternal() throws IOException {
        FileInputStream in = openFileInput(Constants.FILE_NAME);

        try (Scanner scanner = new Scanner(in)) {
            StringBuilder sb = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line).append(System.lineSeparator());
            }

            txtText.setText(sb.toString());
        }
    }

    private void readExternal() throws IOException {
        String status = Environment.getExternalStorageState();

        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("A área de armazenamento não está pronta");
        }

        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, Constants.FILE_NAME);

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder sb = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line).append(System.lineSeparator());
            }

            txtText.setText(sb.toString());
        }
    }
}

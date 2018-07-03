package br.com.softblue.android;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.softblue.android.permission.PermissionActivity;

public class MainActivity extends PermissionActivity {
    private EditText edtTelefone;
    private Intent intent;
    private CallReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTelefone = findViewById(R.id.edt_telefone);

        receiver = new CallReceiver();
        registerReceiver(receiver, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    public void call(View view) {
        String number = edtTelefone.getText().toString();
        Uri uri = Uri.parse("tel:" + number);
        intent = new Intent(Intent.ACTION_CALL, uri);

        enablePermissions(0, true, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    protected void onPermissionsNeeded(int requestPermissionId, List<String> permissions) {
        Toast.makeText(this, "Permissões são necessárias para continuar", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPermissionsDenied(int requestPermissionId, List<String> permissions) {
        Toast.makeText(this, "Permissões são necessárias para continuar", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPermissionsGranted(int requestPermissionId, List<String> permissions) throws SecurityException {
        startActivity(intent);
    }
}

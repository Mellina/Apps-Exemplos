package br.com.softblue.android;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.softblue.android.permission.PermissionActivity;

public class MainActivity extends PermissionActivity {

	private EditText edtTelefone;
	private EditText edtMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		edtTelefone = findViewById(R.id.edt_telefone);
		edtMsg = findViewById(R.id.edt_msg);
	}
	
	public void send(View view) {
		enablePermissions(0, true,
				Manifest.permission.SEND_SMS,
				Manifest.permission.RECEIVE_SMS,
				Manifest.permission.READ_PHONE_STATE);
	}

	@Override
	protected void onPermissionsNeeded(int requestPermissionId, List<String> permissions) {
		Toast.makeText(this, "Não é possível continuar sem as permissões", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPermissionsDenied(int requestPermissionId, List<String> permissions) {
		Toast.makeText(this, "Não é possível continuar sem as permissões", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPermissionsGranted(int requestPermissionId, List<String> permissions) throws SecurityException {
		String telefone = edtTelefone.getText().toString();
		String mensagem = edtMsg.getText().toString();

		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(telefone, null, mensagem, null, null);
	}
}

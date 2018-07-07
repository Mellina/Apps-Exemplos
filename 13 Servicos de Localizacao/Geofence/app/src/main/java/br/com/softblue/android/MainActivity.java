package br.com.softblue.android;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import br.com.softblue.android.location.MockManager;
import br.com.softblue.android.permission.PermissionDialog;
import br.com.softblue.android.play.GooglePlayActivity;

public class MainActivity extends GooglePlayActivity implements PermissionDialog.OnPermissionDialogListener {

    private static final String ACTION_GEOFENCE = "br.com.softblue.android.action.GEOFENCE";

	private MockManager mockManager;
	private TextView txtMsg;
	private PendingIntent pi;
    private GeofenceReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		txtMsg = findViewById(R.id.txt_msg);

		mockManager = new MockManager(getApiClient());
        receiver = new GeofenceReceiver();
	}

	@Override
	protected void onStart() {
		super.onStart();
        registerReceiver(receiver, new IntentFilter(ACTION_GEOFENCE));
	}

	@Override
	protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
	}

    @Override
    protected void onGooglePlayServicesConnected() {
        enablePermissions(0, true, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onGooglePlayServicesConnectionSuspended(int errorCode) {
        Toast.makeText(this, "Conexão ao Google Play Services suspensa. Código: " + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onGooglePlayServicesConnectionFailure(int errorCode) {
        Toast.makeText(this, "Falha ao conectar no Google Play Services. Código: " + errorCode, Toast.LENGTH_SHORT).show();
    }

    public void startLocation(View view) {
		double[][] locations = { { 48.85760, 2.29597 }, { 48.95873, 2.30948 }, { 48.96873, 2.31098 }, { 40.68997, -74.04543 }, { 40.66432, -74.02094 }, { 40.65434, -74.01098 } };
		mockManager.startThread(locations);
	}

	public void removeGeofences(View view) {
        PendingResult<Status> status = LocationServices.GeofencingApi.removeGeofences(getApiClient(), pi);
        status.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    Toast.makeText(MainActivity.this, "Geofences removidos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Geofences não removidos: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
	}

    @Override
    protected void onPermissionsNeeded(int requestPermissionId, List<String> permissions) {
        showPermissionDialog(0, "A permissão é realmente necessária. Deseja autorizar o acesso à localização do dispositivo?");
    }

    @Override
    protected void onPermissionsDenied(int requestPermissionId, List<String> permissions) {
        Toast.makeText(this, "Sem a permissão não é possível continuar", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPermissionsGranted(int requestPermissionId, List<String> permissions) throws SecurityException {
        Geofence.Builder builder = new Geofence.Builder();
        builder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);

        builder.setCircularRegion(48.85760, 2.29597, 300);
        builder.setRequestId("Torre Eiffel");
        Geofence geofence1 = builder.build();

        builder.setCircularRegion(40.68997, -74.04543, 300);
        builder.setRequestId("Estátua da Liberdade");
        Geofence geofence2 = builder.build();

        GeofencingRequest request = new GeofencingRequest.Builder().addGeofence(geofence1).addGeofence(geofence2).build();

        Intent intent = new Intent(ACTION_GEOFENCE);
        pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        PendingResult<Status> status = LocationServices.GeofencingApi.addGeofences(getApiClient(), request, pi);
        status.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    Toast.makeText(MainActivity.this, "Geofences registrados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Geofences não registrados. Erro: " + status.getStatusCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPermissionDialogResult(int dialogId, boolean accepted) {
        if (accepted) {
            enablePermissions(0, false, Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            Toast.makeText(this, "Sem a permissão não é possível continuar", Toast.LENGTH_SHORT).show();
        }
    }

    private class GeofenceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            GeofencingEvent event = GeofencingEvent.fromIntent(intent);

            List<Geofence> geofences = event.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);

            int transition = event.getGeofenceTransition();

            String text;

            if (transition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                text = "Entrando na área da " + geofence.getRequestId();
            } else {
                text = "Saindo da área da " + geofence.getRequestId();
            }

            txtMsg.setText(text);
        }
    }
}

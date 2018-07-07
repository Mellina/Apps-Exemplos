package br.com.softblue.android;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import br.com.softblue.android.location.MockManager;
import br.com.softblue.android.play.GooglePlayActivity;

public class MainActivity extends GooglePlayActivity implements LocationListener {

    private static final int PERMISSION_ID = 1;

    private TextView txtLatitude;
    private TextView txtLongitude;

    private MockManager mockManager;
    private LocationRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLatitude = findViewById(R.id.txt_latitude);
        txtLongitude = findViewById(R.id.txt_longitude);

        mockManager = new MockManager(getApiClient());
    }

    @Override
    protected void onStop() {
        if (mockManager != null) {
            mockManager.dispose();
        }
        super.onStop();
    }

    @Override
    protected void onGooglePlayServicesConnected() {
        request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(2000);
        request.setFastestInterval(1000);

        Toast.makeText(this, "Conectado!", Toast.LENGTH_LONG).show();
        enablePermissions(PERMISSION_ID, true, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onGooglePlayServicesConnectionSuspended(int errorCode) {
        Toast.makeText(this, "Suspenso: " + errorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onGooglePlayServicesConnectionFailure(int errorCode) {
        Toast.makeText(this, "Falhou: " + errorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPermissionsNeeded(int requestPermissionId, List<String> permissions) {

    }

    @Override
    protected void onPermissionsDenied(int requestPermissionId, List<String> permissions) {

    }

    @Override
    protected void onPermissionsGranted(int requestPermissionId, List<String> permissions) throws SecurityException {
        LocationServices.FusedLocationApi.requestLocationUpdates(getApiClient(), request, this);
        mockManager.startThread(3.456, 2.333, 0.02);

        Location location = LocationServices.FusedLocationApi.getLastLocation(getApiClient());
        if (location != null) {
            Toast.makeText(this, "Localização: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Localização não encontrada", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLatitude.setText(String.valueOf(location.getLatitude()));
        txtLongitude.setText(String.valueOf(location.getLongitude()));
    }
}

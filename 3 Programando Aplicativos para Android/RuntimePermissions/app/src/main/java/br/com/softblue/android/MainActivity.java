package br.com.softblue.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int REQ_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // Mostre informações ao usuário
                Toast.makeText(this, "Esta permissão é importante...", Toast.LENGTH_LONG).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQ_CODE);
            }

        } else {
            Toast.makeText(this, "Permissão já concedida", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQ_CODE) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                try {
                    String[] ids = cameraManager.getCameraIdList();
                    cameraManager.openCamera(ids[0], new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(CameraDevice cameraDevice) {
                            Toast.makeText(MainActivity.this, "Câmera aberta", Toast.LENGTH_SHORT).show();
                            cameraDevice.close();
                        }

                        @Override
                        public void onDisconnected(CameraDevice cameraDevice) {
                            cameraDevice.close();
                        }

                        @Override
                        public void onError(CameraDevice cameraDevice, int i) {
                            cameraDevice.close();
                        }
                    }, null);

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "Acesso à câmera não autorizado", Toast.LENGTH_SHORT).show();
            }

        }
    }
}

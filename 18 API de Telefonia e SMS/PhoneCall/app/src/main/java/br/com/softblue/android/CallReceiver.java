package br.com.softblue.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        String text;

        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            text = "Chamada desligada";
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            text = "Telefone fora do gancho";
        } else if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            text = "Recebendo chamada de " + number;
        } else {
            text = "Estado desconhecido (" + state + ")";
        }

        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}

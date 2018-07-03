package br.com.softblue.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        SmsMessage message = messages[0];

        String telefone = message.getDisplayOriginatingAddress();
        String mensagem = message.getMessageBody();

        Toast.makeText(context, "SMS recebido de " + telefone + ": " + mensagem, Toast.LENGTH_LONG).show();
    }
}

package br.com.softblue.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pergunta")
                .setMessage("Você entendeu como funcionam os dialogs?")
                .setPositiveButton("Sim", this)
                .setNegativeButton("Não", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        String msg = null;

        if (which == DialogInterface.BUTTON_POSITIVE) {
            msg = "Você escolheu SIM";
        } else if (which == Dialog.BUTTON_NEGATIVE) {
            msg = "Você escolheu NÃO";
        }

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}

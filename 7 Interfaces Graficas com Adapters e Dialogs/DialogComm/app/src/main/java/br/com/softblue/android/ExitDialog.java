package br.com.softblue.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class ExitDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private ExitListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof ExitListener)) {
            throw new RuntimeException("A activity precisa implementar a interface ExitDialog.ExitListener");
        }

        listener = (ExitListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Deseja sair?")
                .setPositiveButton("Sim", this)
                .setNegativeButton("Não", this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onExit();
        }
    }

    public interface ExitListener {
        void onExit();
    }
}

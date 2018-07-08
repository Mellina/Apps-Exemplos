package br.com.softblue.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class RadioDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Escolha uma banda:")
                .setSingleChoiceItems(R.array.bandas, 2, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String[] bandas = getActivity().getResources().getStringArray(R.array.bandas);
        String banda = bandas[which];

        Toast.makeText(getActivity(), "Você escolheu a banda " + banda, Toast.LENGTH_SHORT).show();
    }
}

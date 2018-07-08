package br.com.softblue.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MultipleDialogFragment extends DialogFragment implements DialogInterface.OnMultiChoiceClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        boolean[] checked = { true, false, true, false };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Escolha uma banda:")
                .setMultiChoiceItems(R.array.bandas, checked, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        String[] bandas = getActivity().getResources().getStringArray(R.array.bandas);
        String banda = bandas[which];

        if (isChecked) {
            Toast.makeText(getActivity(), "Você escolheu a banda " + banda, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Você não escolheu mais a banda " + banda, Toast.LENGTH_SHORT).show();
        }
    }
}

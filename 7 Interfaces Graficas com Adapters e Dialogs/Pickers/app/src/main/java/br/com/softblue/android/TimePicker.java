package br.com.softblue.android;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, 16, 30, true);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        String msg = String.format("Você escolheu o horário %02d:%02d", hourOfDay, minute);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}

package br.com.softblue.android;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Locale;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, 2020, 1, 15);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String msg = String.format(new Locale("pt", "BR"), "Você escolheu a data %02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
package br.com.softblue.android.fragment;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import br.com.softblue.android.util.DateUtils;

// Dialog de seleção de data (DatePicker)

public class SelectDateDialog extends DialogFragment implements OnDateSetListener {
	
	private OnDateSetListener listener;
	private int dayOfMonth;
	private int monthOfYear;
	private int year;
	private boolean isDateSet;
	
	// Cria o fragment. Recebe um listener que será usado para notificar quando a data for alterada
	public static SelectDateDialog newInstance(OnDateSetListener listener) {
		SelectDateDialog dialog = new SelectDateDialog();
		dialog.listener = listener;
		return dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (isDateSet) {
			// Se uma data foi fornecida, exibe o dialog com esta data
			return new DatePickerDialog(getActivity(), this, year, monthOfYear - 1, dayOfMonth);

		} else {
			// Ser uma data não foi fornecida, exibe o dialog com a data de hoje
			int[] today = DateUtils.today();
			return new DatePickerDialog(getActivity(), this, today[2], today[1] - 1, today[0]);
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// Uma data foi definida. Se existir um listener, avisa sobre a data escolhida
		if (listener != null) {
			listener.onDateSet(year, monthOfYear + 1, dayOfMonth);
		}
	}

	// Define uma data para ser exibida quando o dialog for aberto
	public void setDate(int year, int monthOfYear, int dayOfMonth) {
		this.year = year;
		this.monthOfYear = monthOfYear;
		this.dayOfMonth = dayOfMonth;
		this.isDateSet = true;
	}

	// Interface implementada por classes que desejam ser notificadas a respeito de seleção de data
	public interface OnDateSetListener {
		void onDateSet(int year, int monthOfYear, int dayOfMonth);
	}
}

package br.com.softblue.android.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import br.com.softblue.android.R;

// Dialog para confirmação de exclusão de despesa
public class DeleteDespesaDialog extends DialogFragment implements OnClickListener {
	
	private long[] ids;
	private OnDeleteDespesaListener listener;
	
	// Cria o dialog. São fornecidos os IDs a serem excluídos e o listener para notificação
	public static DeleteDespesaDialog newInstance(long[] ids, OnDeleteDespesaListener listener) {
		DeleteDespesaDialog dialog = new DeleteDespesaDialog();
		dialog.ids = ids;
		dialog.listener = listener;
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(R.string.dialog_title_delete);
		
		// Define a mensagem do dialog de acordo com a quantidade de despesas a serem excluídas
		if (ids.length == 1) {
			builder.setMessage("Deseja excluir a despesa selecionada?");
		} else {
			builder.setMessage("Deseja excluir as " + ids.length + " despesas selecionadas?");
		}
		
		builder.setNegativeButton("Não", this);
		builder.setPositiveButton("Sim", this);
		
		return builder.create();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// Se a exclusão foi confirmada, avisa através do listener
		if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
			listener.onDeleteDespesa(ids);
		}
	}
	
	// Interface para comunicação de exclusão de despesa
	public interface OnDeleteDespesaListener {
		void onDeleteDespesa(long[] ids);
	}
}

package br.com.softblue.android.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener {

	private OnDeleteListener listener;
	private int produtoId;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

        if (!(activity instanceof OnDeleteListener)) {
            throw new RuntimeException("A activity deve implementar DialogFragment.OnDeleteListener");
        }

        this.listener = (OnDeleteListener) activity;
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Deseja excluir o produto?");
		builder.setPositiveButton("Sim", this);
		builder.setNegativeButton("Não", this);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
			listener.onDelete(produtoId);
		}
	}

	public void setProdutoId(int id) {
		this.produtoId = id;
	}

    public interface OnDeleteListener {
        public void onDelete(int produtoId);
    }
}

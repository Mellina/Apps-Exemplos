package br.com.softblue.android.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.softblue.android.R;
import br.com.softblue.android.util.StringUtils;

// Dialog para criação de categoria
public class CreateCategoriaDialog extends DialogFragment implements OnClickListener, TextWatcher, OnShowListener {
	
	private OnCategoriaCreateListener listener;
	private EditText edtNome;
	private Button btnGravar;
	
	// Cria o fragment. É fornecido um listener para ser avisado quando a categoria for criada
	public static CreateCategoriaDialog newInstance(OnCategoriaCreateListener listener) {
		CreateCategoriaDialog dialog = new CreateCategoriaDialog();
		dialog.listener = listener;
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Adiciona um TextWatcher à caixa de digitação de texto. O TextWatcher é chamado toda vez que o texto
		// da caixa é alterado (usado aqui para validação de dados)
		View view =  View.inflate(getActivity(), R.layout.dialog_createcategoria, null);
		edtNome = view.findViewById(R.id.edt_nome);
		edtNome.addTextChangedListener(this);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(R.string.dialog_title_novacategoria);
		builder.setNegativeButton(R.string.btn_cancelar, this);
		builder.setPositiveButton(R.string.btn_gravar, this);
		builder.setView(view);
		
		AlertDialog dialog = builder.create();
		dialog.setOnShowListener(this);
		return dialog;
	}

	public interface OnCategoriaCreateListener {
		void onCategoriaCreate(String nome);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// Se o cadastro da categoria foi confirmado, notifica o fato através do listener
		if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
			listener.onCategoriaCreate(edtNome.getText().toString().trim());
		}
	}

	// Chamado pelo Android assim que a caixa de diálogo é exibida
	@Override
	public void onShow(DialogInterface dialog) {
		// O método getButton() retorna o botão desejado da caixa de diálogo
		btnGravar = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
		
		// O botão de gravação começa desabilitado
		btnGravar.setEnabled(false);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	// Chamado pelo Android quando o texto da caixa de texto é alterado
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// O botão gravar é habilitado apenas se houver um texto válido na caixa de texto
		btnGravar.setEnabled(!StringUtils.isEmptyOrWhiteSpaces(edtNome.getText().toString()));
	}
}

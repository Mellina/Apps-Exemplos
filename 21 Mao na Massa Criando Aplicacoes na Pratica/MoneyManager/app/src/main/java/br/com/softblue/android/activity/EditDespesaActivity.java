package br.com.softblue.android.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import br.com.softblue.android.fragment.EditDespesaFragment;
import br.com.softblue.android.model.Despesa;

// Activity para edição de despesa (nova despesa ou existente)
public class EditDespesaActivity extends Activity implements EditDespesaFragment.OnDespesaEditFinished {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

		// O ID da despesa vem como extra na intent. Se for cadastro, o ID será 0
		long despesaId = getIntent().getLongExtra("despesaId", 0);

		if (savedInstanceState == null) {
			// Exibe o fragment para edição dentro da activity. O fragment não precisa ser adicionado se a activity estiver sendo
			// recriada, neste caso, o próprio Android insere o fragment novamente

			// Cria o fragment
			EditDespesaFragment fragment;

			if (despesaId > 0) {
				// Se a despesa existir, fornece o ID da despesa ao fragment
				fragment = EditDespesaFragment.newInstance(despesaId);
			} else {
				// Se a despesa não existir, fornece null nomo ID
				fragment = EditDespesaFragment.newInstance(null);
			}

			// Exibe o fragment na activity
			getFragmentManager().beginTransaction().replace(android.R.id.content, fragment, "editFragment").commit();
		}
	}

	@Override
	public void onDespesaEditFinished(Despesa despesa, boolean recorded) {
		// A edição da despesa terminou. Mostra uma mensagem se sucesso (se a despesa foi gravada) e finaliza a activity
		if (recorded) {
			Toast.makeText(this, "Despesa gravada com sucesso!", Toast.LENGTH_SHORT).show();
		}

		finish();
	}
}

package br.com.softblue.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.softblue.android.R;
import br.com.softblue.android.fragment.DespesasListFragment;
import br.com.softblue.android.fragment.EditDespesaFragment;
import br.com.softblue.android.model.Despesa;
import br.com.softblue.android.util.ScreenUtils;

// Activity para exibição da lista de despesas cadastradas
public class DespesasListActivity extends Activity implements DespesasListFragment.OnDespesaClickListener, EditDespesaFragment.OnDespesaEditFinished {

	private View layoutEdit;
	private EditDespesaFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_despesaslist);

		layoutEdit = findViewById(R.id.lay_edit);
	}

	@Override
	public void onDespesaClick(long despesaId) {
		// Uma despesa foi clicada
		
		if (!ScreenUtils.isDualPane(this)) {
			// Se a tela estiver como single pane, abre uma nova activity que vai abrir a edição da despesa
			Intent intent = new Intent(this, EditDespesaActivity.class);
			intent.putExtra("despesaId", despesaId);
			startActivity(intent);

		} else {
			// Se a tela estiver como dual pane, exibe a edição da despesa em um fragment, do lado direito
			layoutEdit.setVisibility(View.VISIBLE);
			fragment = EditDespesaFragment.newInstance(despesaId);
			getFragmentManager().beginTransaction().replace(R.id.lay_edit, fragment, "editFragment").addToBackStack(null).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_add) {
			// Uma nova despesa deve ser inserida
			if (!ScreenUtils.isDualPane(this)) {
				// Se for single pane, abre uma nova activity para cadastro de despesa
				Intent intent = new Intent(this, EditDespesaActivity.class);
				startActivity(intent);

			} else {
				// Ser for dual pane, abre o cadastro à direita, em um fragment
				layoutEdit.setVisibility(View.VISIBLE);
				fragment = new EditDespesaFragment();
				getFragmentManager().beginTransaction().replace(R.id.lay_edit, fragment, "editFragment").addToBackStack(null).commit();
			}

			return true;

		} else if (item.getItemId() == R.id.action_report) {
			// O relatório deve ser exibido em uma nova activity
			Intent intent = new Intent(this, ReportActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDespesaEditFinished(Despesa despesa, boolean recorded) {
		// A edição da despesa terminou. Esconde o layout de edição e remove o fragment
		layoutEdit.setVisibility(View.GONE);
		getFragmentManager().beginTransaction().remove(fragment).commit();

		if (recorded) {
			// Se a despesa foi gravada, mostra uma mensagem de sucesso
			Toast.makeText(this, R.string.toast_despesa_gravada, Toast.LENGTH_SHORT).show();
		}
	}
}
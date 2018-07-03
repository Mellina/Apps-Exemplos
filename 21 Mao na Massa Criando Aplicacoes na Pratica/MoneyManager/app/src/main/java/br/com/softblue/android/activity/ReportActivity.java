package br.com.softblue.android.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import br.com.softblue.android.R;
import br.com.softblue.android.dao.DAOFactory;
import br.com.softblue.android.model.Despesa;
import br.com.softblue.android.util.DateUtils;
import br.com.softblue.android.util.NumberUtils;
import br.com.softblue.android.util.ScreenUtils;

// Activity para exibição do relatório
public class ReportActivity extends Activity implements LoaderCallbacks<Cursor> {

	private TextView txtEmpty;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

		setContentView(R.layout.activity_report);

		txtEmpty = findViewById(R.id.txt_empty);
		layout = findViewById(R.id.lay_info);

		// Inicia o loader
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// Obtém os dados do relatório
		return DAOFactory.getRelatorioDAO(this).createRelatorio();
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (!cursor.moveToFirst()) {
			// Não existem dados, então mostra a view com a mensagem de que não há dados
			txtEmpty.setVisibility(View.VISIBLE);
			layout.setVisibility(View.GONE);

		} else {
			do {
				// Extrai a data e o valor do cursor
				Date data = new Date(cursor.getLong(cursor.getColumnIndex(Despesa.Columns.DATA)));
				double valor = cursor.getDouble(cursor.getColumnIndex(Despesa.Columns.VALOR));

				// É preciso montar os TextViews para exibição dos dados de forma dinâmica
				
				// Cria um LinearLayout que ocupa a linha toda, com orientação horizontal
				LinearLayout l = new LinearLayout(this);
				l.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				l.setOrientation(LinearLayout.HORIZONTAL);

				// Define o padding como 10dp. É preciso converter dp para pixels
				int padding = ScreenUtils.convertToPixels(this, 10);
				l.setPadding(padding, padding, padding, padding);
				
				// Adiciona o LinearLayout layout de exibição
				layout.addView(l);

				// Cria um TextView para exibir a data. Ele ficará à esquerda
				TextView txtData = new TextView(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
				txtData.setLayoutParams(params);
				txtData.setText(DateUtils.formatDate(data));
				l.addView(txtData);

				// Cria um TextView para exibir o valor. Ele ficará à direita.
				TextView txtValor = new TextView(this);
				params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
				txtValor.setLayoutParams(params);
				txtValor.setGravity(Gravity.END);
				txtValor.setText(NumberUtils.formatAsCurrency(valor));
				l.addView(txtValor);
				
			} while (cursor.moveToNext());
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
}

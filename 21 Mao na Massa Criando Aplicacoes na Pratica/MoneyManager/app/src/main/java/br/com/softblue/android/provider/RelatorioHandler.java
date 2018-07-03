package br.com.softblue.android.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.softblue.android.model.Despesa;

//Handler associado a operações com relatórios
public class RelatorioHandler extends DataHandler {

	protected RelatorioHandler(Context context) {
		super(context);
	}

	@Override
	public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// Gera o relatório, agrupando os resultados por dia
		
		String[] columns = {
			Despesa.Columns.DATA,
			"SUM(" + Despesa.Columns.VALOR + ") AS " + Despesa.Columns.VALOR
		};
		
		return db().query(Despesa.TABLE_NAME, columns, null, null, Despesa.Columns.DATA, null, Despesa.Columns.DATA);
	}

	@Override
	public Uri insert(int code, Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int delete(int code, Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}
}

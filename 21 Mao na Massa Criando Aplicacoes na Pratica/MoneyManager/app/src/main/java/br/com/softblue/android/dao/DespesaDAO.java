package br.com.softblue.android.dao;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.net.Uri;

import br.com.softblue.android.model.Despesa;
import br.com.softblue.android.provider.DataProvider;
import br.com.softblue.android.util.DateUtils;

public class DespesaDAO extends DAO {

	DespesaDAO() {
	}

	public void save(Despesa despesa) {
		Uri newUri = contentResolver().insert(DataProvider.CONTENT_DESPESAS_URI, despesa.values());
		long id = ContentUris.parseId(newUri);
		despesa.setId(id);
	}
	
	public void update(Despesa despesa) {
		Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DESPESAS_URI, despesa.getId());
		contentResolver().update(uri, despesa.values(), null, null);
	}
	
	public void delete(long id) {
		Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DESPESAS_URI, id);
		contentResolver().delete(uri, null, null);
	}
	
	public CursorLoader getDespesas(int mes, int ano) {
		String where = Despesa.FullColumns.DATA + " >= ? AND " + Despesa.FullColumns.DATA + "<= ?";
		
		long[] dates = DateUtils.getRange(mes, ano);
		String[] args = { String.valueOf(dates[0]), String.valueOf(dates[1]) };
		
		return new CursorLoader(context(), DataProvider.CONTENT_DESPESAS_URI, null, where, args, null);
	}

	public CursorLoader getDespesaById(long id) {
		Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DESPESAS_URI, id);
		return new CursorLoader(context(), uri, null, null, null, null);
	}
}
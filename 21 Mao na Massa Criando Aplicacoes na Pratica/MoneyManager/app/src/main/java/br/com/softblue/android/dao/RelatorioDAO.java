package br.com.softblue.android.dao;

import android.content.CursorLoader;

import br.com.softblue.android.provider.DataProvider;

public class RelatorioDAO extends DAO {

	RelatorioDAO() {
	}

	public CursorLoader createRelatorio() {
		return new CursorLoader(context(), DataProvider.CONTENT_RELATORIO_URI, null, null, null, null);
	}
}
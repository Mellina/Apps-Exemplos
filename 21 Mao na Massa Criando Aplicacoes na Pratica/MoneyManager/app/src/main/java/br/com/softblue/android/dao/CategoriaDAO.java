package br.com.softblue.android.dao;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.net.Uri;

import br.com.softblue.android.model.Categoria;
import br.com.softblue.android.provider.DataProvider;

public class CategoriaDAO extends DAO {

	CategoriaDAO() {
	}

	public void save(Categoria categoria) {
		Uri newUri = contentResolver().insert(DataProvider.CONTENT_CATEGORIAS_URI, categoria.values());
		long id = ContentUris.parseId(newUri);
		categoria.setId(id);
	}

	public CursorLoader getCategorias() {
		return new CursorLoader(context(), DataProvider.CONTENT_CATEGORIAS_URI, null, null, null, null);
	}

	public CursorLoader getCategoriaById(long id) {
		Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_CATEGORIAS_URI, id);
		return new CursorLoader(context(), uri, null, null, null, null);
	}
}
package br.com.softblue.android.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.softblue.android.model.Categoria;

// Handler associado a operações com categorias
public class CategoriaHandler extends DataHandler {

	protected CategoriaHandler(Context context) {
		super(context);
	}

	@Override
	public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		String[] columns = { 
			Categoria.Columns._ID,
			Categoria.Columns.NOME
		};

		if (code == DataProvider.CODE_CATEGORIA) {
			// Retorna as categorias cadastradas
			return db().query(Categoria.TABLE_NAME, columns, selection, selectionArgs, null, null, Categoria.Columns.NOME);
		
		} else {
			// Retorna a categoria com um determinado ID
			long id = ContentUris.parseId(uri);
			return db().query(Categoria.TABLE_NAME, columns, Categoria.Columns._ID + " = ?", new String[]{ String.valueOf(id) }, null, null, Categoria.Columns.NOME);
		}
	}

	@Override
	public Uri insert(int code, Uri uri, ContentValues values) {
		if (code == DataProvider.CODE_CATEGORIA_ID) {
			throw new InvalidURIException(uri);
		}

		// Insere uma categoria
		long id = db().insert(Categoria.TABLE_NAME, null, values);
		return ContentUris.withAppendedId(uri, id);
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

package br.com.softblue.android.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.softblue.android.model.Categoria;
import br.com.softblue.android.model.Despesa;

//Handler associado a operações com despesas
public class DespesaHandler extends DataHandler {

	protected DespesaHandler(Context context) {
		super(context);
	}

	@Override
	public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		String[] columns = { Despesa.FullColumns._ID, Despesa.FullColumns.DATA, Despesa.FullColumns.DESCRICAO, Despesa.FullColumns.VALOR, Categoria.FullColumns._ID, Categoria.FullColumns.NOME };

		String[] aliases = { Despesa.Aliases._ID, Despesa.Aliases.DATA, Despesa.Aliases.DESCRICAO, Despesa.Aliases.VALOR, Categoria.Aliases._ID, Categoria.Aliases.NOME };

		StringBuilder query = new StringBuilder();
		query.append("SELECT ");

		boolean first = true;
		for (int i = 0; i < columns.length; i++) {
			if (!first) {
				query.append(", ");
			}
			query.append(columns[i]).append(" AS ").append(aliases[i]);
			first = false;
		}

		query.append(" FROM ").append(Despesa.TABLE_NAME).append(" INNER JOIN ").append(Categoria.TABLE_NAME);
		query.append(" ON ").append(Despesa.FullColumns.CATEGORIA_ID).append(" = ").append(Categoria.FullColumns._ID);
		
		if (code == DataProvider.CODE_DESPESA) {
			if (selection != null) {
				query.append(" WHERE ").append(selection);
			}

			query.append(" ORDER BY ").append(Despesa.FullColumns.DATA).append(", ").append(Despesa.FullColumns.DESCRICAO);
			return db().rawQuery(query.toString(), selectionArgs);

		} else {
			long id = ContentUris.parseId(uri);
			query.append(" WHERE ").append(Despesa.FullColumns._ID).append(" = ?");
			return db().rawQuery(query.toString(), new String[] { String.valueOf(id) });
		}
	}

	@Override
	public Uri insert(int code, Uri uri, ContentValues values) {
		if (code == DataProvider.CODE_DESPESA_ID) {
			throw new InvalidURIException(uri);
		}

		// Insere uma despesa
		long id = db().insert(Despesa.TABLE_NAME, null, values);
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (code == DataProvider.CODE_DESPESA) {
			throw new InvalidURIException(uri);
		}

		// Atualiza uma despesa
		long id = ContentUris.parseId(uri);
		return db().update(Despesa.TABLE_NAME, values, Despesa.Columns._ID + " = ?", new String[] { String.valueOf(id) });
	}

	@Override
	public int delete(int code, Uri uri, String selection, String[] selectionArgs) {
		if (code == DataProvider.CODE_DESPESA) {
			throw new InvalidURIException(uri);
		}

		// Exclui uma despesa
		long id = ContentUris.parseId(uri);
		return  db().delete(Despesa.TABLE_NAME, Despesa.Columns._ID + " = ?", new String[] { String.valueOf(id) });
	}
}

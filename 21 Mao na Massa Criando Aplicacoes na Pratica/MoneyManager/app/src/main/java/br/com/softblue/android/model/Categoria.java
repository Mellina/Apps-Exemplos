package br.com.softblue.android.model;

import java.io.Serializable;

import android.content.ContentValues;
import android.database.Cursor;

public class Categoria implements Serializable {

	public static final String TABLE_NAME = "categoria";

	public static final class Columns {
		public static final String _ID = "_id";
		public static final String NOME = "nome";
	}
	
	public static final class FullColumns {
		public static final String _ID = TABLE_NAME + "." + Columns._ID;
		public static final String NOME = TABLE_NAME + "." + Columns.NOME;
	}
	
	public static final class Aliases {
		public static final String _ID = TABLE_NAME + "_" + Columns._ID;
		public static final String NOME = TABLE_NAME + "_" + Columns.NOME;
	}

	private Long id;
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ContentValues values() {
		ContentValues values = new ContentValues();

		if (nome != null) {
			values.put(Columns.NOME, nome);
		}

		return values;
	}

	public void loadFromCursor(Cursor c) {
		clear();

		int index = c.getColumnIndex(Columns._ID);
		if (index > -1 && !c.isNull(index)) {
			setId(c.getLong(index));
		}

		index = c.getColumnIndex(Columns.NOME);
		if (index > -1 && !c.isNull(index)) {
			setNome(c.getString(index));
		}
	}

	public void clear() {
		setId(null);
		setNome(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

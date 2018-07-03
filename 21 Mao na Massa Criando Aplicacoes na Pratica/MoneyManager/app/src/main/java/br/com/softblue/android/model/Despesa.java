package br.com.softblue.android.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

import br.com.softblue.android.dao.DAOFactory;

public class Despesa implements Serializable {

	public static final String TABLE_NAME = "despesa";

	public static final class Columns {
		public static final String _ID = "_id";
		public static final String DATA = "data";
		public static final String DESCRICAO = "descricao";
		public static final String CATEGORIA_ID = "categoria_id";
		public static final String VALOR = "valor";
	}

	public static final class FullColumns {
		public static final String _ID = TABLE_NAME + "." + Columns._ID;
		public static final String DATA = TABLE_NAME + "." + Columns.DATA;
		public static final String DESCRICAO = TABLE_NAME + "." + Columns.DESCRICAO;
		public static final String CATEGORIA_ID = TABLE_NAME + "." + Columns.CATEGORIA_ID;
		public static final String VALOR = TABLE_NAME + "." + Columns.VALOR;
	}

	public static final class Aliases {
		public static final String _ID = Columns._ID;
		public static final String DATA = TABLE_NAME + "_" + Columns.DATA;
		public static final String DESCRICAO = TABLE_NAME + "_" + Columns.DESCRICAO;
		public static final String VALOR = TABLE_NAME + "_" + Columns.VALOR;
	}

	private Long id;
	private Date data;
	private String descricao;
	private Categoria categoria;
	private Double valor;

	public Despesa() {

	}

	public Despesa(Despesa d) {
		this.id = d.id;
		this.data = d.data;
		this.descricao = d.descricao;
		this.categoria = d.categoria;
		this.valor = d.valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public ContentValues values() {
		ContentValues values = new ContentValues();

		if (data != null) {
			values.put(Columns.DATA, data.getTime());
		}

		if (descricao != null) {
			values.put(Columns.DESCRICAO, descricao);
		}

		if (valor != null) {
			values.put(Columns.VALOR, valor);
		}

		if (categoria != null) {
			values.put(Columns.CATEGORIA_ID, categoria.getId());
		}

		return values;
	}

	public void loadFromCursor(Cursor c) {
		clear();

		int index = c.getColumnIndex(Columns._ID);
		if (index > -1 && !c.isNull(index)) {
			setId(c.getLong(index));
		}

		index = c.getColumnIndex(Aliases.DATA);
		if (index > -1 && !c.isNull(index)) {
			setData(new Date(c.getLong(index)));
		}

		index = c.getColumnIndex(Aliases.DESCRICAO);
		if (index > -1 && !c.isNull(index)) {
			setDescricao(c.getString(index));
		}

		index = c.getColumnIndex(Aliases.VALOR);
		if (index > -1 && !c.isNull(index)) {
			setValor(c.getDouble(index));
		}

		index = c.getColumnIndex(Categoria.Aliases._ID);
		if (index > -1 && !c.isNull(index)) {
			if (categoria == null) {
				categoria = new Categoria();
			}
			categoria.setId(c.getLong(index));
		}

		index = c.getColumnIndex(Categoria.Aliases.NOME);
		if (index > -1 && !c.isNull(index)) {
			if (categoria == null) {
				categoria = new Categoria();
			}
			categoria.setNome(c.getString(index));
		}
	}

	private void clear() {
		setId(null);
		setData(null);
		setDescricao(null);
		setCategoria(null);
		setValor(null);
	}

    // Cria despesas para testes
    public static void createDummy(Context context, int qtde) {
        Categoria c = new Categoria();
        c.setNome("Categoria");
        DAOFactory.getCategoriaDAO(context).save(c);

        for (int i = 1; i <= qtde; i++) {
            Despesa d = new Despesa();
            d.setCategoria(c);
            d.setData(new Date());
            d.setDescricao("Despesa " + i);
            d.setValor(50.0);
            DAOFactory.getDepsesaDAO(context).save(d);
        }
    }
}

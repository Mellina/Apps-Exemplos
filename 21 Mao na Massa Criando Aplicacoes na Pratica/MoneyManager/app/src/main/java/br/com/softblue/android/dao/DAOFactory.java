package br.com.softblue.android.dao;

import android.content.Context;

// Classe que retorna instâncias dos DAOs
public class DAOFactory {

	private static CategoriaDAO categoriaDAO;
	private static DespesaDAO despesaDAO;
	private static RelatorioDAO relatorioDAO;

	public static CategoriaDAO getCategoriaDAO(Context context) {
		if (categoriaDAO == null) {
			categoriaDAO = new CategoriaDAO();
			categoriaDAO.init(context.getApplicationContext());
		}
		return categoriaDAO;
	}

	public static DespesaDAO getDepsesaDAO(Context context) {
		if (despesaDAO == null) {
			despesaDAO = new DespesaDAO();
			despesaDAO.init(context.getApplicationContext());
		}
		return despesaDAO;
	}
	
	public static RelatorioDAO getRelatorioDAO(Context context) {
		if (relatorioDAO == null) {
			relatorioDAO = new RelatorioDAO();
			relatorioDAO.init(context.getApplicationContext());
		}
		return relatorioDAO;
	}
}

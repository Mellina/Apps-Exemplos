package br.com.softblue.android.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.softblue.android.R;
import br.com.softblue.android.model.Categoria;

// Adapter para a lista de categorias (spinner na tela de edição de despesa)
public class CategoriasAdapter extends CursorAdapter {

	private Categoria categoria = new Categoria();
	private List<Categoria> categorias = new ArrayList<>();

	public CategoriasAdapter(Context context) {
		super(context, null, 0);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// Carrega os dados do cursor para o objeto Categoria
		categoria.loadFromCursor(cursor);
		
		TextView txtCategoria = view.findViewById(R.id.txt_categoria);
		txtCategoria.setText(categoria.getNome());
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		return inflater.inflate(R.layout.adapter_categorias, parent, false);
	}
	
	// Obtém a posição de uma categoria
	public int getPosition(Categoria categoria) {
		return categorias.indexOf(categoria);
	}
	
	// Obtém a categoria em uma posição
	public Categoria getItem(int position) {
		return categorias.get(position);
	}
	
	// Atualiza os dados 
	public void updateData() {
		Cursor cursor = getCursor();
		
		// Salva a posição atual do cursor
		int pos = cursor.getPosition();
		
		if (cursor.moveToFirst()) {
			categorias.clear();
			do {
				// Atualiza a lista de categorias cadastradas
				Categoria categoria = new Categoria();
				categoria.loadFromCursor(cursor);
				categorias.add(cursor.getPosition(), categoria);
			} while (cursor.moveToNext());
		}
		
		// Restaura a posição do cursor, salva previamente
		cursor.moveToPosition(pos);
	}
}

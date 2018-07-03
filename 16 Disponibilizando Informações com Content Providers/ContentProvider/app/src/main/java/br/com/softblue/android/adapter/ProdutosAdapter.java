package br.com.softblue.android.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.softblue.android.R;
import br.com.softblue.android.data.Produto;
import br.com.softblue.android.data.ProdutoDAO;

public class ProdutosAdapter extends CursorAdapter {
    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private LayoutInflater inflater;

    public ProdutosAdapter(Context context) {
        super(context, null, 0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.adapter_listprodutos, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Produto p = ProdutoDAO.fromCursor(cursor);

        TextView txtNome = view.findViewById(R.id.txt_nome);
        txtNome.setText(p.getNome());

        TextView txtValor = view.findViewById(R.id.txt_valor);
        txtValor.setText(nf.format(p.getValor()));
    }
}

package br.com.softblue.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProdutosAdapter extends BaseAdapter {

    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private List<Produto> produtos;
    private LayoutInflater inflater;

    public ProdutosAdapter(Context context, List<Produto> produtos) {
        this.produtos = produtos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder vh;

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_produtos, parent, false);
            vh = new ViewHolder();
            vh.txtNome = (TextView) view.findViewById(R.id.txt_nome);
            vh.txtPreco = (TextView) view.findViewById(R.id.txt_preco);
            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }

        Produto produto = produtos.get(position);

        vh.txtNome.setText(produto.getNome());
        vh.txtPreco.setText(nf.format(produto.getPreco()));

        return view;
    }

    private static class ViewHolder {
        public TextView txtNome;
        public TextView txtPreco;
    }
}

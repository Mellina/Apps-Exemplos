package br.com.softblue.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.softblue.android.R;
import br.com.softblue.android.data.Produto;

public class ProdutosAdapter extends BaseAdapter {

	private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

	private Context context;
	private List<Produto> produtos = new ArrayList<>();

	public ProdutosAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return produtos.size();
	}

	@Override
	public Produto getItem(int position) {
		return produtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return produtos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.adapter_listprodutos, parent, false);
			holder = new ViewHolder();
			holder.txtNome = view.findViewById(R.id.txt_nome);
			holder.txtValor = view.findViewById(R.id.txt_valor);
			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}

		Produto produto = produtos.get(position);

		holder.txtNome.setText(produto.getNome());
		holder.txtValor.setText(nf.format(produto.getValor()));

		return view;
	}

	public void setItems(List<Produto> produtos) {
		this.produtos = produtos;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		public TextView txtNome;
		public TextView txtValor;
	}
}

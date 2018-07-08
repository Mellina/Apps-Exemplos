package br.com.softblue.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    //private ArrayAdapter<CharSequence> adapter;
    //private ArrayAdapter<String> adapter;
    private ProdutosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adapter = ArrayAdapter.createFromResource(this, R.array.produtos, android.R.layout.simple_list_item_1);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //adapter.add("Café");
        //adapter.add("Macarrão");
        //adapter.add("Feijão");

        adapter = new ProdutosAdapter(this, Produto.getProdutos());

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //String item = adapter.getItem(position);
        Produto produto = (Produto) adapter.getItem(position);
        Toast.makeText(this, "Item clicado: " + produto.getNome(), Toast.LENGTH_LONG).show();
    }
}

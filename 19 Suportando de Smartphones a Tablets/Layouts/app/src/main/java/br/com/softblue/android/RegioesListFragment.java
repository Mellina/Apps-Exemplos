package br.com.softblue.android;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class RegioesListFragment extends ListFragment {

    private OnRegiaoSelectedListener listener;
    private ArrayAdapter<Regiao> adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnRegiaoSelectedListener) {
            this.listener = (OnRegiaoSelectedListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Regiao> regioes = Regioes.getRegioes(getActivity(), R.xml.dados);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, regioes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.onRegiaoSelected(adapter.getItem(position));
        }
    }

    public interface OnRegiaoSelectedListener {
        void onRegiaoSelected(Regiao regiao);
    }
}

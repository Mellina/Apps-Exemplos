package br.com.softblue.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private TextView txtNomeRegiao;
    private TextView txtEstados;
    private Regiao regiao;

    public static DetailFragment newInstance(Regiao regiao) {
        DetailFragment fragment = new DetailFragment();
        fragment.regiao = regiao;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        txtNomeRegiao = view.findViewById(R.id.txt_nome_regiao);
        txtEstados = view.findViewById(R.id.txt_estados);
        atualizarDados();
        return view;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
        atualizarDados();
    }

    private void atualizarDados() {
        if (regiao != null) {
            txtNomeRegiao.setText(regiao.getNome());

            StringBuilder sb = new StringBuilder();

            for (Estado estado : regiao.getEstados()) {
                sb.append(estado.getNome()).append(" (").append(estado.getSigla()).append(")\n");
            }

            txtEstados.setText(sb.toString());
        }
    }
}

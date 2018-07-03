package br.com.softblue.android.fragment;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import br.com.softblue.android.R;
import br.com.softblue.android.adapter.DespesasAdapter;
import br.com.softblue.android.dao.DAOFactory;
import br.com.softblue.android.dao.DespesaDAO;
import br.com.softblue.android.util.DateUtils;

// Fragment de exibição da lista de despesas
public class DespesasListFragment extends ListFragment
        implements LoaderCallbacks<Cursor>, MultiChoiceModeListener, DeleteDespesaDialog.OnDeleteDespesaListener, OnClickListener, DespesasAdapter.OnItemCheckedChangeListener {

	private DespesasAdapter despesasAdapter;
	private OnDespesaClickListener listener;
	
	private Spinner spiMes;
	private Spinner spiAno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
	public void onAttach(Context context) {
		super.onAttach(context);

		// Atribuir a activity que abriu o fragment como um listener de clique em despesa
		if (context instanceof OnDespesaClickListener) {
			this.listener = (OnDespesaClickListener) context;
		}
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            // Cria o adapter
            despesasAdapter = new DespesasAdapter(getActivity(), this);
            setListAdapter(despesasAdapter);
        }
    }

    @Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_despesaslist, container, false);
		
		int[] today = DateUtils.today();
		
		// Cria o adapter de meses
		spiMes = view.findViewById(R.id.spi_mes);
        ArrayAdapter<CharSequence> mesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.meses, android.R.layout.simple_dropdown_item_1line);
		spiMes.setAdapter(mesAdapter);
		spiMes.setSelection(today[1] - 1);
		
		// Cria o adapter de anos
		spiAno = view.findViewById(R.id.spi_ano);
        ArrayAdapter<Integer> anoAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line);
		
		int anoAtual = today[2];
		for (int i = anoAtual - 3; i <= anoAtual; i++) {
			anoAdapter.add(i);
		}
		spiAno.setAdapter(anoAdapter);
		spiAno.setSelection(anoAdapter.getCount() - 1);

		Button btnUpdate = view.findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(this);

		return view;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Habilita o action mode de seleções múltiplas da lista
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(this);

        // Desabilita o click longo
        getListView().setLongClickable(false);

        // Inicia o loader para carregar as despesas
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// Lê o mês e ano selecionados nos spinners.
		// No caso do mês é preciso somar 1 porque Janeiro está no índice 0 do array
		int mes = spiMes.getSelectedItemPosition() + 1;
		int ano = (Integer) spiAno.getSelectedItem();
		
		// Carrega despesas como base em um mês/ano
        return DAOFactory.getDepsesaDAO(getActivity()).getDespesas(mes, ano);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		despesasAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		despesasAdapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Uma despesa foi clicada. Avisa através do listener
		if (listener != null) {
			listener.onDespesaClick(id);
		}
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		if (item.getItemId() == R.id.action_delete) {
			// Abre o dialog de exclusão de despesa
			DeleteDespesaDialog dialog = DeleteDespesaDialog.newInstance(getListView().getCheckedItemIds(), this);
			dialog.show(getFragmentManager(), "deleteDialog");
			return true;
		}

		return false;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		getActivity().getMenuInflater().inflate(R.menu.actionmode, menu);
		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// Quando o action mode é finalizado, remove a seleção de todos os itens
		despesasAdapter.unselectItems();
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
	}

	@Override
	public void onDeleteDespesa(long[] ids) {
		// Exclui as despesas selecionadas
		
		DespesaDAO despesaDAO = DAOFactory.getDepsesaDAO(getActivity());

		for (long id : ids) {
			despesaDAO.delete(id);
		}

		// Remove todas as seleções de itens
		despesasAdapter.unselectItems();
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_update) {
			// Quando o botão de atualização é clicado, o loader é reinicializado para recarregar os dados com o novo mês/ano
			getLoaderManager().restartLoader(0, null, this);
		}
	}

    @Override
    public void onItemCheckedChanged(int position, boolean checked) {
        getListView().setItemChecked(position, checked);
    }

    // Interface para aviso de clique em despesa
	public interface OnDespesaClickListener {
		void onDespesaClick(long despesaId);
	}
}

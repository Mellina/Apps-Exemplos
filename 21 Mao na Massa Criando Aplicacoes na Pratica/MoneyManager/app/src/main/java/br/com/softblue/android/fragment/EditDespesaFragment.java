package br.com.softblue.android.fragment;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import br.com.softblue.android.R;
import br.com.softblue.android.adapter.CategoriasAdapter;
import br.com.softblue.android.dao.DAOFactory;
import br.com.softblue.android.model.Categoria;
import br.com.softblue.android.model.Despesa;
import br.com.softblue.android.util.DateUtils;
import br.com.softblue.android.util.StringUtils;

// Fragment de edição de despesas
public class EditDespesaFragment extends Fragment
        implements OnClickListener, SelectDateDialog.OnDateSetListener, LoaderCallbacks<Cursor>, OnItemSelectedListener, CreateCategoriaDialog.OnCategoriaCreateListener, TextWatcher {

	// Identificadores dos loaders
	private static final int LOADER_DESPESA = 1;
	private static final int LOADER_CATEGORIAS = 2;

	private EditText edtData;
	private EditText edtDescricao;
	private Spinner spiCategoria;
	private EditText edtValor;
	private Button btnGravar;

	private ViewGroup layout;
	private ProgressBar progress;

	private Long despesaId;
	private Despesa despesa = new Despesa();
	private Categoria novaCategoria;

	private OnDespesaEditFinished listener;
	private CategoriasAdapter categoriasAdapter;

	// Indica se houve mudança de configuração (ex: rotação de tela)
	private boolean configurationChanged;

	// Cria o fragment. É fornecido um ID de despesa (pode ser null se a despesa
	// ainda não existir)
	public static EditDespesaFragment newInstance(Long despesaId) {
		EditDespesaFragment fragment = new EditDespesaFragment();
		fragment.despesaId = despesaId;
		return fragment;
}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		// Armazena a activity dona do fragment como um listener, para ser
		// notificada ao final da edição
		if (context instanceof OnDespesaEditFinished) {
			this.listener = (OnDespesaEditFinished) context;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_editdespesa, container, false);

		edtData = view.findViewById(R.id.edt_data);
		edtData.setOnClickListener(this);

		edtDescricao = view.findViewById(R.id.edt_descricao);
		edtDescricao.addTextChangedListener(this);

		spiCategoria = view.findViewById(R.id.spi_categoria);
		spiCategoria.setOnItemSelectedListener(this);

		edtValor = view.findViewById(R.id.edt_valor);
		edtValor.addTextChangedListener(this);

		btnGravar = view.findViewById(R.id.btn_gravar);
		btnGravar.setOnClickListener(this);
		btnGravar.setEnabled(false);

		Button btnCancelar = view.findViewById(R.id.btn_cancelar);
		btnCancelar.setOnClickListener(this);

		Button btnCategoria = view.findViewById(R.id.btn_categoria);
		btnCategoria.setOnClickListener(this);

		layout = view.findViewById(R.id.layout);
		progress = view.findViewById(R.id.progress);

		categoriasAdapter = new CategoriasAdapter(getActivity());
		spiCategoria.setAdapter(categoriasAdapter);

		if (savedInstanceState != null) {
			// Se a activity está sendo recriada, os atributos do fragment têm
			// seus valores recuperados
			this.despesa = (Despesa) savedInstanceState.getSerializable("despesa");
			this.novaCategoria = (Categoria) savedInstanceState.getSerializable("categoria");
			this.spiCategoria.setSelection(savedInstanceState.getInt("selectedCategoria"));
			if (savedInstanceState.containsKey("despesaId")) {
				this.despesaId = savedInstanceState.getLong("despesaId");
			}
			configurationChanged = true;
		}

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Exibe a view de progresso
		layout.setVisibility(View.GONE);
		progress.setVisibility(View.VISIBLE);

		// Inicia o loader. Começa carregando as categorias
		getLoaderManager().initLoader(LOADER_CATEGORIAS, null, this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.edt_data) {
			SelectDateDialog dialog = SelectDateDialog.newInstance(this);

			String date = edtData.getText().toString();

			if (!StringUtils.isEmptyOrWhiteSpaces(date)) {
				int[] info = DateUtils.parseDateInfo(date);
				dialog.setDate(info[2], info[1], info[0]);
			}

			dialog.show(getFragmentManager(), "dateDialog");

		} else if (v.getId() == R.id.btn_gravar) {
			despesa.setData(DateUtils.createDate(edtData.getText().toString().trim()));
			despesa.setDescricao(edtDescricao.getText().toString().trim());
			despesa.setValor(Double.parseDouble(edtValor.getText().toString().trim()));

			if (despesaId == null) {
				DAOFactory.getDepsesaDAO(getActivity()).save(despesa);
			} else {
				DAOFactory.getDepsesaDAO(getActivity()).update(despesa);
			}

			if (listener != null) {
				listener.onDespesaEditFinished(despesa, true);
			}

		} else if (v.getId() == R.id.btn_cancelar) {
			if (listener != null) {
				listener.onDespesaEditFinished(despesa, false);
			}

		} else if (v.getId() == R.id.btn_categoria) {
			CreateCategoriaDialog dialog = CreateCategoriaDialog.newInstance(this);
			dialog.show(getFragmentManager(), "categoriaDialog");
		}
	}

	@Override
	public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
		edtData.setText(DateUtils.formatDate(year, monthOfYear, dayOfMonth));
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id == LOADER_DESPESA) {
			return DAOFactory.getDepsesaDAO(getActivity()).getDespesaById(despesaId);

		} else {
			return DAOFactory.getCategoriaDAO(getActivity()).getCategorias();
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (loader.getId() == LOADER_CATEGORIAS) {
			// Atualiza o cursor e o adapter de categorias com os dados carregados
			categoriasAdapter.swapCursor(cursor);
			categoriasAdapter.updateData();

			if (!configurationChanged) {
				// Se não houve mudança de configuração, continua com o carregamento

				// Coloca o foco no campo da descrição
				edtDescricao.requestFocus();

				if (despesaId != null) {
					// Se for edição de uma despesa, carrega a despesa
					getLoaderManager().initLoader(LOADER_DESPESA, null, this);

				} else {
					// Se for a criação de um despesa, marca o campo de data com a data de hoje
					int[] today = DateUtils.today();
					edtData.setText(DateUtils.formatDate(today[2], today[1], today[0]));

					// Para de exibir o indicador de progresso
					layout.setVisibility(View.VISIBLE);
					progress.setVisibility(View.GONE);
				}
			
			} else {
				// É uma mudança de configuração, então apenas seleciona a categoria previamente selecionada
				spiCategoria.setSelection(categoriasAdapter.getPosition(novaCategoria));
				novaCategoria = null;
			}
		
		} else if (loader.getId() == LOADER_DESPESA) {
			// Carrega o objeto Despesa com os dados do cursor
			cursor.moveToFirst();
			despesa.loadFromCursor(cursor);

			// Coloca os dados da despesa na tela
			edtData.setText(DateUtils.formatDate(despesa.getData()));
			edtDescricao.setText(despesa.getDescricao());
			edtValor.setText(String.valueOf(despesa.getValor()));
			spiCategoria.setSelection(categoriasAdapter.getPosition(despesa.getCategoria()));

			// Para de exibir o indicador de progresso
			layout.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (loader.getId() == LOADER_CATEGORIAS) {
			categoriasAdapter.swapCursor(null);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
		// Quando uma categoria é selecionada, atrela ela à despesa
		despesa.setCategoria(categoriasAdapter.getItem(position));
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	}

	@Override
	public void onCategoriaCreate(String nome) {
		// Uma nova categoria foi criada. Grava os dados no objeto Categoria e salva no banco de dados
		novaCategoria = new Categoria();
		novaCategoria.setNome(nome);
		DAOFactory.getCategoriaDAO(getActivity()).save(novaCategoria);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	// Método chamado quando as caixas de edição de descrição e/ou valor tiverem seus textos alterados
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		boolean valid = true;
		
		if (StringUtils.isEmptyOrWhiteSpaces(edtDescricao.getText())) {
			valid = false;
		}

		if (StringUtils.isEmptyOrWhiteSpaces(edtValor.getText())) {
			valid = false;
		}

		// O botão gravar será habilitado apenas se a descrição e o valor tiverem valores válidos
		btnGravar.setEnabled(valid);
	}

	// Interface para notificação a respeito da finalização no processo de edição de despesa
	public interface OnDespesaEditFinished {
		void onDespesaEditFinished(Despesa despesa, boolean recorded);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Salva os dados para que eles sejam recuperados depois da recriação do fragment
		outState.putSerializable("despesa", despesa);
		outState.putSerializable("categoria", novaCategoria);
		outState.putInt("selectedCategoria", spiCategoria.getSelectedItemPosition());

		if (despesaId != null) {
			outState.putLong("despesaId", despesaId);
		}
	}
}

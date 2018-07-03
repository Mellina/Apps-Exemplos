package br.com.softblue.android.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import br.com.softblue.android.R;
import br.com.softblue.android.model.Despesa;
import br.com.softblue.android.util.DateUtils;
import br.com.softblue.android.util.NumberUtils;

// Adapter para a lista de despesas
public class DespesasAdapter extends CursorAdapter implements OnCheckedChangeListener {

	private OnItemCheckedChangeListener listener;
	
	private Despesa despesa = new Despesa();
	
	// Map para mapear o ID da despesa para um objeto com informações sobre ela no adapter
	private Map<Long, Info> infoMap = new HashMap<>();

	public DespesasAdapter(Context context, OnItemCheckedChangeListener listener) {
		super(context, null, 0);
		this.listener = listener;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// Carrega os dados da despesa a partir do cursor
		despesa.loadFromCursor(cursor);

		// Define informações nas views
		
		CheckBox checkbox = view.findViewById(R.id.chk_item);
		checkbox.setOnCheckedChangeListener(this);

		TextView txtDescricao = view.findViewById(R.id.txt_descricao);
		txtDescricao.setText(despesa.getDescricao());

		TextView txtCategoria = view.findViewById(R.id.txt_categoria);
		txtCategoria.setText(despesa.getCategoria().getNome());

		TextView txtValor = view.findViewById(R.id.txt_valor);
		txtValor.setText(NumberUtils.formatAsCurrency(despesa.getValor()));

		TextView txtData = view.findViewById(R.id.txt_data);
		txtData.setText(DateUtils.formatDate(despesa.getData()));

		// Busca pela despesa no map
		Info info = infoMap.get(despesa.getId());
		if (info == null) {
			// Se ela ainda não existe, será criada no map
			info = new Info();
			
			// Cria uma nova despesa (usar o objeto compartilhado não vai funcionar)
			Despesa despesa = new Despesa(this.despesa);
			
			info.despesa = despesa;
			info.selected = false;
			
			infoMap.put(despesa.getId(), info);
		}

		// Armazena a view, posição e checkbox associados à despesa
		info.view = view;
		info.position = cursor.getPosition();
		info.checkbox = checkbox;

		// Associada o objeto Info
		checkbox.setTag(info);
		checkbox.setChecked(info.selected);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		return inflater.inflate(R.layout.adapter_despesas, parent, false);
	}

	@Override
	// Chamado quando um checkbox tem seu estado alterado (checado/não checado)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Obtém o Info associado ao checkbox
		Info model = (Info) buttonView.getTag();
		
		// Avisa o fragmenet para que ele possa atualizar o estado do item na ListView
        if (listener != null) {
            listener.onItemCheckedChanged(model.position, isChecked);
        }
		
		// Marca a despesa como selecionada
		model.selected = isChecked;

		// Mudar a cor de fundo da view, dependendo do estado do checkbox
		if (isChecked) {
			model.view.setBackgroundColor(Color.LTGRAY);
		} else {
			model.view.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	public void unselectItems() {
		// Remove as marcações dos checkboxes da lista
		for (Info model : infoMap.values()) {
			model.checkbox.setChecked(false);
		}
	}

    public interface OnItemCheckedChangeListener {
		void onItemCheckedChanged(int position, boolean checked);
    }

	// Classe que representa informações de uma despesa
	public static class Info {
		// Selecionada na lista ou não
		public boolean selected;
		
		// Posição na lista
        public int position;
		
		// View associada à despesa
        public View view;
		
		// Objeto despesa
        public Despesa despesa;
		
		// Checkbox associado
        public CheckBox checkbox;
	}
}

package br.com.softblue.android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TextFragment extends Fragment implements View.OnClickListener {

    private EditText edtText;
    private OnInvertListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof OnInvertListener)) {
            throw new RuntimeException("A activity deve implementar a interface TextFragment.OnInvertListener");
        }

        listener = (OnInvertListener) context;
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_text, container, false);

        edtText = view.findViewById(R.id.edt_texto);

        Button btnInverter = view.findViewById(R.id.btn_inverter);
        btnInverter.setOnClickListener(this);

        return view;
	}

    @Override
    public void onClick(View v) {
        if (listener != null) {
            String texto = edtText.getText().toString();
            listener.onInvert(texto);
        }
    }

    public interface OnInvertListener {
        void onInvert(String text);
    }
}

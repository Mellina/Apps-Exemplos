package softblue.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TextFragment extends Fragment implements View.OnClickListener {
	
	private EditText editTextView;
	private InvertListener listener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (!(activity instanceof InvertListener)) {
			throw new RuntimeException("A activity deve implementar InvertListener");
		}
		
		listener = (InvertListener) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_text, container, false);
		
		editTextView = (EditText) view.findViewById(R.id.edt_text);
		
		Button button = (Button) view.findViewById(R.id.btn_inverter);
		button.setOnClickListener(this);
		
		return view;
		
	}

	@Override
	public void onClick(View v) {
		listener.onInvert(editTextView.getText().toString());
	}
	
	public interface InvertListener {
		public void onInvert(String text);
	}
}

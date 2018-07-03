package softblue.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends Activity implements TextFragment.InvertListener {
	
	private ResultFragment resultFrag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getFragmentManager();
		resultFrag = (ResultFragment) fm.findFragmentById(R.id.frag_result);
	}

	@Override
	public void onInvert(String text) {
		resultFrag.invert(text);
	}
}

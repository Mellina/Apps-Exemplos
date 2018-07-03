package br.com.softblue.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements RegioesListFragment.OnRegiaoSelectedListener {

    private DetailFragment detailFragment;
    private View detailLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);
        detailLayout = findViewById(R.id.lay_detail);
    }

    @Override
    public void onRegiaoSelected(Regiao regiao) {
        boolean dualPane = getResources().getBoolean(R.bool.dualPane);

        if (!dualPane) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("regiao", regiao);
            startActivityForResult(intent, 0);

        } else {
            detailLayout.setVisibility(View.VISIBLE);
            detailFragment.setRegiao(regiao);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Regiao regiao = (Regiao) data.getSerializableExtra("regiao");

            detailLayout.setVisibility(View.VISIBLE);
            detailFragment.setRegiao(regiao);
        }
    }
}

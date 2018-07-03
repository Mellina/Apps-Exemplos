package br.com.softblue.android.util;

import android.content.Context;

import br.com.softblue.android.R;

public class ScreenUtils {

	// Converte um valor em dp para pixels
	public static int convertToPixels(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	// Verifica se o aplicativo está executando em modo dual pane
	public static boolean isDualPane(Context context) {
		return context.getResources().getBoolean(R.bool.dualPane);
	}
}

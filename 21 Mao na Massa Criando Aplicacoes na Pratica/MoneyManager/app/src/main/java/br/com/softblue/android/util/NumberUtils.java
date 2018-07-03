package br.com.softblue.android.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

	private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	
	// Format um valor no formato de moeda (R$)
	public static String formatAsCurrency(double value) {
		return nf.format(value);
	}
}

package br.com.softblue.android.provider;

import android.net.Uri;

// Exceção que representa uma URI inválida
public class InvalidURIException extends RuntimeException {

	public InvalidURIException(Uri uri) {
		super("URI inválida: " + uri);
	}
}

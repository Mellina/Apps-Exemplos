package br.com.softblue.android;

import java.io.Serializable;

public class Estado implements Serializable {

	private String nome;
	private String sigla;

	public Estado(String nome, String sigla) {
		this.nome = nome;
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public String getSigla() {
		return sigla;
	}
}

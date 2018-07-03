package br.com.softblue.android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Regiao implements Serializable {

	private String nome;
	private List<Estado> estados = new ArrayList<>();

	public Regiao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	@Override
	public String toString() {
		return nome;
	}
}

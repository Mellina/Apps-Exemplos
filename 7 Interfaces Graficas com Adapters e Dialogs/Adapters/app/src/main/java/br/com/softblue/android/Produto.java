package br.com.softblue.android;

import java.util.ArrayList;
import java.util.List;

public class Produto {

    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public static List<Produto> getProdutos() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Feijão", 3.5));
        produtos.add(new Produto("Macarrão", 6));
        produtos.add(new Produto("Arroz", 7.3));
        produtos.add(new Produto("Batata", 2.4));
        produtos.add(new Produto("Cenoura", 2.6));
        return produtos;
    }
}

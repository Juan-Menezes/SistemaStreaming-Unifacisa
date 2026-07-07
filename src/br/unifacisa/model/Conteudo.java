package br.unifacisa.model;

import br.unifacisa.enums.ClassificacaoIndicativa;

public abstract class Conteudo {
    protected String titulo;
    protected ClassificacaoIndicativa classificacao;
    protected int reproducoes;

    public Conteudo(String titulo, ClassificacaoIndicativa classificacao, int reproducoes) {
        this.titulo = titulo;
        this.classificacao = classificacao;
        this.reproducoes = reproducoes;
    }

    public String getTitulo() {
        return titulo;
    }

    public ClassificacaoIndicativa getClassificacao() {
        return classificacao;
    }

    public int getReproducoes() {
        return reproducoes;
    }

    public abstract double calcularRoyalties();
}

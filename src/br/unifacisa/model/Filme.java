package br.unifacisa.model;

import br.unifacisa.enums.ClassificacaoIndicativa;

public class Filme extends Conteudo{
    public Filme(String titulo, ClassificacaoIndicativa classificacao, int reproducoes) {
        super(titulo, classificacao, reproducoes);
    }

    @Override
    public double calcularRoyalties() {
        return reproducoes * 1.50;
    }
}

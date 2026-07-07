package br.unifacisa.model;

import br.unifacisa.enums.ClassificacaoIndicativa;

public class Musica extends Conteudo{
    public Musica(String titulo, ClassificacaoIndicativa classificacao, int reproducoes) {
        super(titulo, classificacao, reproducoes);
    }

    @Override
    public double calcularRoyalties() {
        return reproducoes*0.05;
    }
}

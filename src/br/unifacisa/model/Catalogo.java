package br.unifacisa.model;

import br.unifacisa.enums.ClassificacaoIndicativa;
import java.util.ArrayList;

public class Catalogo {
    private ArrayList<Conteudo> conteudos = new ArrayList<>();

    public void adicionarConteudo(Conteudo conteudo) {
        conteudos.add(conteudo);
    }

    public void buscar(String titulo) {
        boolean encontrado = false;
        for (Conteudo conteudo : conteudos) {
            if (conteudo.titulo.equals(titulo)) {
                System.out.println("Título Encontrado!! " + conteudo.titulo);
                encontrado = true;
            }
        }
        if(encontrado == false){
            System.out.println("Título não encontrado!");
        }
    }

    public void buscar(ClassificacaoIndicativa classificacao) {
        boolean encontrado = false;
        for (Conteudo conteudo : conteudos) {
            if (conteudo.classificacao == classificacao) {
                System.out.println("Conteudo: " + conteudo.titulo);
                encontrado = true;
            }
        }
        if(encontrado==false){
            System.out.println("Nenhum conteúdo encontrado com essa classificação");
        }
    }
}


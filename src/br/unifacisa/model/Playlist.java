package br.unifacisa.model;

import br.unifacisa.enums.TipoPlano;
import br.unifacisa.exceptions.LimitePlaylistException;

import java.util.ArrayList;

public class Playlist {
    private String nome;
    private ArrayList<Conteudo> conteudos;
    private Usuario dono;

    public Playlist(String nome, Usuario dono) {
        this.nome = nome;
        this.conteudos = new ArrayList<>();
        this.dono = dono;
    }

    public void adicionarConteudo (Conteudo conteudo) throws LimitePlaylistException {
        if (dono.getPlano().getTipoPlano() == TipoPlano.GRATUITO && conteudos.size() >= 15) {
            throw new LimitePlaylistException("Erro: Plano gratuito permite apenas 15 itens");
        }
        conteudos.add(conteudo);
        System.out.printf("Conteúdo %s adicionado à Playlist %s com sucesso! \n", conteudo.titulo, nome);
    }
    public String getNome() {
        return nome;
    }

    public Usuario getDono() {
        return dono;
    }

    public ArrayList<Conteudo> getConteudos() {
        return conteudos;
    }
}

package br.unifacisa.service;

import br.unifacisa.enums.ClassificacaoIndicativa;
import br.unifacisa.exceptions.ConteudoRestritoException;
import br.unifacisa.model.Conteudo;
import br.unifacisa.model.Usuario;

public class ReproducaoService {

    public void reproduzir(Usuario usuario, Conteudo conteudo) throws ConteudoRestritoException {

        int idade = usuario.getIdade();

        ClassificacaoIndicativa classificacao =
                conteudo.getClassificacao();

        if (classificacao == ClassificacaoIndicativa.DOZE_ANOS
                && idade < 12) {

            throw new ConteudoRestritoException(
                    "Conteúdo permitido apenas para maiores de 12 anos."
            );
        }

        if (classificacao == ClassificacaoIndicativa.DEZOITO_ANOS
                && idade < 18) {

            throw new ConteudoRestritoException(
                    "Conteúdo permitido apenas para maiores de 18 anos."
            );
        }

        System.out.println(
                "Reproduzindo: " + conteudo.getTitulo()
        );
    }
}
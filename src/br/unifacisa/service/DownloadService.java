package br.unifacisa.service;

import br.unifacisa.enums.TipoPlano;
import br.unifacisa.exceptions.DownloadNaoPermitidoException;
import br.unifacisa.model.Conteudo;
import br.unifacisa.model.Usuario;

public class DownloadService {

    public void baixar(Usuario usuario, Conteudo conteudo) throws DownloadNaoPermitidoException {

        if (usuario.getPlano().getTipoPlano() == TipoPlano.GRATUITO) {

            throw new DownloadNaoPermitidoException(
                    "Download disponível apenas para usuários Premium."
            );
        }

        System.out.println(
                "Download iniciado: " + conteudo.getTitulo()
        );
    }
}
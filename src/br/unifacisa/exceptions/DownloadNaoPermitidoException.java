package br.unifacisa.exceptions;

public class DownloadNaoPermitidoException extends Exception {
    public DownloadNaoPermitidoException(String mensagem) {
        super(mensagem);
    }
}

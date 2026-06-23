package br.unifacisa.exceptions;

public class DownloadNaoPermitidoException extends RuntimeException {
    public DownloadNaoPermitidoException(String message) {
        super(message);
    }
}

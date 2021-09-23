package com.rafaelvieira.movieflix.services.exceptions;

// Retorno 403  onde verifica se o usuário esta logado.
public class ForbiddenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ForbiddenException(String msg) {
        super(msg);
    }

}

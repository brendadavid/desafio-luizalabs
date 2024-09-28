package com.desafio.luizalabs.wishlist.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum ErrosWishlist {

    EXISTE_PRODUTO_CADASTRADO("Já existe um produto com o código %s cadastrado na sua Wishlist.");

    private final String mensagem;

    ErrosWishlist(String mensagem) { this.mensagem = mensagem; }

    public ResponseStatusException exception(Object... args) {
        return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format(mensagem, args));
    }
}

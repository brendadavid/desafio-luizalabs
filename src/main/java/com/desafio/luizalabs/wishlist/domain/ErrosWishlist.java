package com.desafio.luizalabs.wishlist.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum ErrosWishlist {

    EXISTE_PRODUTO_CADASTRADO("Já existe um ou mais produtos com o mesmo id cadastrado na Wishlist do clienteId %s."),
    EXCEDEU_LIMITE_MAXIMO("Já existem 20 produtos cadastrados na Wishlist do clienteId %s."),
    NAO_EXISTE_PRODUTO_CADASTRADO("Não existe nenhum produto cadastrado na Wishlist do clienteId %s."),
    NAO_EXISTE_PRODUTO_POR_ID("Não existe um produtoId %s cadastrado na Wishlist do clienteId %s.");


    private final String mensagem;

    ErrosWishlist(String mensagem) { this.mensagem = mensagem; }

    public ResponseStatusException unprocessableEntityException(Object... args) {
        return exception(HttpStatus.UNPROCESSABLE_ENTITY, args);
    }

    public ResponseStatusException notFoundException(Object... args) {
        return exception(HttpStatus.NOT_FOUND, args);
    }

    public ResponseStatusException exception(HttpStatus status, Object... args) {
        return new ResponseStatusException(status, String.format(mensagem, args));
    }
}

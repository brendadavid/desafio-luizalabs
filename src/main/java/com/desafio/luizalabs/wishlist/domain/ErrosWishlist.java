package com.desafio.luizalabs.wishlist.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum ErrosWishlist {

    EXISTE_PRODUTO_CADASTRADO("Já existe um produto com mesmo id cadastrado na Wishlist com clienteId %s."),
    EXCEDEU_LIMITE_MAXIMO("Já existem 20 produtos cadastrados na Wishlist com clienteId %s."),
    NAO_EXISTE_PRODUTO_CADASTRADO("Não existe nenhum produto cadastrado na Wishlist com clienteId %s."),
    NAO_EXISTE_PRODUTO_POR_CODIGO("Não existe um produto com id %s cadastrado na Wishlist para esse clienteId: %s.");


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

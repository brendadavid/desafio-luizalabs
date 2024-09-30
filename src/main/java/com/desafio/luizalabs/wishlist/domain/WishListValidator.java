package com.desafio.luizalabs.wishlist.domain;

import com.desafio.luizalabs.wishlist.infrastructure.ProdutoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class WishListValidator {

    private static final int MAX_PRODUTOS = 20;

    public void validaSeJaExisteProdutoCadastrado(boolean existeProduto, Long clienteId) {
        if (existeProduto) {
            throw ErrosWishlist.EXISTE_PRODUTO_CADASTRADO.unprocessableEntityException(clienteId);
        }
    }

    public void verificaLimiteMaximoDeProdutos(List<Long> produtoIds, Long clienteId) {
        if (produtoIds.size() > MAX_PRODUTOS) {
            throw ErrosWishlist.EXCEDEU_LIMITE_MAXIMO.unprocessableEntityException(clienteId);
        }
    }

    public void validaSeExisteProdutoCadastradoPorId(ProdutoProjection projection, Long clienteId, Long produtoId) {
        if (isEmpty(projection)) {
           throw ErrosWishlist.NAO_EXISTE_PRODUTO_POR_CODIGO.notFoundException(produtoId, clienteId);
        }
    }

    public void validaSeExisteAlgumProdutoCadastrado(Long clienteId, List<Long> produtos) {
        if (produtos.isEmpty()) {
            throw ErrosWishlist.NAO_EXISTE_PRODUTO_CADASTRADO.notFoundException(clienteId);
        }
    }
}

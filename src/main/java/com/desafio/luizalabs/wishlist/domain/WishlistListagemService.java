package com.desafio.luizalabs.wishlist.domain;

import com.desafio.luizalabs.wishlist.domain.bo.ProdutoBO;
import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.infrastructure.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistListagemService {

    private final WishlistRepository repository;
    private final WishListValidator validator;
    private final WishlistMapper mapper;
    private final MongoOperations mongoOperations;

    public WishlistBO buscarProdutos(Long clienteId) {
        var wishlist = repository.buscarTodosOsProdutos(mongoOperations, clienteId);
        validator.validaSeExisteAlgumProdutoCadastrado(clienteId, wishlist);

        return mapper.toBO(wishlist);
    }

    public ProdutoBO buscarProdutoPorId(Long clienteId, Long produtoId) {
        var produtoProjection = repository.buscarProdutoPorId(mongoOperations, clienteId, produtoId);
        validator.validaSeExisteProdutoCadastradoPorId(produtoProjection, clienteId, produtoId);

        return mapper.toBO(produtoProjection);
    }
}

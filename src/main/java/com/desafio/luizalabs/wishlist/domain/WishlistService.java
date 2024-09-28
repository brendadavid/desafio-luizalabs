package com.desafio.luizalabs.wishlist.domain;

import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.infrastructure.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository repository;
    private final WishlistMapper mapper;
    private final MongoOperations mongoOperations;

    public void adicionarProdutos(WishlistBO bo) {
        validaSeExisteProdutoCadastrado(bo.getCodigo());
        this.mongoOperations.save(mapper.toModel(bo));
    }

    private void validaSeExisteProdutoCadastrado(String codigo) {
        var existeProduto = repository.buscarProduto(mongoOperations, codigo);

        if (existeProduto) {
            throw ErrosWishlist.EXISTE_PRODUTO_CADASTRADO.exception(codigo);
        }
    }
}

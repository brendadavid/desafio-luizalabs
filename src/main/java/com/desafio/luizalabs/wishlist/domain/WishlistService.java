package com.desafio.luizalabs.wishlist.domain;

import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.infrastructure.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository repository;
    private final WishListValidator validator;
    private final WishlistMapper mapper;
    private final MongoOperations mongoOperations;

    @Transactional
    public void adicionarProdutos(WishlistBO bo) {
        var existeProduto =
                repository.buscarProduto(
                        mongoOperations,
                        bo.getProdutoIds(),
                        bo.getClienteId()
                );

        validator.validaSeJaExisteProdutoCadastrado(existeProduto, bo.getClienteId());

        repository.findByClienteId(bo.getClienteId())
                .ifPresentOrElse(
                        wishlist ->
                        {
                            wishlist.getProdutoIds().addAll(bo.getProdutoIds());
                            validator.verificaLimiteMaximoDeProdutos(wishlist.getProdutoIds(), wishlist.getClienteId());
                            mongoOperations.save(wishlist);
                        },
                        () -> this.mongoOperations.save(mapper.toModel(bo))
                );
    }

    public void deletarPorCodigo(Long clienteId, Long produtoId) {
        var produtoProjection = repository.buscarProdutoPorId(mongoOperations, clienteId, produtoId);
        validator.validaSeExisteProdutoCadastradoPorId(produtoProjection, clienteId, produtoId);

        repository.deletarPorCodigo(mongoOperations, produtoId, clienteId);
    }
}

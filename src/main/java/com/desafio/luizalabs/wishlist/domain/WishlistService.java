package com.desafio.luizalabs.wishlist.domain;

import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import com.desafio.luizalabs.wishlist.infrastructure.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository repository;
    private final WishlistMapper mapper;
    private final MongoOperations mongoOperations;

    @Transactional
    public void adicionarProdutos(WishlistBO bo) {
        validaSeJaExisteProdutoCadastrado(bo.getCodigoProduto(), bo.getClienteId());
        this.mongoOperations.save(mapper.toModel(bo));
    }

    public List<WishlistBO> buscarProdutos(Long clienteId) {
        var produtos = repository.buscarTodosOsProdutos(mongoOperations, clienteId);
        validaSeExisteAlgumProdutoCadastrado(clienteId, produtos);

        return produtos.stream()
                .map(mapper::toBO)
                .toList();
    }

    public WishlistBO buscarProdutoPorCodigo(Long clienteId, String codigoProduto) {
        var produto = validaSeExisteProdutoCadastradoPorCodigo(codigoProduto, clienteId);

        return mapper.toBO(produto);
    }

    public void deletarPorCodigo(Long clienteId, String codigoProduto) {
        validaSeExisteProdutoCadastradoPorCodigo(codigoProduto, clienteId);
        repository.deletarPorCodigo(mongoOperations, codigoProduto, clienteId);
    }

    private void validaSeJaExisteProdutoCadastrado(String codigoProduto, Long clienteId) {
        var existeProduto = repository.buscarProduto(mongoOperations, codigoProduto, clienteId);

        if (existeProduto) {
            throw ErrosWishlist.EXISTE_PRODUTO_CADASTRADO.unprocessableEntityException(codigoProduto, clienteId);
        }
    }

    private void validaSeExisteAlgumProdutoCadastrado(Long clienteId, List<Wishlist> produtos) {
        if (produtos.isEmpty()) {
            throw ErrosWishlist.NAO_EXISTE_PRODUTO_CADASTRADO.notFoundException(clienteId);
        }
    }

    private Wishlist validaSeExisteProdutoCadastradoPorCodigo(String codigoProduto, Long clienteId) {
        return repository.findByCodigoProdutoAndClienteId(codigoProduto, clienteId)
                .orElseThrow(() ->
                        ErrosWishlist.NAO_EXISTE_PRODUTO_POR_CODIGO.notFoundException(codigoProduto, clienteId)
                );
    }
}

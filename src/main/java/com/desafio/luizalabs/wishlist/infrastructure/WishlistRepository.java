package com.desafio.luizalabs.wishlist.infrastructure;

import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.codehaus.plexus.util.StringUtils.isNotEmpty;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String>{

    default boolean buscarProduto(
            MongoOperations mongoOperations,
            String codigoProduto,
            Long clienteId
    ) {
        Query query = new Query();

        if (isNotEmpty(codigoProduto)) {
            query.addCriteria(where("codigoProduto").is(codigoProduto)
                    .and("clienteId").is(clienteId));
        }

        return mongoOperations.exists(query, Wishlist.class);
    }

    default List<Wishlist> buscarTodosOsProdutos(
            MongoOperations mongoOperations,
            Long clienteId
    ) {
        Query query = new Query();

        query.addCriteria(where("clienteId").is(clienteId));

        return mongoOperations.find(query, Wishlist.class);
    }

    default void deletarPorCodigo(
            MongoOperations mongoOperations,
            String codigoProduto,
            Long clienteId
    ) {
        Query query = new Query();

        query.addCriteria(where("codigoProduto").is(codigoProduto)
                .and("clienteId").is(clienteId));

        mongoOperations.findAndRemove(query, Wishlist.class);
    }

    Optional<Wishlist> findByCodigoProdutoAndClienteId(String codigoProduto, Long clienteId);
}

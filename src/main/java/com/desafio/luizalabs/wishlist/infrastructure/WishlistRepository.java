package com.desafio.luizalabs.wishlist.infrastructure;

import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String>{

    default boolean buscarProduto(
            MongoOperations mongoOperations,
            List<Long> produtoIds,
            Long clienteId
    ) {
        Query query = new Query();

        query.addCriteria(where("produtoIds").in(produtoIds)
                .and("clienteId").is(clienteId));

        return mongoOperations.exists(query, Wishlist.class);
    }

    default Wishlist buscarTodosOsProdutos(
            MongoOperations mongoOperations,
            Long clienteId
    ) {
        Query query = new Query();

        query.addCriteria(where("clienteId").is(clienteId));

        return mongoOperations.findOne(query, Wishlist.class);
    }

    default ProdutoProjection buscarProdutoPorId(
            MongoOperations mongoOperations,
            Long clienteId,
            Long produtoId
    ) {
        Query query = new Query();

        query.addCriteria(where("produtoIds").is(produtoId)
                .and("clienteId").is(clienteId));

        query.fields().include("produtoIds.$");

        return mongoOperations.findOne(query, ProdutoProjection.class, "wishlist");
    }

    default void deletarProdutoPorId(
            MongoOperations mongoOperations,
            Long produtoId,
            Long clienteId
    ) {
        Query query = new Query();

        query.addCriteria(where("produtoIds").is(produtoId)
                .and("clienteId").is(clienteId));

        Update update = new Update();
        update.pull("produtoIds", produtoId);

        mongoOperations.updateFirst(query, update, Wishlist.class);
    }

    Optional<Wishlist> findByClienteId(Long clienteId);
}

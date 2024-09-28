package com.desafio.luizalabs.wishlist.infrastructure;

import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import static org.codehaus.plexus.util.StringUtils.isNotEmpty;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    default boolean buscarProduto(
            MongoOperations mongoOperations,
            String codigo
    ) {
        Query query = new Query();

        if (isNotEmpty(codigo)) {
            query.addCriteria(where("codigo").is(codigo));
        }

        return mongoOperations.exists(query, Wishlist.class);
    }
}

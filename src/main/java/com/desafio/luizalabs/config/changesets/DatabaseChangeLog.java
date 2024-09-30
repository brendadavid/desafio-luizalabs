package com.desafio.luizalabs.config.changesets;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.BsonDocument;

@ChangeLog
public class DatabaseChangeLog {

    private static final String COLLECTION_WISHLIST = "wishlist";

    @ChangeSet(order = "01", id = "2024-09-29 20:00", author = "Brenda David")
    public void criarIndiceClientId(MongoDatabase db) {
        IndexOptions options = new IndexOptions()
                .name("clientid_wishlist_index")
                .background(true);

        db.getCollection(COLLECTION_WISHLIST)
                .createIndex(BsonDocument.parse("{ 'clientId': 1 }"), options);
    }

    @ChangeSet(order = "02", id = "2024-09-29 23:00", author = "Brenda David")
    public void criarIndiceProdutoIds(MongoDatabase db) {
        IndexOptions options = new IndexOptions()
                .name("produtoids_wishlist_index")
                .background(true);

        db.getCollection(COLLECTION_WISHLIST)
                .createIndex(BsonDocument.parse("{ 'produtoIds': 1 }"), options);
    }
}

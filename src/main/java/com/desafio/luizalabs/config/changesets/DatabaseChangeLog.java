package com.desafio.luizalabs.config.changesets;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.BsonDocument;

@ChangeLog
public class DatabaseChangeLog {

    private static final String COLLECTION_PRODUTOS = "wishlist";

    @ChangeSet(order = "01", id = "2024-09-26 20:00", author = "Brenda David")
    public void criarIndiceCodigo(MongoDatabase db) {
        IndexOptions options = new IndexOptions()
                .name("codigo_wishlist_index")
                .background(true);

        db.getCollection(COLLECTION_PRODUTOS)
                .createIndex(BsonDocument.parse("{ 'codigo': 1 }"), options);
    }

    @ChangeSet(order = "02", id = "2024-09-27 19:00", author = "Brenda David")
    public void criarIndiceNome(MongoDatabase db) {
        IndexOptions options = new IndexOptions()
                .name("nome_wishlist_index")
                .background(true);

        db.getCollection(COLLECTION_PRODUTOS)
                .createIndex(BsonDocument.parse("{ 'nome': 1 }"), options);
    }
}

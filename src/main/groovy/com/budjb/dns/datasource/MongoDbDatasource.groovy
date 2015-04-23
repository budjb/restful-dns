package com.budjb.dns.datasource

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

class MongoDbDatasource {
    /**
     * MongoDB client instance.
     */
    protected MongoClient mongoClient

    /**
     * Makes the connection to the MongoDB server(s).
     */
    void connect() {
        mongoClient = new MongoClient('localhost') // TODO: use configuration for server and creds
    }


    /**
     * Retrieves a named database from the database server.
     *
     * @param name
     * @return
     */
    MongoDatabase getDatabase(String name) {
        return mongoClient.getDatabase(name)
    }

    /**
     * Retrieves a named collection from the configured database.
     *
     * @param name
     * @return
     */
    MongoCollection<Document> getCollection(String name) {
        return getDatabase(/* TODO: config */'restful-dns').getCollection(name)
    }
}

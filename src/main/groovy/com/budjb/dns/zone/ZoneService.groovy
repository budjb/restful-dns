package com.budjb.dns.zone

import com.budjb.dns.Classification
import com.budjb.dns.RecordType
import com.budjb.dns.datasource.MongoDbDatasource
import com.budjb.dns.zone.record.Record
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.conversions.Bson
import org.springframework.beans.factory.annotation.Autowired

import static com.mongodb.client.model.Filters.eq
import static com.mongodb.client.model.Filters.regex

class ZoneService {
    /**
     * MongoDB datasource.
     */
    @Autowired
    MongoDbDatasource mongoDbDatasource

    /**
     * Name for the database collection containing DNS zones.
     */
    static final String MONGO_ZONE_COLLECTION_NAME = 'zones'

    /**
     * Returns whether the given record matches the given parameters.
     *
     * @param record
     * @param recordType
     * @param classification
     * @return
     */
    boolean isRecordMatching(Record record, RecordType recordType, Classification classification) {
        if (record.classification != classification) {
            return false
        }

        if (record.recordType == recordType) {
            return true
        }

        return false
    }

    /**
     * Returns whether the given record matches the given parameters.
     *
     * @param record
     * @param name
     * @param recordType
     * @param classification
     * @return
     */
    boolean isRecordMatching(Record record, String name, RecordType recordType, Classification classification) {
        if (!isRecordMatching(record, recordType, classification)) {
            return false
        }

        return name == record.name
    }

    /**
     * Finds all records in a zone with a given record type and classification.
     *
     * @param zone
     * @param recordType
     * @param classification
     * @return
     */
    Set<Record> findRecords(Zone zone, RecordType recordType, Classification classification) {
        Set<Record> matches = zone.records.findAll { isRecordMatching(it, recordType, classification) }

        if (recordType == RecordType.CNAME && matches.size() > 1) {
            // TODO: this may need better error handling, but CNAMEs should be mutually exclusive with other records.
            return []
        }

        return matches
    }

    /**
     * Finds all records in a zone with a given name, record type, and classification.
     *
     * @param zone
     * @param name
     * @param recordType
     * @param classification
     * @return
     */
    Set<Record> findRecords(Zone zone, String name, RecordType recordType, Classification classification) {
        name = stripDomainRoot(name, zone.name)

        Set<Record> matches = zone.records.findAll { isRecordMatching(it, name, recordType, classification) }

        if (recordType == RecordType.CNAME && matches.size() > 1) {
            // TODO: this may need better error handling, but CNAMEs should be mutually exclusive with other records.
            return []
        }

        return matches
    }

    /**
     * Attempts to find a zone by its name.
     *
     * @param name Full name of the zone.
     * @return Matching zone, or null if not found.
     */
    Zone loadZone(String name) {
        Document document = getCollection().find(eq('name', name)).first()

        if (!document) {
            return null
        }

        return new Zone(document)
    }

    /**
     * Attempt to find a zone that matches the query.
     *
     * @param query
     * @return
     */
    Zone findZone(String query) {
        List<String> tokens = query.split('\\.')

        MongoCollection<Document> collection = getCollection()

        Document document
        while (tokens.size()) {
            String lookup = tokens.join('.')

            Bson filter = regex('name', "^(.*\\.)?$lookup\$")

            document = collection.find(filter).first()

            if (document) {
                break
            }

            tokens.remove(0)
        }

        if (document) {
            return new Zone(document)
        }

        return null
    }

    /**
     * Returns the MongoDB collection containing DNS zones.
     *
     * @return
     */
    MongoCollection<Document> getCollection() {
        return mongoDbDatasource.getCollection(MONGO_ZONE_COLLECTION_NAME)
    }

    /**
     * Strips the domain root from the query string.
     *
     * @param query
     * @param root
     * @return
     */
    String stripDomainRoot(String query, String root) {
        if (query == Zone.ROOT_ALIAS) {
            return query
        }

        int index = query.lastIndexOf(root)

        if (index == -1) {
            return query
        }

        String stripped = query.substring(0, index)

        if (!stripped) {
            return Zone.ROOT_ALIAS
        }

        if (stripped[-1] == '.') {
            stripped = stripped.substring(0, stripped.lastIndexOf('.'))
        }

        if (!stripped) {
            return Zone.ROOT_ALIAS
        }

        return stripped
    }
}

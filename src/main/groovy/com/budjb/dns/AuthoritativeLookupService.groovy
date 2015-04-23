package com.budjb.dns

import com.budjb.dns.datasource.MongoDbDatasource
import com.budjb.dns.zone.Zone
import com.budjb.dns.zone.record.Record
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.conversions.Bson
import org.springframework.beans.factory.annotation.Autowired

import static com.mongodb.client.model.Filters.regex
import static com.mongodb.client.model.Filters.eq

/**
 * Service responsible for looking up authoritative answers to DNS questions.
 */
class AuthoritativeLookupService {
}

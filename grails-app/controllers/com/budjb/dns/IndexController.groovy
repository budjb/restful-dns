package com.budjb.dns

import com.budjb.dns.datasource.MongoDbDatasource
import com.budjb.dns.zone.Zone
import com.budjb.dns.zone.record.AddressRecord
import com.budjb.dns.zone.record.CnameRecord
import com.budjb.dns.zone.record.SoaRecord
import groovy.json.JsonBuilder
import org.bson.Document

class IndexController {
    MongoDbDatasource mongoDbDatasource

    AuthoritativeLookupService authoritativeLookupService

    def zone() {
        SoaRecord soa = new SoaRecord()
        soa.email = 'bud@budjb.com'
        soa.nameserver = 'ns1.budjb.com'
        soa.serial = 123456
        soa.classification = Classification.INTERNET

        Zone zone = new Zone()
        zone.name = 'example.com'
        zone.ttl = 60 * 60 * 24 // 1 day
        zone.soaRecord = soa

        AddressRecord a1 = new AddressRecord()
        a1.name = 'git'
        a1.ip = '192.168.1.5'
        a1.classification = Classification.INTERNET
        zone.addRecord(a1)

        CnameRecord cn1 = new CnameRecord()
        cn1.name = 'www'
        cn1.alias = 'budjb.com'
        cn1.classification = Classification.INTERNET
        zone.addRecord(cn1)

        render text: new JsonBuilder(zone.toMap()).toPrettyString(), contentType: 'text/plain'
    }

    def mongo() {
        SoaRecord soa = new SoaRecord()
        soa.email = 'bud@budjb.com'
        soa.nameserver = 'ns1.budjb.com'
        soa.serial = 123456
        soa.classification = Classification.INTERNET

        Zone zone = new Zone()
        zone.name = 'example.com'
        zone.ttl = 60 * 60 * 24 // 1 day
        zone.soaRecord = soa

        AddressRecord a1 = new AddressRecord()
        a1.name = 'git'
        a1.ip = '192.168.1.5'
        a1.classification = Classification.INTERNET
        zone.addRecord(a1)

        CnameRecord cn1 = new CnameRecord()
        cn1.name = 'www'
        cn1.alias = 'budjb.com'
        cn1.classification = Classification.INTERNET
        zone.addRecord(cn1)

        Document document = new Document(zone.toMap())

        mongoDbDatasource.getCollection('zones').insertOne(document)
    }

    def getmongo() {
        render text: '', contentType: 'text/plain'

        Zone zone = authoritativeLookupService.findZone(params.query)

        if (zone) {
            render new JsonBuilder(zone.toMap()).toPrettyString()
        }
        else {
            render 'no match'
        }
    }
}
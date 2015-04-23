package com.budjb.dns.resolver

import com.budjb.dns.Classification
import com.budjb.dns.Question
import com.budjb.dns.RecordType
import com.budjb.dns.Response
import com.budjb.dns.server.DnsRequestParser
import com.budjb.dns.zone.Zone
import com.budjb.dns.zone.ZoneService
import com.budjb.dns.zone.record.AddressRecord
import com.budjb.dns.zone.record.CnameRecord
import com.budjb.dns.zone.record.NsRecord
import com.budjb.dns.zone.record.SoaRecord
import spock.lang.Specification

class AuthoritativeResolverSpec extends Specification {
    Zone zone
    AuthoritativeResolver authoritativeResolver
    ZoneService zoneService

    NsRecord ns1
    NsRecord ns2
    SoaRecord soa
    AddressRecord rootv4
    CnameRecord wwwv4
    AddressRecord nsA

    def setup() {
        ns1 = new NsRecord()
        ns1.name = '@'
        ns1.classification = Classification.INTERNET
        ns1.ns = 'ns1.budjb.com'

        ns2 = new NsRecord()
        ns2.name = '@'
        ns2.classification = Classification.INTERNET
        ns2.ns = 'ns2.example.com'

        soa = new SoaRecord()
        soa.name = '@'
        soa.classification = Classification.INTERNET
        soa.serial = 2015042201
        soa.email = 'foobar@budjb.com'
        soa.nameserver = 'ns1.budjb.com'
        soa.expire = 13245
        soa.refresh = 12345
        soa.retry = 12345
        soa.negativeTtl = 12345

        rootv4 = new AddressRecord()
        rootv4.name = '@'
        rootv4.classification = Classification.INTERNET
        rootv4.ip = '192.168.0.20'

        wwwv4 = new CnameRecord()
        wwwv4.name = 'www'
        wwwv4.classification = Classification.INTERNET
        wwwv4.alias = '@'

        nsA = new AddressRecord()
        nsA.name = 'ns1'
        nsA.classification = Classification.INTERNET
        nsA.ip = '192.168.0.2'

        zone = new Zone()
        zone.name = 'budjb.com'
        zone.soaRecord = soa
        zone.addRecord(ns1)
        zone.addRecord(ns2)
        zone.addRecord(rootv4)
        zone.addRecord(wwwv4)
        zone.addRecord(nsA)

        zoneService = Spy(ZoneService)
        zoneService.findZone(*_) >> zone

        authoritativeResolver = new AuthoritativeResolver()
        authoritativeResolver.zoneService = zoneService
    }

    def 'Ensure all required records are returned with a CNAME match'() {
        setup:
        Question question = new Question()
        question.name = 'www.budjb.com'
        question.classification = Classification.INTERNET
        question.type = RecordType.A

        DnsRequestParser parser = Mock(DnsRequestParser)
        parser.getQuestion() >> question
        parser.getIsRecursionDesired() >> true
        parser.getMessageId() >> 12345

        Response response = new Response(parser)

        when:
        authoritativeResolver.answer(question, response)

        then:
        response.isRecursionDesired()
        response.getMessageId() == 12345

        response.answers.size() == 2
        response.answers.contains(wwwv4)
        response.answers.contains(rootv4)

        response.authority.size() == 2
        response.authority.contains(ns1)
        response.authority.contains(ns2)

        response.additional.size() == 1
        response.additional.contains(nsA)

        response.isAuthoritative()
    }

    def 'Ensure all required records are returned with an address match'() {
        setup:
        Question question = new Question()
        question.name = 'budjb.com'
        question.classification = Classification.INTERNET
        question.type = RecordType.A

        DnsRequestParser parser = Mock(DnsRequestParser)
        parser.getQuestion() >> question
        parser.getIsRecursionDesired() >> true
        parser.getMessageId() >> 12345

        Response response = new Response(parser)

        when:
        authoritativeResolver.answer(question, response)

        then:
        response.isRecursionDesired()
        response.getMessageId() == 12345

        response.answers.size() == 1
        response.answers.contains(rootv4)

        response.authority.size() == 2
        response.authority.contains(ns1)
        response.authority.contains(ns2)

        response.additional.size() == 1
        response.additional.contains(nsA)

        response.isAuthoritative()
    }
}

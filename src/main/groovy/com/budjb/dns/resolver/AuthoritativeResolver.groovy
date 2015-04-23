package com.budjb.dns.resolver

import com.budjb.dns.Question
import com.budjb.dns.RecordType
import com.budjb.dns.Response
import com.budjb.dns.zone.ZoneService
import com.budjb.dns.zone.record.CnameRecord
import com.budjb.dns.zone.record.NsRecord
import com.budjb.dns.zone.record.Record
import org.springframework.beans.factory.annotation.Autowired

class AuthoritativeResolver {
    /**
     * Zone service.
     */
    @Autowired
    ZoneService zoneService

    /**
     * Attempts to answer a question.
     *
     * @param question
     * @param response
     */
    void answer(Question question, Response response) {
        ResolverContext context = new ResolverContext()
        context.question = question
        context.response = response

        addAnswers(context)
    }

    /**
     * Attempts to answer a question authoritatively.
     *
     * @param question
     */
    void addAnswers(ResolverContext context) {
        if (context.zone && context.question.name != '@' && !context.question.name.endsWith(context.zone.name)) {
            return
        }

        if (!context.zone) {
            context.zone = zoneService.findZone(context.question.name)

            if (!context.zone) {
                return
            }
        }

        Set<Record> records = zoneService.findRecords(context.zone, context.question.name, context.question.type, context.question.classification)

        if (!records.size() && context.question.type in [RecordType.A, RecordType.AAAA]) {
            records = zoneService.findRecords(context.zone, context.question.name, RecordType.CNAME, context.question.classification)
        }

        if (!records.size()) {
            return
        }

        records.each {
            context.response.addAnswer(it)

            if (it.recordType == RecordType.CNAME) {
                Question question = new Question()
                question.classification = context.question.classification
                question.type = context.question.type
                question.name = (it as CnameRecord).alias

                ResolverContext cnameContext = new ResolverContext()
                cnameContext.question = question
                cnameContext.zone = context.zone
                cnameContext.response = context.response

                addAnswers(cnameContext)
            }

        }

        addAuthorities(context)
    }

    /**
     * Add any authority records for the zone to the response. Also attempts to
     * authoritatively add those authorities' addresses.
     *
     * @param context
     */
    void addAuthorities(ResolverContext context) {
        zoneService.findRecords(context.zone, RecordType.NS, context.question.classification).each {
            context.response.addAuthority(it)

            Question question = new Question()
            question.classification = context.question.classification
            question.type = context.question.type
            question.name = (it as NsRecord).ns

            ResolverContext nsContext = new ResolverContext()
            nsContext.question = question
            nsContext.zone = context.zone
            nsContext.response = context.response

            addAdditional(nsContext)
        }
    }

    /**
     * Add additional entries to the response.
     *
     * @param context
     */
    void addAdditional(ResolverContext context) {
        zoneService.findRecords(context.zone, context.question.name, context.question.type, context.question.classification).each {
            context.response.addAdditional(it)
        }
    }
}

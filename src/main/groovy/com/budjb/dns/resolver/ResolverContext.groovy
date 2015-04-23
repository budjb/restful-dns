package com.budjb.dns.resolver

import com.budjb.dns.Question
import com.budjb.dns.Response
import com.budjb.dns.zone.Zone

class ResolverContext {
    /**
     * Matching DNS zone.
     */
    Zone zone

    /**
     * Request question.
     */
    Question question

    /**
     * Response object.
     */
    Response response
}

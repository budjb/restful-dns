package com.budjb.dns.server

import com.budjb.dns.Question

/**
 * An object that represents a DNS request.
 */
interface DnsRequestParser {
    /**
     * Returns the request message ID.
     *
     * @return
     */
    int getMessageId()

    /**
     * Returns whether the request is truncated.
     *
     * @return
     */
    boolean getIsTruncated()

    /**
     * Returns whether recursion is desired.
     *
     * @return
     */
    boolean getIsRecursionDesired()

    /**
     * Returns a list of questions posed in the request.
     *
     * @return
     */
    Question getQuestion()
}

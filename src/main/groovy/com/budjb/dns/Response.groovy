package com.budjb.dns

import com.budjb.dns.server.DnsRequestParser
import com.budjb.dns.zone.record.Record

class Response {
    /**
     * Message ID.
     */
    int messageId

    /**
     * Operation code.
     */
    OperationCode operationCode

    /**
     * Whether recursion was desired.
     */
    boolean recursionDesired

    /**
     * Whether recursion is available.
     */
    boolean recursionAvailable

    /**
     * Response code.
     */
    ResponseCode responseCode

    /**
     * The list of questions posed in the request.
     */
    Question question

    /**
     * The list of answers to respond with.
     */
    Set<Record> answers = new LinkedHashSet<Record>()

    /**
     * The list of authority responses.
     */
    Set<Record> authority = new LinkedHashSet<Record>()

    /**
     * The list of additional answers in the response.
     */
    Set<Record> additional = new LinkedHashSet<Record>()

    /**
     * Constructor.
     *
     * @param parser
     */
    Response(DnsRequestParser parser) {
        question = parser.question
        recursionDesired = parser.isRecursionDesired
        messageId = parser.messageId
    }

    /**
     * Disable setting questions.
     *
     * @param questions
     */
    void setQuestions(Question question) {
        throw new UnsupportedOperationException()
    }

    /**
     * Disable setting answer records.
     *
     * @param answers
     */
    void setAnswers(Set<Record> answers) {
        throw new UnsupportedOperationException()
    }

    /**
     * Disable setting authority records.
     *
     * @param authority
     */
    void setAuthority(Set<Record> authority) {
        throw new UnsupportedOperationException()
    }

    /**
     * Disable setting additional records.
     */
    void setAdditional(Set<Record> additional) {
        throw new UnsupportedOperationException()
    }

    /**
     * Add an answer to the response.
     *
     * @param answer
     */
    void addAnswer(Record answer) {
        this.answers.add(answer)
    }

    /**
     * Add an authority record to the response.
     *
     * @param authority
     */
    void addAuthority(Record authority) {
        this.authority.add(authority)
    }

    /**
     * Add an additional answer to the response.
     *
     * @param additional
     */
    void addAdditional(Record additional) {
        this.additional.add(additional)
    }

    /**
     * Whether the response is authoritative.
     *
     * @return
     */
    boolean isAuthoritative() {
        return this.authority.size() > 0
    }
}

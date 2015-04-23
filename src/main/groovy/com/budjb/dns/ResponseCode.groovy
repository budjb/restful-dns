package com.budjb.dns

/**
 * An enumeration of the various response codes the server can respond with.
 */
enum ResponseCode {
    /**
     * No error in the response.
     */
    NO_ERROR(0),

    /**
     * The request could not be parsed.
     */
    FORMAT_ERROR(1),

    /**
     * An internal server failure.
     */
    SERVER_FAILURE(2),

    /**
     * DNS questions did not exist (NXDOMAIN).
     */
    NAME_ERROR(3),

    /**
     * The DNS server does not support the question type.
     */
    NOT_IMPLEMENTED(4),

    /**
     * The DNS server refused to fulfill the request.
     */
    REFUSED(5)
}

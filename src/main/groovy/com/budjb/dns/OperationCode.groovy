package com.budjb.dns

/**
 * An enumeration of DNS request operation codes.
 */
enum OperationCode {
    /**
     * Standard query.
     */
    QUERY(0),

    /**
     * Inversion query. Unsupported.
     */
    IQUERY(1),

    /**
     * DNS status query.
     */
    STATUS(2)

    /**
     * Integer code used in requests and responses.
     */
    int code

    /**
     * Constructor.
     *
     * @param code
     */
    OperationCode(int code) {
        this.code = code
    }
}

package com.budjb.dns.server

/**
 * An object representing a DNS response.
 */
interface DnsResponseWriter {
    /**
     * Write the response to an output stream.
     *
     * @param outputStream
     */
    void writeTo(OutputStream outputStream)
}

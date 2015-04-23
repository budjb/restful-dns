package com.budjb.dns.server

interface DnsServer {
    /**
     * Return the port to bind the server on.
     *
     * @return
     */
    int getPort()

    /**
     * Set the port to bind the server on.
     */
    void setPort(int port)

    /**
     * Returns the maximum number of concurrent threads.
     * 0 means unlimited.
     *
     * @return
     */
    int getConcurrentThreads()

    /**
     * Set the maximum number of concurrent threads.
     * 0 means unlimited.
     *
     * @param concurrentThreads
     */
    void setConcurrentThreads(int concurrentThreads)

    /**
     * Start the DNS server.
     */
    void start()

    /**
     * Stop the DNS server.
     */
    void stop()
}

package com.budjb.dns.server

import java.util.concurrent.ExecutorService

/**
 * Interface to create the set of objects responsible for server traffic and processing.
 */
interface DnsServerComponentFactory {
    /**
     * Create a DNS request object.
     *
     * @param inputStream
     * @return
     */
    DnsRequestParser createRequestParser(InputStream inputStream)

    /**
     * Create a DNS response object.
     *
     * @param request
     * @return
     */
    DnsResponseWriter createResponseWriter(DnsRequestParser request)

    /**
     * Create a UDP server runnable object.
     *
     * @param port
     * @param threadPool
     * @return
     */
    UdpServerRunnable createUdpServerRunnable(int port, ExecutorService threadPool)

    /**
     * Create a UDP packet request worker.
     *
     * @param socket
     * @param packet
     * @return
     */
    UdpPacketWorker createUdpPacketWorker(DatagramSocket socket, DatagramPacket packet)
}
package com.budjb.dns.server.impl

import com.budjb.dns.server.*
import com.budjb.dns.util.ByteUtil
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired

import java.util.concurrent.ExecutorService

@CompileStatic
class DnsServerComponentFactoryImpl implements DnsServerComponentFactory {
    /**
     * Byte conversion utility.
     */
    @Autowired
    ByteUtil byteUtil

    /**
     * Create a DNS request object.
     *
     * @param inputStream
     * @return
     */
    @Override
    DnsRequestParser createRequestParser(InputStream inputStream) {
        return new DnsRequestParserImpl(inputStream, byteUtil)
    }

    /**
     * Create a DNS response object.
     *
     * @param request
     * @return
     */
    @Override
    DnsResponseWriter createResponseWriter(DnsRequestParser request) {
        return new DnsResponseWriterImpl(request, byteUtil)
    }

    /**
     * Create a UDP server runnable object.
     *
     * @param port
     * @param threadPool
     * @return
     */
    @Override
    UdpServerRunnable createUdpServerRunnable(int port, ExecutorService threadPool) {
        return new UdpServerRunnableImpl(port, threadPool, this)
    }

    /**
     * Create a UDP packet request worker.
     *
     * @param socket
     * @param packet
     * @return
     */
    @Override
    UdpPacketWorker createUdpPacketWorker(DatagramSocket socket, DatagramPacket packet) {
        return new UdpPacketWorkerImpl(socket, packet, this)
    }
}

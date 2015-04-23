package com.budjb.dns.server.impl

import com.budjb.dns.server.DnsRequestParser
import com.budjb.dns.server.DnsResponseWriter
import com.budjb.dns.server.DnsServerComponentFactory
import com.budjb.dns.server.UdpPacketWorker

class UdpPacketWorkerImpl implements UdpPacketWorker {
    /**
     * UDP packet.
     */
    DatagramPacket packet

    /**
     * UDP socket.
     */
    DatagramSocket socket

    /**
     * DNS server component factory.
     */
    DnsServerComponentFactory dnsServerComponentFactory

    /**
     * Constructor.
     *
     * @param packet
     */
    UdpPacketWorkerImpl(DatagramSocket socket, DatagramPacket packet, DnsServerComponentFactory dnsServerComponentFactory) {
        this.socket = socket
        this.packet = packet
        this.dnsServerComponentFactory = dnsServerComponentFactory
    }

    /**
     * Run the thread.
     */
    @Override
    void run() {
        OutputStream outputStream = new ByteArrayOutputStream()
        InputStream inputStream = new ByteArrayInputStream(packet.getData(), 0, 512)

        DnsRequestParser request = dnsServerComponentFactory.createRequestParser(inputStream)

        // TODO: a real lookup will need to be injected here
        DnsResponseWriter response = dnsServerComponentFactory.createResponseWriter(request)

        response.writeTo(outputStream)

        socket.send(new DatagramPacket(
            outputStream.toByteArray(),
            outputStream.size(),
            packet.getAddress(),
            packet.getPort()
        ))
    }
}

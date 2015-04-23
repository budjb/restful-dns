package com.budjb.dns.server.impl

import com.budjb.dns.server.DnsServer
import com.budjb.dns.server.DnsServerComponentFactory
import com.budjb.dns.server.UdpServerRunnable
import org.springframework.beans.factory.annotation.Autowired

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DnsServerImpl implements DnsServer {
    /**
     * Number of concurrent requests.
     */
    int concurrentThreads = 0

    /**
     * UDP server port.
     */
    int port = 53

    /**
     * Thread pool.
     */
    protected ExecutorService threadPool

    /**
     * DNS server component factory.
     */
    @Autowired
    DnsServerComponentFactory dnsServerComponentFactory

    /**
     * UDP server runnable.
     */
    protected UdpServerRunnable udpServerRunnable

    /**
     * Start the DNS server.
     */
    void start() {
        if (concurrentThreads > 0) {
            threadPool = Executors.newFixedThreadPool(concurrentThreads)
        }
        else {
            threadPool = Executors.newCachedThreadPool()
        }

        udpServerRunnable = dnsServerComponentFactory.createUdpServerRunnable(port, threadPool)

        new Thread(udpServerRunnable).start()
    }

    /**
     * Stops the DNS server.
     */
    void stop() {
        udpServerRunnable.stop()
    }
}

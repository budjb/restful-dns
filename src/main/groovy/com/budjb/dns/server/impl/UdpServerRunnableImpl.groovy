package com.budjb.dns.server.impl

import com.budjb.dns.server.DnsServerComponentFactory
import com.budjb.dns.server.UdpServerRunnable
import com.budjb.dns.server.impl.UdpPacketWorkerImpl

import java.util.concurrent.ExecutorService

class UdpServerRunnableImpl implements UdpServerRunnable {
    /**
     * Whether the server is running.
     */
    private boolean running = false

    /**
     * UDP server socket.
     */
    private DatagramSocket datagramSocket

    /**
     * UDP server port.
     */
    private int port

    /**
     * Thread pool.
     */
    private ExecutorService threadPool

    /**
     * DNS server component factory.
     */
    private DnsServerComponentFactory dnsServerComponentFactory

    /**
     * Constructor.
     *
     * @param threadPool
     */
    UdpServerRunnableImpl(int port, ExecutorService threadPool, DnsServerComponentFactory dnsServerComponentFactory) {
        this.port = port
        this.threadPool = threadPool
        this.dnsServerComponentFactory = dnsServerComponentFactory
    }

    /**
     * Run the server thread.
     */
    @Override
    void run() {
        setRunning(true)

        datagramSocket = new DatagramSocket(port)

        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[512], 512)

                datagramSocket.receive(packet)

                threadPool.execute(dnsServerComponentFactory.createUdpPacketWorker(datagramSocket, packet))
            }
        }
        catch (IOException e) {
            if (getRunning()) {
                throw new RuntimeException(e)
            }
        }
        finally {
            stop()
        }
    }

    /**
     * Returns whether the TCP server is running.
     *
     * @return
     */
    synchronized boolean getRunning() {
        return running
    }

    /**
     * Sets whether the TCP server is running.
     *
     * @param running
     * @return
     */
    protected synchronized setRunning(boolean running) {
        this.running = running
    }

    /**
     * Stops the TCP server.
     */
    void stop() {
        setRunning(false)

        if (!datagramSocket.isClosed()) {
            datagramSocket.close()
        }
    }
}

package com.budjb.dns.server

interface UdpServerRunnable extends Runnable {
    /**
     * Returns whether the UDP server is running.
     *
     * @return
     */
    boolean getRunning()

    /**
     * Stops the UDP server.
     */
    void stop()
}

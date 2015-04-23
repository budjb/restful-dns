package com.budjb.dns.server.impl

import com.budjb.dns.server.impl.TcpConnectionWorker

import java.util.concurrent.ExecutorService

class TcpServerRunnable implements Runnable {
    /**
     * Whether the server is running.
     */
    private boolean running = false

    /**
     * TCP server socket.
     */
    private ServerSocket serverSocket

    /**
     * TCP server port.
     */
    private int port

    /**
     * Thread pool.
     */
    private ExecutorService threadPool

    /**
     * Constructor.
     *
     * @param threadPool
     */
    TcpServerRunnable(int port, ExecutorService threadPool) {
        this.port = port
        this.threadPool = threadPool
    }

    /**
     * Run the server thread.
     */
    @Override
    void run() {
        setRunning(true)

        serverSocket = new ServerSocket(port)

        try {
            while (true) {
                Socket socket = serverSocket.accept()

                TcpConnectionWorker worker = new TcpConnectionWorker(socket)

                threadPool.execute(worker)
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

        if (!serverSocket.isClosed()) {
            serverSocket.close()
        }
    }
}

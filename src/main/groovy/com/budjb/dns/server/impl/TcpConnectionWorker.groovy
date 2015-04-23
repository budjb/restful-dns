package com.budjb.dns.server.impl

import com.budjb.dns.util.ByteUtil

class TcpConnectionWorker implements Runnable {
    /**
     * TCP connection socket.
     */
    Socket socket

    /**
     * Constructor.
     *
     * @param socket
     */
    TcpConnectionWorker(Socket socket) {
        this.socket = socket
    }

    /**
     * Run the thread.
     */
    @Override
    void run() {
        OutputStream outputStream = socket.getOutputStream()
        InputStream inputStream = socket.getInputStream()

        DnsRequestParserImpl request = new DnsRequestParserImpl(inputStream)

        DnsResponseWriterImpl response = new DnsResponseWriterImpl(request, new ByteUtil())

        response.writeTo(outputStream)

        inputStream.close()
        outputStream.close()
        socket.close()
    }
}

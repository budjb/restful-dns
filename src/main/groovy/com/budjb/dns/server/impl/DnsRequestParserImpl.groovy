package com.budjb.dns.server.impl

import com.budjb.dns.Classification
import com.budjb.dns.Question
import com.budjb.dns.RecordType
import com.budjb.dns.server.DnsRequestParser
import com.budjb.dns.util.ByteUtil

/**
 * An implementation of a DNS request object.
 */
class DnsRequestParserImpl implements DnsRequestParser {
    /**
     * Request message ID.
     */
    int messageId

    /**
     * Whether the request is truncated.
     */
    boolean isTruncated

    /**
     * Whether recursion is desired.
     */
    boolean isRecursionDesired

    /**
     * List of questions in the request.
     */
    Question question

    /**
     * Byte conversion utility.
     */
    ByteUtil byteUtil

    /**
     * Input stream.
     */
    protected final InputStream inputStream

    /**
     * Constructor.
     *
     * @param inputStream
     */
    DnsRequestParserImpl(InputStream inputStream, ByteUtil byteUtil) throws IOException {
        this.inputStream = inputStream
        this.byteUtil = byteUtil

        parseRequest()
    }

    /**
     * Parses the request input stream.
     */
    protected void parseRequest() throws IOException {
        parseHeader()
        parseQuestion()
    }

    /**
     * Parses the request header.
     */
    protected void parseHeader() throws IOException {
        // Message ID
        messageId = byteUtil.toInt(read(2))

        // First flags section
        byte flag = read(1)[0]
        isTruncated = flag & 0x4 ? true : false
        isRecursionDesired = flag & 0x2 ? true : false

        // Skip the second flags section
        inputStream.skip(1)

        // Question count
        if (byteUtil.toInt(read(2)) > 1) {
            throw new IllegalArgumentException('multiple questions are not supported') // TODO: make a better exception
        }

        // Skip the rest of the counters
        inputStream.skip(6)
    }

    /**
     * Parses an individual question.
     */
    protected void parseQuestion() throws IOException {
        String name = readName()
        int type = byteUtil.toInt(read(2))
        int classification = byteUtil.toInt(read(2))

        question = new Question([
            name: name,
            type: RecordType.findByCode(type),
            classification: Classification.findByCode(classification)
        ])
    }

    /**
     * Read a given number of bytes from the input stream.
     * This method also provides error handling.
     *
     * @param count
     * @return
     */
    protected byte[] read(int count) throws IOException {
        byte[] bytes = new byte[count]

        for (int i = 0; i < count; i++) {
            byte value = inputStream.read()

            if (value == -1 as byte) {
                throw new IOException("unexpected end of stream; expected ${count} bytes but received ${i}")
            }

            bytes[i] = value
        }

        return bytes
    }

    /**
     * Read a question name from the input stream.
     *
     * @return
     * @throws IOException
     */
    protected String readName() throws IOException {
        int length

        List<String> sections = []

        while ((length = byteUtil.toInt(read(1))) != 0) {
            sections << new String(read(length))
        }

        return sections.join('.')
    }
}

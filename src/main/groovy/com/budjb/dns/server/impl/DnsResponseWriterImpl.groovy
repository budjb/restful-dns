package com.budjb.dns.server.impl

import com.budjb.dns.Question
import com.budjb.dns.server.DnsRequestParser
import com.budjb.dns.server.DnsResponseWriter
import com.budjb.dns.util.ByteUtil

/**
 * Implementation of a DNS response object.
 */
class DnsResponseWriterImpl implements DnsResponseWriter {
    /**
     * Byte conversion utility.
     */
    ByteUtil byteUtil

    /**
     * DNS request object.
     */
    DnsRequestParser request

    /**
     * Question pointers.
     */
    Map<Question, Integer> pointers = [:]

    /**
     * Constructor.
     */
    DnsResponseWriterImpl(DnsRequestParser request, ByteUtil byteUtil) {
        this.request = request
        this.byteUtil = byteUtil
    }

    /**
     * Writes the response to the socket's output stream.
     *
     * @param outputStream
     */
    void writeTo(OutputStream outputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()

        writeHeader(byteArrayOutputStream)
        writeQuestions(byteArrayOutputStream)
        writeAnswers(byteArrayOutputStream)

        byteArrayOutputStream.writeTo(outputStream)
    }

    /**
     * Writes the questions section of the reply.
     *
     * @param outputStream
     */
    protected void writeQuestions(ByteArrayOutputStream outputStream) {
        request.questions.each { writeQuestion(it, outputStream) }
    }

    /**
     * Writes a question to the output stream.
     *
     * @param question
     * @param outputStream
     */
    protected void writeQuestion(Question question, ByteArrayOutputStream outputStream) {
        // Store a name pointer for use in the answer section
        pointers.put(question, outputStream.size())

        // Write each name segment, terminated with a null character (0)
        question.name.split('\\.').each { String section ->
            outputStream.write(byteUtil.toBytes(section.size() as int, 1))
            outputStream.write(section.getBytes())
        }
        outputStream.write(byteUtil.toBytes(0, 1))

        // Write the record type
        outputStream.write(byteUtil.toBytes(question.type.code, 2))

        // Write the classification
        outputStream.write(byteUtil.toBytes(question.classification.code, 2))

    }
    /**
     * Writes the answers section of the reply.
     *
     * @param outputStream
     */
    protected void writeAnswers(ByteArrayOutputStream outputStream) {
        request.questions.each { Question question ->
            // If the question has a byte offset pointer, use it; otherwise output the whole name
            if (pointers.containsKey(question)) {
                int pointer = 0
                pointer |= 3 << 14
                pointer |= pointers.get(question)
                outputStream.write(byteUtil.toBytes(pointer, 2))
            }
            else {
                question.name.split('\\.').each { String section ->
                    outputStream.write(byteUtil.toBytes(section.size() as int, 1))
                    outputStream.write(section.getBytes())
                }
                outputStream.write(byteUtil.toBytes(0, 1))
            }

            // Write the record type
            outputStream.write(byteUtil.toBytes(1, 2)) // TODO: use actual classification according to the result

            // Write the classification
            outputStream.write(byteUtil.toBytes(1, 2)) // TODO: use the actual classification

            // Write the TTL
            outputStream.write(byteUtil.toBytes(21, 4)) // TODO: use the actual TTL

            // Write the data section length
            outputStream.write(byteUtil.toBytes(4, 2)) // TODO: use the correct length for the actual answer

            // Write the data section
            outputStream.write([192, 168, 1, 3] as byte[]) // TODO: use actual result value
        }
    }

    /**
     * Writes the header section of the reply.
     *
     * @param outputStream
     */
    protected void writeHeader(ByteArrayOutputStream outputStream) {
        // Write the message ID
        outputStream.write(byteUtil.toBytes(request.messageId, 2))

        // Write the header flags
        outputStream.write(getHeaderFlags())

        // Write the question count
        outputStream.write(byteUtil.toBytes(request.questions.size(), 2))

        // Write the answer count
        outputStream.write(byteUtil.toBytes(request.questions.size(), 2)) // TODO: use the actual answer count

        // Write the name server count
        outputStream.write(byteUtil.toBytes(0, 2)) // TODO: output NS records

        // Write the additional section count
        outputStream.write(byteUtil.toBytes(0, 2)) // TODO: output additional records
    }

    /**
     * Build the header flags for a DNS response.
     *
     * @return
     */
    protected byte[] getHeaderFlags() {
        byte[] flags = new byte[2]

        byte section = 0

        // Set the answer bit
        section |= 128

        // Set the authority bit
        section |= 4 // TODO: use actual authority bit

        flags[0] = section
        flags[1] = 0 as byte // TODO: set real bits

        return flags
    }
}

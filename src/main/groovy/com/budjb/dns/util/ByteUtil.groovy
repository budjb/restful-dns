package com.budjb.dns.util

/**
 * A utility to handle various operations involving bytes conversion.
 */
class ByteUtil {
    /**
     * Render a byte as a string of bits.
     *
     * @param input
     * @return
     */
    String toBitString(byte input) {
        return (0..(Byte.SIZE - 1)).collect { i ->
            return (input >> (Byte.SIZE - 1 - i) & 0x1) as String
        }.join('')
    }

    /**
     * Render a byte array as a string of bits.
     *
     * @param input
     * @return
     */
    String toBitString(byte[] input) {
        StringBuilder stringBuilder = new StringBuilder()

        int size = input.size()
        for (int i = 0; i < size; i++) {
            stringBuilder.append(toBitString(input[i]))

            if (i < size - 1) {
                if ((i + 1) % 4 == 0) {
                    stringBuilder.append("\n")
                }
                else {
                    stringBuilder.append(" ")
                }
            }
        }

        return stringBuilder.toString()
    }

    /**
     * Convert an integer to a byte array of the given size.
     *
     * @param value
     * @param size
     * @return
     */
    byte[] toBytes(int value, int size = Integer.SIZE) {
        if (size < 1) {
            size = 1
        }
        else if (size > Integer.SIZE) {
            size = Integer.SIZE
        }

        byte[] bytes = new byte[size]

        for (int i = 0; i < size; i++) {
            int offset = (size - i - 1) * Byte.SIZE

            bytes[i] = value >>> offset & 0xff
        }

        return bytes
    }

    /**
     * Convert a short to a byte array of the given size.
     *
     * @param value
     * @param size
     * @return
     */
    byte[] toBytes(short value, int size = Short.SIZE) {
        if (size < 1) {
            size = 1
        }
        else if (size > Integer.SIZE) {
            size = Integer.SIZE
        }

        byte[] bytes = new byte[size]

        for (int i = 0; i < size; i++) {
            int offset = (size - i - 1) * Byte.SIZE

            bytes[i] = value >>> offset & 0xff
        }

        return bytes
    }

    /**
     * Converts a given byte array to an integer.
     *
     * @param values
     * @return
     */
    int toInt(byte[] values) {
        if (values.size() > Integer.SIZE) {
            throw new IllegalArgumentException()
        }

        int value = 0

        values.each {
            value = value << Byte.SIZE | it & 0xff
        }

        return value
    }

    /**
     * Converts a given byte array to a short.
     *
     * @param values
     * @return
     */
    short toShort(byte[] values) {
        if (values.size() > Short.SIZE) {
            throw new IllegalArgumentException()
        }

        short value = 0

        values.each {
            value = value << Byte.SIZE | it & 0xff
        }

        return value
    }
}

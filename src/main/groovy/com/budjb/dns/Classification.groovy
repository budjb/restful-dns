package com.budjb.dns

/**
 * Record classification type.
 */
enum Classification {
    /**
     * Internet classification (most common).
     */
    INTERNET('IN', 1),

    /**
     * Chaos.
     */
    CHAOS('CH', 3),

    /**
     * Hesiod.
     */
    HESIOD('HS', 4),

    /**
     * None.
     */
    NONE('', 254),

    /**
     * Any.
     */
    ANY('*', 255)

    /**
     * Common name of the classification type.
     */
    String name

    /**
     * Integer code for the classification type.
     */
    int code

    /**
     * Constructor.
     *
     * @param name
     */
    Classification(String name, int code) {
        this.name = name
        this.code = code
    }

    /**
     * Find a classification by its name.
     *
     * @param name
     * @return
     */
    static Classification findByName(String name) {
        return values().find { it.name == name }
    }

    /**
     * Find a classification by its code.
     *
     * @param code
     * @return
     */
    static Classification findByCode(int code) {
        return values().find { it.code == code }
    }
}

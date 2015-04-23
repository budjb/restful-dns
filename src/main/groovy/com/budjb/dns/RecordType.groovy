package com.budjb.dns

/**
 * An enumeration of the various supported record types.
 */
enum RecordType {
    /**
     * Address record type.
     */
    A('A', 1),

    /**
     * Name server record type.
     */
    NS('NS', 2),

    /**
     * CNAME record type.
     */
    CNAME('CNAME', 5),

    /**
     * SOA record type.
     */
    SOA('SOA', 6),

    /**
     * Well known service record type.
     */
    WKS('WKS', 11),

    /**
     * Domain name pointer record type.
     */
    PTR('PTR', 12),

    /**
     * Mail exchange record type.
     */
    MX('MX', 15),

    /**
     * IPv6 address record type.
     */
    AAAA('AAAA', 28),

    /**
     * Server selection record type.
     */
    SRV('SRV', 33),

    /**
     * Any record.
     */
    ANY('ANY', 255)

    /**
     * Name of the record type.
     */
    String name

    /**
     * Code of the record type.
     */
    int code

    /**
     * Constructor.
     *
     * @param name
     * @param code
     */
    RecordType(String name, int code) {
        this.name = name
        this.code = code
    }

    /**
     * Find a record type by its name.
     *
     * @param name
     * @return
     */
    static RecordType findByName(String name) {
        name = name.toUpperCase()

        return values().find { it.name == name }
    }

    /**
     * Find a record type by its code.
     *
     * @param code
     * @return
     */
    static RecordType findByCode(int code) {
        return values().find { it.code == code }
    }
}

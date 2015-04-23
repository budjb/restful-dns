package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

/**
 * IPv6 address record.
 */
class IPv6AddressRecord extends AbstractRecord {
    /**
     * IPv6 address.
     */
    String ip

    /**
     * Constructor.
     */
    IPv6AddressRecord() { }

    /**
     * Constructor.
     *
     * @param data
     */
    IPv6AddressRecord(Map<String, Object> data) {
        fromMap(data)
    }

    /**
     * Returns the record type.
     *
     * @return
     */
    @Override
    RecordType getRecordType() {
        return RecordType.AAAA
    }

    /**
     * Return the record as a Map.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return super.toMap() + [ip: ip]
    }

    /**
     * Load the record from a dataset map.
     *
     * @param map
     */
    @Override
    protected void fromMap(Map<String, Object> map) {
        super.fromMap(map)

        ip = map['ip']
    }

    /**
     * Determines if another object is the same IPv6 address record.
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other) {
        return super.equals(other) && ip == ((IPv6AddressRecord) other).ip
    }

    /**
     * Returns whether the record's properties are valid.
     *
     * @return
     */
    @Override
    boolean isValid() {
        return false
    }
}

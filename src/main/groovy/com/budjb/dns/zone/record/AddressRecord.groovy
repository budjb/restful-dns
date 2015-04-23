package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

/**
 * IPv4 address record.
 */
class AddressRecord extends AbstractRecord {
    /**
     * IPv4 address.
     */
    String ip

    /**
     * Constructor.
     */
    AddressRecord() { }

    /**
     * Constructor.
     *
     * @param data
     */
    AddressRecord(Map<String, Object> data) {
        fromMap(data)
    }

    /**
     * Returns the record type.
     *
     * @return
     */
    @Override
    RecordType getRecordType() {
        return RecordType.A
    }

    /**
     * Outputs the record as a map suitable for serialization as JSON.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return super.toMap() + [ip: ip]
    }

    /**
     * Loads the record from a dataset map.
     *
     * @param map
     */
    @Override
    protected void fromMap(Map<String, Object> map) {
        super.fromMap(map)

        ip = map['ip']
    }

    /**
     * Determines if another object is the same address record.
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other) {
        return super.equals(other) && ip == ((AddressRecord) other).ip
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

package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

/**
 * Name server record.
 */
class NsRecord extends AbstractRecord {
    /**
     * Host of the nameserver.
     */
    String ns

    /**
     * Constructor.
     */
    NsRecord() { }

    /**
     * Constructor.
     *
     * @param data
     */
    NsRecord(Map<String, Object> data) {
        fromMap(data)
    }

    /**
     * Returns the record type.
     *
     * @return
     */
    @Override
    RecordType getRecordType() {
        return RecordType.NS
    }

    /**
     * Return the record as a Map.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return super.toMap() + [ns: ns]
    }

    /**
     * Load the record from a dataset map.
     *
     * @param map
     */
    @Override
    protected void fromMap(Map<String, Object> map) {
        super.fromMap(map)

        ns = map['ns']
    }

    /**
     * Determines if another object is the same nameserver record.
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other) {
        return super.equals(other) && ns == ((NsRecord) other).ns
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

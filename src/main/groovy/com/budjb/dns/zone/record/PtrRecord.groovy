package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

class PtrRecord extends AbstractRecord {
    /**
     * Host the IP belongs to.
     */
    String host

    /**
     * Constructor.
     */
    PtrRecord() { }

    /**
     * Constructor.
     *
     * @param data
     */
    PtrRecord(Map<String, Object> data) {
        fromMap(data)
    }

    /**
     * Returns the record type.
     *
     * @return
     */
    @Override
    RecordType getRecordType() {
        return RecordType.PTR
    }

    /**
     * Return the record as a Map.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return super.toMap() + [host: host]
    }

    /**
     * Load the record from a dataset map.
     *
     * @param map
     */
    @Override
    protected void fromMap(Map<String, Object> map) {
        super.fromMap(map)

        host = map['host']
    }

    /**
     * Determines if another object is the same PTR record.
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other) {
        return super.equals(other) && host == ((PtrRecord)other).host
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

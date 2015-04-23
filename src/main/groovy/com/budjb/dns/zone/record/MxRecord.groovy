package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

/**
 * Mail exchange record.
 */
class MxRecord extends AbstractRecord {
    /**
     * Order preference.
     */
    int preference

    /**
     * Mail exchange server.
     */
    String exchange

    /**
     * Constructor.
     */
    MxRecord() { }

    /**
     * Constructor.
     *
     * @param data
     */
    MxRecord(Map<String, Object> data) {
        fromMap(data)
    }

    /**
     * Returns the record type.
     *
     * @return
     */
    @Override
    RecordType getRecordType() {
        return RecordType.MX
    }

    /**
     * Return the record as a Map.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return super.toMap() + [
            preference: preference,
            exchange  : exchange
        ]
    }

    /**
     * Loads the record from a dataset map.
     *
     * @param map
     */
    @Override
    protected void fromMap(Map<String, Object> map) {
        super.fromMap(map)

        preference = map['preference']
        exchange = map['exchange']
    }

    /**
     * Determines if another object is the same mail exchange record.
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other) {
        return super.equals(other) && preference == ((MxRecord) other).preference && exchange == ((MxRecord) other).exchange
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

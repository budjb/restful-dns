package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

/**
 * CNAME record.
 */
class CnameRecord extends AbstractRecord {
    /**
     * Alias name.
     */
    String alias

    /**
     * Constructor.
     */
    CnameRecord() { }

    /**
     * Constructor.
     *
     * @param data
     */
    CnameRecord(Map<String, Object> data) {
        fromMap(data)
    }

    /**
     * Returns the record type.
     *
     * @return
     */
    @Override
    RecordType getRecordType() {
        return RecordType.CNAME
    }

    /**
     * Output the record as a map.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return super.toMap() + [alias: alias]
    }

    /**
     * Load the record from a dataset map.
     *
     * @param map
     */
    @Override
    protected void fromMap(Map<String, Object> map) {
        super.fromMap(map)

        alias = map['alias']
    }

    /**
     * Determines if another object is the same CNAME record.
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other) {
        return super.equals(other) && alias == ((CnameRecord) other).alias
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

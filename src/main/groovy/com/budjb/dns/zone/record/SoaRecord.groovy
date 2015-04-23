package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

/**
 * SOA record.
 */
class SoaRecord extends AbstractRecord {
    /**
     * Serial version of the zone.
     */
    long serial

    /**
     * Interval when a slave should refresh the zone.
     */
    long refresh

    /**
     * Interval when a slave should attempt to refresh a zone after a previous failure to do so.
     */
    long retry

    /**
     * How long the zone is valid before a slave stops responding authoritatively for it.
     */
    long expire

    /**
     * How long clients should cache negative record lookups.
     */
    long negativeTtl

    /**
     * Authoritative name server (master).
     */
    String nameserver

    /**
     * The email address of the DNS zone's administrator.
     */
    String email

    /**
     * Constructor.
     */
    SoaRecord() { }

    /**
     * Constructor.
     *
     * @param data
     */
    SoaRecord(Map<String, Object> data) {
        fromMap(data)
    }

    /**
     * Returns the record type.
     *
     * @return
     */
    @Override
    RecordType getRecordType() {
        return RecordType.SOA
    }

    /**
     * Returns the record as a Map.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return super.toMap() + [
            serial     : serial,
            refresh    : refresh,
            retry      : retry,
            expire     : expire,
            negativeTtl: negativeTtl,
            nameserver : nameserver,
            email      : email
        ]
    }

    /**
     * Loads the data for this record from a Map dataset.
     *
     * @param map
     */
    @Override
    protected void fromMap(Map<String, Object> map) {
        super.fromMap(map)

        serial = map['serial']
        refresh = map['refresh']
        retry = map['retry']
        expire = map['expire']
        negativeTtl = map['negativeTtl']
        nameserver = map['nameserver']
        email = map['email']
    }

    /**
     * Determines if another object is the same SOA record.
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other) {
        if (!super.equals(other)) {
            return false
        }

        other = (SoaRecord) other

        return serial == other.serial && refresh == other.refresh && retry == other.retry && expire == other.expire &&
            negativeTtl == other.negativeTtl && nameserver == other.nameserver && email == other.email
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

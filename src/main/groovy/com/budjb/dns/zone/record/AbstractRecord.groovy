package com.budjb.dns.zone.record

import com.budjb.dns.Classification

abstract class AbstractRecord implements Record {
    /**
     * Name of the record.
     */
    String name

    /**
     * Network classification of the record.
     */
    Classification classification

    /**
     * TTL of the record.
     */
    Integer ttl

    /**
     * Determines if another object is equal to this record.
     *
     * @param other
     * @return
     */
    boolean equals(Object other) {
        if (other == null) {
            return false
        }

        if (other.getClass() != this.getClass()) {
            return false
        }

        other = (Record) other

        return recordType == other.recordType && classification == other.classification && name == other.name &&
            ttl == other.ttl
    }

    /**
     * Outputs the record as a map suitable for serialization as JSON.
     *
     * @return
     */
    @Override
    Map<String, Object> toMap() {
        return [
            name          : name,
            type          : recordType.name,
            classification: classification.name,
            ttl           : ttl
        ]
    }

    /**
     * Loads the data for this record from a Map dataset.
     *
     * @param map
     */
    protected void fromMap(Map<String, Object> map) {
        name = map['name']
        classification = Classification.findByName(map['classification'])
        ttl = map['ttl']
    }
}

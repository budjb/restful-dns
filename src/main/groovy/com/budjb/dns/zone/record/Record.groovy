package com.budjb.dns.zone.record

import com.budjb.dns.Classification
import com.budjb.dns.RecordType

/**
 * A DNS record type.
 */
interface Record {
    /**
     * Returns the record type.
     *
     * @return
     */
    RecordType getRecordType()

    /**
     * Return the record name.
     *
     * @return
     */
    String getName()

    /**
     * Set the record name.
     *
     * @param name
     */
    void setName(String name)

    /**
     * Return the default TTL of the zone.
     *
     * @return
     */
    Integer getTtl()

    /**
     * Set the default TTL of the zone.
     *
     * @param ttl
     */
    void setTtl(Integer ttl)

    /**
     * Return the record classification.
     *
     * @return
     */
    Classification getClassification()

    /**
     * Set the record classification.
     *
     * @param classification
     */
    void setClassification(Classification classification)

    /**
     * Outputs the record as a map suitable for serialization as JSON.
     *
     * @return
     */
    Map<String, Object> toMap()

    /**
     * Returns whether the record's properties are valid.
     *
     * @return
     */
    boolean isValid()
}

package com.budjb.dns.zone

import com.budjb.dns.zone.record.Record
import com.budjb.dns.zone.record.RecordFactory
import com.budjb.dns.zone.record.SoaRecord

/**
 * Object representing a DNS zone.
 */
class Zone {
    /**
     * Alias meaning the root name of the zone.
     */
    static final String ROOT_ALIAS = '@'

    /**
     * Root name of the zone.
     */
    String name

    /**
     * SOA record.
     */
    private SoaRecord soaRecord

    /**
     * Default TTL of the zone.
     */
    int ttl

    /**
     * List of records for the zone.
     */
    private Set<Record> records

    /**
     * Constructor.
     */
    Zone() {
        records = new LinkedHashSet<Record>()
    }

    /**
     * Constructor.
     *
     * @param data
     */
    Zone(Map<String, Object> data) {
        this()
        fromMap(data)
    }

    /**
     * Sets the SOA record.
     *
     * @param soaRecord
     */
    void setSoaRecord(SoaRecord soaRecord) {
        soaRecord.name = '@'

        this.soaRecord = soaRecord
    }

    /**
     * Returns the SOA record.
     *
     * @return
     */
    SoaRecord getSoaRecord() {
        return soaRecord
    }

    /**
     * Setting DNS records is not supported.
     *
     * @param records
     */
    final void setRecords(Set<Record> records) {
        throw new UnsupportedOperationException()
    }

    /**
     * Returns the list of records in the zone.
     *
     * @return
     */
    Set<Record> getRecords() {
        return records
    }

    /**
     * Add a record to the zone.
     *
     * @param record
     */
    void addRecord(Record record) {
        record.name = sanitizeRecordName(record.name)

        records.add(record)
    }

    /**
     * Removes a record from the zone.
     *
     * @param record
     */
    void removeRecord(Record record) {
        records.remove(record)
    }

    /**
     * Outputs the zone as a map suitable for serialization as JSON.
     *
     * @return
     */
    Map<String, Object> toMap() {
        return [
            name: name,
            ttl: ttl,
            soaRecord: soaRecord.toMap(),
            records: records.collect { it.toMap() }
        ]
    }

    /**
     * Loads the zone from a Map dataset.
     *
     * @param map
     */
    protected void fromMap(Map<String, Object> map) {
        RecordFactory recordFactory = new RecordFactory()

        name = map['name'] as String
        ttl = map['ttl'] as int

        soaRecord = new SoaRecord(map['soaRecord'] as Map<String, Object>)

        map['records'].each {
            addRecord(recordFactory.createRecord(it as Map<String, Object>))
        }
    }

    /**
     * Strips an FQDN of its parent zone name, and replaces empty names with "@".
     *
     * @param name
     * @return
     */
    protected String sanitizeRecordName(String name) {
        if (name.endsWith(this.name)) {
            name = name.replace("${this.name}\$", '')
        }

        if (name == '') {
            name = ROOT_ALIAS
        }

        return name
    }
}

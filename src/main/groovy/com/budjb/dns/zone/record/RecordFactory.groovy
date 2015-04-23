package com.budjb.dns.zone.record

import com.budjb.dns.RecordType

class RecordFactory {
    /**
     * Creates a new record object for the given record type.
     *
     * @param data
     * @return
     */
    Record createRecord(Map<String, Object> data) {
        return getRecordForType(RecordType.findByName(data['type'])).newInstance(data)
    }

    /**
     * Creates a new record object for the given record type.
     *
     * @param recordType
     * @return
     */
    Record createRecord(RecordType recordType) {
        return getRecordForType(RecordType.findByName(data['type'])).newInstance()
    }

    /**
     * Attempts to find the correct record class for the given record type.
     *
     * @param recordType
     * @return
     */
    Class<? extends Record> getRecordForType(RecordType recordType) {
        switch (recordType) {
            case RecordType.A:
                return AddressRecord

            case RecordType.AAAA:
                return IPv6AddressRecord

            case RecordType.CNAME:
                return CnameRecord

            case RecordType.MX:
                return MxRecord

            case RecordType.NS:
                return NsRecord

            case RecordType.PTR:
                return PtrRecord

            case RecordType.SOA:
                return SoaRecord

            default:
                throw new UnsupportedOperationException("record type ${recordType.name} is not supported")
        }
    }
}

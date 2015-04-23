package com.budjb.dns

/**
 * An object that represents an individual DNS question.
 */
class Question {
    /**
     * Question name.
     */
    String name

    /**
     * Question record type.
     */
    RecordType type

    /**
     * Network classification.
     */
    Classification classification
}

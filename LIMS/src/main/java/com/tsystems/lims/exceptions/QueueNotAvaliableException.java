package com.tsystems.lims.exceptions;

@SuppressWarnings("serial")
public class QueueNotAvaliableException extends RuntimeException {
    public QueueNotAvaliableException() {
    }

    public QueueNotAvaliableException(String message) {
        super(message);
    }
}

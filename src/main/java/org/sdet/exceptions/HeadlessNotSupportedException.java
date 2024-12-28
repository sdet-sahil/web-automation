package org.sdet.exceptions;

public class HeadlessNotSupportedException extends RuntimeException {
    public HeadlessNotSupportedException(String message) {
        super(message);
    }
}

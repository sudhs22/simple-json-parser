package org.sudhs.exception;

/**
 * Custom Exception for Parsing
 */
public class JsonParseException extends RuntimeException {
    public JsonParseException(String message, Throwable cause){
        super(message, cause);
    }
}

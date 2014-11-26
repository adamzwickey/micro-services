package io.pivotal.demo;

public class ResponseObject {

    private String _message,_origMessage;
    private long _processingTime = 0;
    private boolean _error = false;

    public ResponseObject(String message, String originalMessage) {
        _message = message;
        _origMessage = originalMessage;
    }

    public ResponseObject(String message, String originalMessage, boolean error) {
        _message = message;
        _origMessage = originalMessage;
        _error = error;
    }

    public String getOriginalMessage() {
        return _origMessage;
    }

    public String getMessage() {
        return _message;
    }

    public long getProcessingTime() {
        return _processingTime;
    }

    public void setProcessingTime(long time) {
        _processingTime = time;
    }

    public boolean isCircuitBreaker() {
        return _error;
    }

}

package com.usebilbo.exception;

public class CoreConversionException extends CoreException {
    private static final long serialVersionUID = 1883499937406224261L;

    public CoreConversionException() {
        super();
    }

    public CoreConversionException(String message) {
        super(message);
    }

    public CoreConversionException(String message, Throwable t) {
        super(message, t);
    }

    public CoreConversionException(Throwable t) {
        super(t);
    }
}

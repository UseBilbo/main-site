package com.usebilbo.exception;

public class CorePersistenceException extends CoreException {
    private static final long serialVersionUID = -2841624421353105074L;

    public CorePersistenceException() {
        super();
    }

    public CorePersistenceException(String message) {
        super(message);
    }

    public CorePersistenceException(String message, Throwable t) {
        super(message, t);
    }

    public CorePersistenceException(Throwable t) {
        super(t);
    }
}

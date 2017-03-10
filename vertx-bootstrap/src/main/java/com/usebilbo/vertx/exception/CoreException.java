package com.usebilbo.vertx.exception;

public class CoreException extends RuntimeException {
    private static final long serialVersionUID = -1899652570420541180L;

    public CoreException() {
        super();
    }

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable t) {
        super(message, t);
    }

    public CoreException(Throwable t) {
        super(t);
    }

    public static CoreException from(String message) {
        return new CoreException(message);
    }

    public static CoreException from(Exception e) {
        return new CoreException(e);
    }

    public static CoreException from(String message, Throwable t) {
        return new CoreException(message, t);
    }

    public static CoreException wrap(String message, Exception e) {
        return e instanceof CoreException ? (CoreException) e : CoreException.from(message, e);
    }
    public static CoreException wrap(Exception e) {
        return e instanceof CoreException ? (CoreException) e : CoreException.from(e);
    }
}

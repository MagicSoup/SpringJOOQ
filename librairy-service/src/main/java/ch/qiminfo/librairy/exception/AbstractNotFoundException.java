package ch.qiminfo.librairy.exception;

/**
 * The type Abstract not found exception.
 */
public abstract class AbstractNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Abstract not found exception.
     *
     * @param message the message
     */
    public AbstractNotFoundException(String message) {
        super(message);
    }
}

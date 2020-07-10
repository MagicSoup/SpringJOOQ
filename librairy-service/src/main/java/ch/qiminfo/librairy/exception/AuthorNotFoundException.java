package ch.qiminfo.librairy.exception;

/**
 * The type Author not found exception.
 */
public final class AuthorNotFoundException extends AbstractNotFoundException {

    private static final String MESSAGE = "Author not found with uuid %s";

    /**
     * Instantiates a new Author not found exception.
     *
     * @param uuid the uuid
     */
    public AuthorNotFoundException(String uuid) {
        super(String.format(MESSAGE, uuid));
    }
}

package ch.qiminfo.librairy.exception;

/**
 * The type Book not found exception.
 */
public final class BookNotFoundException extends AbstractNotFoundException {

    private static final String MESSAGE = "Book not found with uuid %s";

    /**
     * Instantiates a new Book not found exception.
     *
     * @param uuid the uuid
     */
    public BookNotFoundException(String uuid) {
        super(String.format(MESSAGE, uuid));
    }
}

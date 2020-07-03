package ch.qiminfo.librairy.exception;

public final class BookNotFoundException extends AbstractNotFoundException {

    private static final String MESSAGE = "Book not found with uuid %s";

    public BookNotFoundException(String uuid) {
        super(String.format(MESSAGE, uuid));
    }
}

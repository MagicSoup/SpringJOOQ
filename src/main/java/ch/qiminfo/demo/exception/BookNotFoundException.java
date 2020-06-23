package ch.qiminfo.demo.exception;

public final class BookNotFoundException extends AbstractNotFoundException {

    private final static String MESSAGE = "Book not found with uuid %s";

    public BookNotFoundException(String uuid) {
        super(String.format(MESSAGE, uuid));
    }
}

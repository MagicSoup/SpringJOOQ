package ch.qiminfo.librairy.exception;

public final class AuthorNotFoundException extends AbstractNotFoundException {

    private final static String MESSAGE = "Author not found with uuid %s";

    public AuthorNotFoundException(String uuid) {
        super(String.format(MESSAGE, uuid));
    }
}

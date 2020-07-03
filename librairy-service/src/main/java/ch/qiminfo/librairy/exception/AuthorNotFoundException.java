package ch.qiminfo.librairy.exception;

public final class AuthorNotFoundException extends AbstractNotFoundException {

    private static final String MESSAGE = "Author not found with uuid %s";

    public AuthorNotFoundException(String uuid) {
        super(String.format(MESSAGE, uuid));
    }
}

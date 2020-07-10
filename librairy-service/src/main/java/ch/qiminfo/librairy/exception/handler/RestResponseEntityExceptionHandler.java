package ch.qiminfo.librairy.exception.handler;

import ch.qiminfo.librairy.exception.AuthorNotFoundException;
import ch.qiminfo.librairy.exception.BookNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Rest response entity exception handler.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * The Conflict message error.
     */
    static final String CONFLICT_MESSAGE_ERROR = "A conflict has been dectected.";

    /**
     * Handle conflict response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, CONFLICT_MESSAGE_ERROR, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Handle not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value = {AuthorNotFoundException.class, BookNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}

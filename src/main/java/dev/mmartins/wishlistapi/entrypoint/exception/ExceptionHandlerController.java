package dev.mmartins.wishlistapi.entrypoint.exception;

import dev.mmartins.wishlistapi.application.exception.WishlistLimitExceededException;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final WishlistNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(WishlistLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleLimitExceeded(final WishlistLimitExceededException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(final IllegalStateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(final Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    private ResponseEntity<ErrorResponse> buildResponse(final HttpStatus status, final String message) {
        ErrorResponse error = new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }
}

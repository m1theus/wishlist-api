package dev.mmartins.wishlistapi.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class WishlistLimitExceededException extends RuntimeException {
    @Getter
    private HttpStatus status;

    public WishlistLimitExceededException() {
        this.status = HttpStatus.BAD_REQUEST;
    }

    public WishlistLimitExceededException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}

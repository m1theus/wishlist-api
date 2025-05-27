package dev.mmartins.wishlistapi.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class WishlistNotFoundException extends RuntimeException {
    @Getter
    private HttpStatus status;

    public WishlistNotFoundException() {
        this.status = HttpStatus.NOT_FOUND;
    }

    public WishlistNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}

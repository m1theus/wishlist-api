package dev.mmartins.wishlistapi.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ProductAlreadyInWishlistException extends RuntimeException {
    @Getter
    private HttpStatus status;

    public ProductAlreadyInWishlistException() {
        this.status = HttpStatus.CONFLICT;
    }

    public ProductAlreadyInWishlistException(final String productId) {
        super(String.format("Product %s already in wishlist", productId));
        this.status = HttpStatus.CONFLICT;
    }
}

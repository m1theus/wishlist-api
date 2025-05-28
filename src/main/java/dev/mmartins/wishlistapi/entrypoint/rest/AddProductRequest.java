package dev.mmartins.wishlistapi.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record AddProductRequest(
        @JsonIgnore String wishlistId,
        String productId
) {
    AddProductRequest With(final String wishlistId) {
        return new AddProductRequest(wishlistId, productId());
    }
}

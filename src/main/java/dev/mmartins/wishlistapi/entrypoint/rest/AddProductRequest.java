package dev.mmartins.wishlistapi.entrypoint.rest;

public record AddProductRequest(
        String wishlistId,
        String productId
) {
    AddProductRequest With(final String wishlistId) {
        return new AddProductRequest(wishlistId, productId());
    }
}

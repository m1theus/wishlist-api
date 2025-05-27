package dev.mmartins.wishlistapi.entrypoint.rest;

public record WishlistRequest(
        String name,
        String owner
) {
}

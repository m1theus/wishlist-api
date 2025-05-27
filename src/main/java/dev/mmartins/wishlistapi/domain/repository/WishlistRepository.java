package dev.mmartins.wishlistapi.domain.repository;

import dev.mmartins.wishlistapi.domain.entity.Wishlist;

public interface WishlistRepository {
    Wishlist create(final Wishlist wishlist);
}

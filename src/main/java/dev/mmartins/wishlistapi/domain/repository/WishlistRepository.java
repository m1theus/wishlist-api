package dev.mmartins.wishlistapi.domain.repository;

import dev.mmartins.wishlistapi.domain.entity.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository {
    Wishlist save(final Wishlist wishlist);
    Optional<Wishlist> findById(final String id);
    List<Wishlist> findAll();
}

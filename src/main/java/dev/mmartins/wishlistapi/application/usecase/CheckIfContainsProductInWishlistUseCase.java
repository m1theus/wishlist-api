package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckIfContainsProductInWishlistUseCase {
    private final WishlistRepository wishlistRepository;

    public CheckIfContainsProductInWishlistUseCase(final WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public boolean execute(final String wishlistId, final String productId) {
        final var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new WishlistNotFoundException(wishlistId));

        var exists = wishlist.containsProduct(productId);
        log.info("product={}, exists={} in the wishlist={}", productId, exists, wishlistId);
        return exists;
    }

}

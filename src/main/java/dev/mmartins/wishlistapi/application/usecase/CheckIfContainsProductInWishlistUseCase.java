package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import org.springframework.stereotype.Component;

@Component
public class CheckIfContainsProductInWishlistUseCase {
    private final WishlistRepository wishlistRepository;

    public CheckIfContainsProductInWishlistUseCase(final WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public boolean execute(final String wishlistId, final String productId) {
        final var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new WishlistNotFoundException(wishlistId));

        return wishlist.containsProduct(productId);
    }

}

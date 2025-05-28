package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import org.springframework.stereotype.Component;

@Component
public class RemoveProductFromWishlistUseCase {
    private final WishlistRepository wishlistRepository;
    public RemoveProductFromWishlistUseCase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public void execute(final String wishlistId, final String productId) {
        final var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new WishlistNotFoundException(wishlistId));

        wishlist.removeProduct(productId);
        wishlistRepository.save(wishlist);
    }
}

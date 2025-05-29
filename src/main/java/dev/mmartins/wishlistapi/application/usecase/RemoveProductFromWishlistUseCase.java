package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
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
        log.info("Removed product={} from wishlist={}", productId, wishlistId);
    }
}

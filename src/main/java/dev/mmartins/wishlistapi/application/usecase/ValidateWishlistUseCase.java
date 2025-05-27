package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.application.exception.WishlistLimitExceededException;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import org.springframework.stereotype.Component;

@Component
public class ValidateWishlistUseCase {

    public void execute(final Wishlist wishlist) {
        if (wishlist == null) {
            throw new WishlistNotFoundException("Wishlist cannot be null");
        }

        if (wishlist.hasReachedLimit()) {
            throw new WishlistLimitExceededException("Wishlist has reached limit");
        }
    }
}

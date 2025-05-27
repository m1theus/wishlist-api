package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.entrypoint.rest.WishlistRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateWishlistUseCase {
    private final WishlistRepository wishlistRepository;
    private final ValidateWishlistUseCase validateWishlistUseCase;

    public CreateWishlistUseCase(final WishlistRepository wishlistRepository,
                                 final ValidateWishlistUseCase validateWishlistUseCase) {
        this.wishlistRepository = wishlistRepository;
        this.validateWishlistUseCase = validateWishlistUseCase;
    }

    public Wishlist execute(final WishlistRequest input) {
        final var wishlist = new Wishlist(input);
        validateWishlistUseCase.execute(wishlist);
        return wishlistRepository.save(wishlist);
    }
}

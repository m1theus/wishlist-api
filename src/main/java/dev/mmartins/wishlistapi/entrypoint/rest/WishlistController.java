package dev.mmartins.wishlistapi.entrypoint.rest;

import dev.mmartins.wishlistapi.application.usecase.CreateWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wishlists")
@RestController
public class WishlistController {
    private final CreateWishlistUseCase createWishlistUseCase;

    public WishlistController(final CreateWishlistUseCase createWishlistUseCase) {
        this.createWishlistUseCase = createWishlistUseCase;
    }

    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody WishlistRequest wishlist) {
        final var output = createWishlistUseCase.execute(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

}

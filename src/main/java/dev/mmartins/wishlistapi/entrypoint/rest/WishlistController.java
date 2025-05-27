package dev.mmartins.wishlistapi.entrypoint.rest;

import dev.mmartins.wishlistapi.application.usecase.AddProductToWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.CreateWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wishlists")
@RestController
public class WishlistController {
    private final CreateWishlistUseCase createWishlistUseCase;
    private final AddProductToWishlistUseCase addProductToWishlistUseCase;

    public WishlistController(final CreateWishlistUseCase createWishlistUseCase,
                              final AddProductToWishlistUseCase addProductToWishlistUseCase) {
        this.createWishlistUseCase = createWishlistUseCase;
        this.addProductToWishlistUseCase = addProductToWishlistUseCase;
    }

    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody WishlistRequest wishlist) {
        final var output = createWishlistUseCase.execute(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PostMapping("/{wishlistId}/products")
    public ResponseEntity<Wishlist> addProductToWishlist(@PathVariable("wishlistId") final String wishlistId,
                                                         @RequestBody final AddProductRequest addProductRequest) {
        final var output = addProductToWishlistUseCase.execute(addProductRequest.With(wishlistId));
        return ResponseEntity.ok(output);
    }
}

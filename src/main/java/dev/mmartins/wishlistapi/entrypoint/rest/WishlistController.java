package dev.mmartins.wishlistapi.entrypoint.rest;

import dev.mmartins.wishlistapi.application.usecase.AddProductToWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.CreateWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.ListAllWishlistsUseCase;
import dev.mmartins.wishlistapi.application.usecase.RemoveProductFromWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/wishlists")
@RestController
public class WishlistController {
    private final CreateWishlistUseCase createWishlistUseCase;
    private final AddProductToWishlistUseCase addProductToWishlistUseCase;
    private final ListAllWishlistsUseCase listAllWishlistsUseCase;
    private final RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    public WishlistController(final CreateWishlistUseCase createWishlistUseCase,
                              final ListAllWishlistsUseCase listAllWishlistsUseCase,
                              final AddProductToWishlistUseCase addProductToWishlistUseCase, RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase) {
        this.createWishlistUseCase = createWishlistUseCase;
        this.listAllWishlistsUseCase = listAllWishlistsUseCase;
        this.addProductToWishlistUseCase = addProductToWishlistUseCase;
        this.removeProductFromWishlistUseCase = removeProductFromWishlistUseCase;
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> listAllWishlists() {
        return ResponseEntity.ok(listAllWishlistsUseCase.execute());
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

    @DeleteMapping("/{wishlistId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable("wishlistId") final String wishlistId,
                                                          @PathVariable("productId") final String productId) {
        removeProductFromWishlistUseCase.execute(wishlistId, productId);
        return ResponseEntity.noContent().build();
    }
}

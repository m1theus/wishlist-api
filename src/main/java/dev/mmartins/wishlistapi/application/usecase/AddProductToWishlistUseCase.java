package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.application.exception.ProductAlreadyInWishlistException;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.domain.entity.Product;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.entrypoint.rest.AddProductRequest;
import org.springframework.stereotype.Component;

@Component
public class AddProductToWishlistUseCase {
    private final WishlistRepository wishlistRepository;

    public AddProductToWishlistUseCase(final WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist execute(final AddProductRequest input) {
        final var wishlist = wishlistRepository.findById(input.wishlistId())
                .orElseThrow(() -> new WishlistNotFoundException(input.wishlistId()));

        if (wishlist.productAlreadyExists(input.productId())) {
            throw new ProductAlreadyInWishlistException(input.productId());
        }

        final var product = new Product(input.productId());
        wishlist.addProduct(product);

        return wishlistRepository.save(wishlist);
    }
}

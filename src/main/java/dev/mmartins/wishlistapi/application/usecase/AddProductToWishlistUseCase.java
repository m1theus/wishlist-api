package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.application.exception.ProductAlreadyInWishlistException;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.domain.entity.Product;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.entrypoint.rest.AddProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddProductToWishlistUseCase {
    private final WishlistRepository wishlistRepository;
    private final ValidateWishlistUseCase validateWishlistUseCase;

    public AddProductToWishlistUseCase(final WishlistRepository wishlistRepository,
                                       final ValidateWishlistUseCase validateWishlistUseCase) {
        this.wishlistRepository = wishlistRepository;
        this.validateWishlistUseCase = validateWishlistUseCase;
    }

    public Wishlist execute(final AddProductRequest input) {
        final var wishlist = wishlistRepository.findById(input.wishlistId())
                .orElseThrow(() -> new WishlistNotFoundException(input.wishlistId()));

        validateWishlistUseCase.execute(wishlist);

        if (wishlist.productAlreadyExists(input.productId())) {
            throw new ProductAlreadyInWishlistException(input.productId());
        }

        final var product = new Product(input.productId(), "PRODUCT_NAME");
        wishlist.addProduct(product);
        log.info("Added product={} to wishlist={}", product.getId(), wishlist.getId());
        return wishlistRepository.save(wishlist);
    }
}

package dev.mmartins.wishlistapi.unit;

import dev.mmartins.wishlistapi.application.exception.WishlistLimitExceededException;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.application.usecase.ValidateWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Product;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dev.mmartins.wishlistapi.unit.WishlistHelper.mockWishlist;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateWishlistUseCaseTest {
    private ValidateWishlistUseCase validateWishlistUseCase;

    @BeforeEach
    void setUp() {
        validateWishlistUseCase = new ValidateWishlistUseCase();
    }

    @Test
    void wishlistShouldBeValid() {
        // given
        final Wishlist wishlist = mockWishlist(2);

        // when + then
        assertDoesNotThrow(() -> validateWishlistUseCase.execute(wishlist));
    }

    @Test
    void shouldThrowExceptionWhenWishlistIsNull() {
        // given
        final Wishlist wishlist = null;

        // when + then
        var exception = assertThrows(WishlistNotFoundException.class, () -> validateWishlistUseCase.execute(wishlist));
        assertNotNull(exception);
    }

    @Test
    void shouldThrowExceptionWhenWishlistIsInvalid() {
        // given
        final Wishlist wishlist = mockWishlist(21);

        // when + then
        var exception = assertThrows(WishlistLimitExceededException.class, () -> validateWishlistUseCase.execute(wishlist));
        assertNotNull(exception);
    }


}

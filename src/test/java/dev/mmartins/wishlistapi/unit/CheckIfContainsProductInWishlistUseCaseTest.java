package dev.mmartins.wishlistapi.unit;

import dev.mmartins.wishlistapi.application.usecase.CheckIfContainsProductInWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CheckIfContainsProductInWishlistUseCaseTest {
    private CheckIfContainsProductInWishlistUseCase checkIfContainsProductInWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void setUp() {
        checkIfContainsProductInWishlistUseCase = new CheckIfContainsProductInWishlistUseCase(wishlistRepository);
    }

    @Test
    void shouldWishlistContainsProduct() {
        // given
        Wishlist wishlist = WishlistHelper.mockWishlist(2);
        Mockito.when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));

        // then
        var response = checkIfContainsProductInWishlistUseCase.execute(wishlist.getId(), "product_1");

        // then
        Assertions.assertTrue(response);
        Mockito.verify(wishlistRepository).findById(wishlist.getId());
        Mockito.verifyNoMoreInteractions(wishlistRepository);
    }

    @Test
    void shouldWishlistNotContainsProduct() {
        // given
        Wishlist wishlist = WishlistHelper.mockWishlist(2);
        Mockito.when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));

        // then
        var response = checkIfContainsProductInWishlistUseCase.execute(wishlist.getId(), "product_3");

        // then
        Assertions.assertFalse(response);
        Mockito.verify(wishlistRepository).findById(wishlist.getId());
        Mockito.verifyNoMoreInteractions(wishlistRepository);
    }
}

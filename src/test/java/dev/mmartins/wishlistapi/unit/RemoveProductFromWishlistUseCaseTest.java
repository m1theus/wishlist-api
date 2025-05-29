package dev.mmartins.wishlistapi.unit;

import dev.mmartins.wishlistapi.application.usecase.CheckIfContainsProductInWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.RemoveProductFromWishlistUseCase;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RemoveProductFromWishlistUseCaseTest {
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void setUp() {
        removeProductFromWishlistUseCase = new RemoveProductFromWishlistUseCase(wishlistRepository);
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        // given
        var wishlist = WishlistHelper.mockWishlist(2);
        var productId = wishlist.getProducts().getFirst().getId();
        Mockito.when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));

        // when
        removeProductFromWishlistUseCase.execute(wishlist.getId(), productId);

        // then
        Assertions.assertEquals(1, wishlist.getProducts().size());
        Mockito.verify(wishlistRepository).findById(wishlist.getId());
        Mockito.verify(wishlistRepository).save(wishlist);
        Mockito.verifyNoMoreInteractions(wishlistRepository);
    }
}

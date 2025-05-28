package dev.mmartins.wishlistapi.unit;

import dev.mmartins.wishlistapi.application.exception.WishlistLimitExceededException;
import dev.mmartins.wishlistapi.application.usecase.CreateWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.ValidateWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.entrypoint.rest.WishlistRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateWishlistUseCaseTest {

    private CreateWishlistUseCase createWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private ValidateWishlistUseCase validateWishlistUseCase;

    @BeforeEach
    void setUp() {
        this.createWishlistUseCase = new CreateWishlistUseCase(wishlistRepository, validateWishlistUseCase);
    }

    @Test
    void shouldCreateWishlist() {
        // given
        var input = new WishlistRequest("wishlist_name", "wishlist_owner");
        doNothing().when(validateWishlistUseCase).execute(any());
        when(wishlistRepository.save(any(Wishlist.class))).thenAnswer(i -> i.getArgument(0));

        // when
        var wishlist = createWishlistUseCase.execute(input);

        // then
        verify(validateWishlistUseCase).execute(any());
        verify(wishlistRepository).save(any(Wishlist.class));
        assertNotNull(wishlist);
    }

    @Test
    void shouldNotCreateWishlistWhenWishlistReachedLimit() {
        // given
        var input = new WishlistRequest("wishlist_name", "wishlist_owner");
        doThrow(WishlistLimitExceededException.class).when(validateWishlistUseCase).execute(any());
        when(wishlistRepository.save(any(Wishlist.class))).thenAnswer(i -> i.getArgument(0));

        // when + then
        var exception = assertThrows(WishlistLimitExceededException.class, () -> createWishlistUseCase.execute(input));
        assertNotNull(exception);
    }

}

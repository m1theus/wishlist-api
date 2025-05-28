package dev.mmartins.wishlistapi.unit;

import dev.mmartins.wishlistapi.application.exception.ProductAlreadyInWishlistException;
import dev.mmartins.wishlistapi.application.exception.WishlistLimitExceededException;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.application.usecase.AddProductToWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.ValidateWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.entrypoint.rest.AddProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddProductToWishlistUseCaseTest {
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ValidateWishlistUseCase validateWishlistUseCase;

    @BeforeEach
    void setUp() {
        addProductToWishlistUseCase = new AddProductToWishlistUseCase(wishlistRepository, validateWishlistUseCase);
    }

    @Test
    void shouldAddProductToWishlist() {
        // given
        var wishlistInput = WishlistHelper.mockWishlist(0);
        when(wishlistRepository.findById(wishlistInput.getId())).thenReturn(Optional.of(wishlistInput));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlistInput);

        // when
        Wishlist wishlist = addProductToWishlistUseCase.execute(new AddProductRequest(wishlistInput.getId(), "product_id_1"));

        // then
        assertNotNull(wishlist);
        assertEquals(1, wishlist.getProducts().size());

        verify(wishlistRepository).findById(wishlistInput.getId());
        verify(validateWishlistUseCase).execute(wishlistInput);
        verify(wishlistRepository).save(wishlistInput);
        verifyNoMoreInteractions(validateWishlistUseCase, wishlistRepository);
    }

    @Test
    void shouldNotAddProductToWishlistIfWishlistNotFound() {
        // given
        var wishlistInput = WishlistHelper.mockWishlist(0);
        when(wishlistRepository.findById(wishlistInput.getId())).thenReturn(Optional.empty());
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlistInput);

        // when + then
        var exception = Assertions.assertThrows(WishlistNotFoundException.class,
                () -> addProductToWishlistUseCase.execute(new AddProductRequest(wishlistInput.getId(), "product_id_1")));
        assertNotNull(exception);
        verify(wishlistRepository).findById(wishlistInput.getId());
        verifyNoMoreInteractions(validateWishlistUseCase, wishlistRepository);
    }

    @Test
    void shouldNotAddProductToWishlistIfProductAlreadyExists() {
        // given
        var wishlistInput = WishlistHelper.mockWishlist(2);
        when(wishlistRepository.findById(wishlistInput.getId())).thenReturn(Optional.of(wishlistInput));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlistInput);

        // when + then
        var exception = Assertions.assertThrows(ProductAlreadyInWishlistException.class,
                () -> addProductToWishlistUseCase.execute(new AddProductRequest(wishlistInput.getId(), "product_1")));
        assertNotNull(exception);
        verify(wishlistRepository).findById(wishlistInput.getId());
        verify(validateWishlistUseCase).execute(wishlistInput);
        verifyNoMoreInteractions(validateWishlistUseCase, wishlistRepository);
    }

    @Test
    void shouldNotAddProductToWishlistIfLimitIsExceeded() {
        // given
        var wishlistInput = WishlistHelper.mockWishlist(20);
        when(wishlistRepository.findById(wishlistInput.getId())).thenReturn(Optional.of(wishlistInput));
        Mockito.doThrow(WishlistLimitExceededException.class).when(validateWishlistUseCase).execute(wishlistInput);
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlistInput);

        // when + then
        var exception = Assertions.assertThrows(WishlistLimitExceededException.class,
                () -> addProductToWishlistUseCase.execute(new AddProductRequest(wishlistInput.getId(), "product_21")));
        assertNotNull(exception);
        verify(wishlistRepository).findById(wishlistInput.getId());
        verify(validateWishlistUseCase).execute(wishlistInput);
        verifyNoMoreInteractions(validateWishlistUseCase, wishlistRepository);
    }
}

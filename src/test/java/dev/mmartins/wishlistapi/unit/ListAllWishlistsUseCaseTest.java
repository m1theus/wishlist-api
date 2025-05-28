package dev.mmartins.wishlistapi.unit;

import dev.mmartins.wishlistapi.application.usecase.ListAllWishlistsUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static dev.mmartins.wishlistapi.unit.WishlistHelper.mockWishlist;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ListAllWishlistsUseCaseTest {

    private ListAllWishlistsUseCase listAllWishlistsUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void setUp() {
        listAllWishlistsUseCase = new ListAllWishlistsUseCase(wishlistRepository);
    }

    @Test
    void shouldListAllWishlists() {
        // given
        List<Wishlist> wishlistList = List.of(mockWishlist(2), mockWishlist(3));
        when(wishlistRepository.findAll()).thenReturn(wishlistList);

        // when
        List<Wishlist> wishlists = listAllWishlistsUseCase.execute();

        // then
        verify(wishlistRepository).findAll();
        assertEquals(2, wishlistList.size());
    }

}

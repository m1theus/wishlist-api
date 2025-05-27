package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAllWishlistsUseCase {
    private final WishlistRepository wishlistRepository;

    public ListAllWishlistsUseCase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> execute() {
        return wishlistRepository.findAll();
    }
}

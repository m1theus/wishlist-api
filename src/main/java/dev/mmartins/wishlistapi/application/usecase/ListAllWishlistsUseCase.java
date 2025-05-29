package dev.mmartins.wishlistapi.application.usecase;

import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ListAllWishlistsUseCase {
    private final WishlistRepository wishlistRepository;

    public ListAllWishlistsUseCase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> execute() {
        var wishlistList = wishlistRepository.findAll();
        log.info("Retrieved all Wishlists, count={}", wishlistList.size());
        return wishlistList;
    }
}

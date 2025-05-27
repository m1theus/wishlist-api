package dev.mmartins.wishlistapi.infrastructure.persistence;

import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.infrastructure.persistence.dao.JpaWishlistRepository;
import dev.mmartins.wishlistapi.infrastructure.persistence.document.WishlistDocument;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository {

    private final JpaWishlistRepository jpaWishlistRepository;

    public WishlistRepositoryImpl(final JpaWishlistRepository jpaWishlistRepository) {
        this.jpaWishlistRepository = jpaWishlistRepository;
    }

    @Override
    public Wishlist save(final Wishlist wishlist) {
        var document = jpaWishlistRepository.save(toDocument(wishlist));
        return toDomain(document);
    }

    @Override
    public Optional<Wishlist> findById(final String id) {
        return jpaWishlistRepository.findById(UUID.fromString(id))
                .map(this::toDomain);
    }

    private Wishlist toDomain(final WishlistDocument document) {
        return Wishlist.from(document);
    }

    private WishlistDocument toDocument(final Wishlist wishlist) {
        return WishlistDocument.from(wishlist);
    }
}

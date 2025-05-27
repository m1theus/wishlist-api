package dev.mmartins.wishlistapi.infrastructure.persistence.dao;

import dev.mmartins.wishlistapi.infrastructure.persistence.document.WishlistDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaWishlistRepository extends CrudRepository<WishlistDocument, UUID> {
}

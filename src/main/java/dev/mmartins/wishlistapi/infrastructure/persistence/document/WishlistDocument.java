package dev.mmartins.wishlistapi.infrastructure.persistence.document;

import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document("wishlist")
public class WishlistDocument {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String owner;
    private List<ProductDocument> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static WishlistDocument from(final Wishlist wishlist) {
        var id = StringUtils.isEmpty(wishlist.getId()) ? UUID.randomUUID() : UUID.fromString(wishlist.getId());
        var products = wishlist
                .getProducts()
                .stream()
                .map(ProductDocument::from)
                .toList();

        return new WishlistDocument(id, wishlist.getName(), wishlist.getOwner(), products, wishlist.getCreatedAt(), wishlist.getUpdatedAt());
    }
}

package dev.mmartins.wishlistapi.domain.entity;

import dev.mmartins.wishlistapi.entrypoint.rest.WishlistRequest;
import dev.mmartins.wishlistapi.infrastructure.persistence.document.WishlistDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Wishlist {
    private static final int PRODUCTS_SIZE_LIMIT = 20;
    private String id;
    private String name;
    private String owner;
    private List<Product> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Wishlist(final String id, final String name, final String owner, final List<Product> products) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.products = products;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Wishlist(final WishlistRequest input) {
        this(null, input.name(), input.owner(), List.of());
    }

    public static Wishlist from(final WishlistDocument document) {
        var products = document.getProducts()
                .stream()
                .map(Product::from)
                .toList();
        return new Wishlist(document.getId().toString(),
                document.getName(), document.getOwner(), products, document.getCreatedAt(), document.getUpdatedAt());
    }

    public boolean hasReachedLimit() {
        return products.size() >= PRODUCTS_SIZE_LIMIT;
    }
}

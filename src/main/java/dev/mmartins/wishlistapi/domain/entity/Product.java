package dev.mmartins.wishlistapi.domain.entity;

import dev.mmartins.wishlistapi.infrastructure.persistence.document.ProductDocument;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String id;

    public Product(final String id) {
        this.id = id;
    }

    public static Product from(final ProductDocument document) {
        return new Product(document.getId().toString());
    }
}

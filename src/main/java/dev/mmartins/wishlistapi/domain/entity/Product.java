package dev.mmartins.wishlistapi.domain.entity;

import dev.mmartins.wishlistapi.infrastructure.persistence.document.ProductDocument;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String id;
    private String name;

    public Product(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static Product from(final ProductDocument document) {
        return new Product(document.getId().toString(), document.getName());
    }
}

package dev.mmartins.wishlistapi.domain.entity;

import dev.mmartins.wishlistapi.infrastructure.persistence.document.ProductDocument;

public class Product {

    public static Product from(final ProductDocument document) {
        return new Product();
    }
}

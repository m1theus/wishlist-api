package dev.mmartins.wishlistapi.infrastructure.persistence.document;

import dev.mmartins.wishlistapi.domain.entity.Product;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("products")
public class ProductDocument {

    public static ProductDocument from(final Product product) {
        return new ProductDocument();
    }

}

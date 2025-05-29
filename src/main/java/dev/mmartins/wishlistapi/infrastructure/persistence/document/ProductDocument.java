package dev.mmartins.wishlistapi.infrastructure.persistence.document;

import dev.mmartins.wishlistapi.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Document("products")
public class ProductDocument {
    @Id
    private UUID id =
            UUID.randomUUID();

    private String name;

    public static ProductDocument from(final Product product) {
        return new ProductDocument(UUID.fromString(product.getId()), product.getName());
    }

}

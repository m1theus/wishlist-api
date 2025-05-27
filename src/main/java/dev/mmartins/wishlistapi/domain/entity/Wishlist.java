package dev.mmartins.wishlistapi.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Wishlist {
    private String id;
    private String name;
    private String owner;
    private List<Product> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package dev.mmartins.wishlistapi.unit;

import dev.mmartins.wishlistapi.domain.entity.Product;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;

import java.util.UUID;

public class WishlistHelper {
    public WishlistHelper() {
        throw new UnsupportedOperationException();
    }

    public static Wishlist mockWishlist(int productSize) {
        Wishlist wishlist = new Wishlist();
        wishlist.setId("wishlist_id");
        wishlist.setName("wishlist_name");
        wishlist.setOwner("wishlist_owner");

        for (int i = 0; i < productSize; i++) {
            wishlist.addProduct(new Product(UUID.randomUUID().toString(), "product_name_" + i));
        }

        return wishlist;
    }
}

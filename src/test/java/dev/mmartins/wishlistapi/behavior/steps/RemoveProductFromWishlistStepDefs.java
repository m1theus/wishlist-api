package dev.mmartins.wishlistapi.behavior.steps;

import com.mongodb.assertions.Assertions;
import dev.mmartins.wishlistapi.AbstractBaseTestContainers;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.application.usecase.RemoveProductFromWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Product;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.infrastructure.persistence.dao.JpaWishlistRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RemoveProductFromWishlistStepDefs extends AbstractBaseTestContainers {
    @Autowired
    private JpaWishlistRepository jpaWishlistRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    private Exception capturedException;

    @Before
    public void setup() {
        jpaWishlistRepository.deleteAll();
    }

    @Given("no wishlist exists with ID {string}")
    public void no_wishlist_exists_with_id(String string) {
        jpaWishlistRepository.deleteById(UUID.fromString(string));
    }

    @Given("a wishlist with ID {string} containing the following products:")
    public void a_wishlist_with_id_containing_the_following_products(String string, io.cucumber.datatable.DataTable dataTable) {
        var wishlist = new Wishlist(string, "wishlist_name", "wishlist_owner", new ArrayList<>());
        List<Map<String, String>> rows = dataTable.asMaps();

        for (Map<String, String> row : rows) {
            Product product = new Product(
                    row.get("productId"),
                    row.get("productName")
            );
            wishlist.addProduct(product);
        }

        wishlistRepository.save(wishlist);
    }

    @When("I remove the product with ID {string} from the wishlist {string}")
    public void i_remove_the_product_with_id_from_the_wishlist(String string, String string2) {
        try {
            removeProductFromWishlistUseCase.execute(string2, string);
        } catch (final Exception e) {
            capturedException = e;
        }
    }

    @When("I attempt to remove the product with ID {string} from wishlist {string}")
    public void i_attempt_to_remove_the_product_with_id_from_wishlist(String string, String string2) {
        try {
            removeProductFromWishlistUseCase.execute(string2, string);
        } catch (final Exception e) {
            capturedException = e;
        }
    }

    @Then("the wishlist {string} should not contain the product {string}")
    public void the_wishlist_should_not_contain_the_product(String string, String string2) {
        var wishlist = wishlistRepository.findById(string)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));

        assertNotNull(wishlist);
        assertEquals(string, wishlist.getId());

        for (Product product : wishlist.getProducts()) {
            assertNotEquals(string2, product.getId());
        }
    }

    @Then("the wishlist {string} should still contain the product {string}")
    public void the_wishlist_should_still_contain_the_product(String string, String string2) {
        var wishlist = wishlistRepository.findById(string)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));

        assertNotNull(wishlist);
        assertEquals(string, wishlist.getId());

        for (Product product : wishlist.getProducts()) {
            assertEquals(string2, product.getId());
        }
    }

    @Then("a WishlistNotFoundException should be thrown with the message {string}")
    public void a_wishlist_not_found_exception_should_be_thrown_with_the_message(String string) {
        assertNotNull(capturedException);
        assertInstanceOf(WishlistNotFoundException.class, capturedException);
        assertThat(capturedException.getMessage(), containsString(string));
    }

}

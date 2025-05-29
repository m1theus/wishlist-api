package dev.mmartins.wishlistapi.behavior.steps;

import dev.mmartins.wishlistapi.AbstractBaseTestContainers;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.application.usecase.CheckIfContainsProductInWishlistUseCase;
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

import static com.mongodb.assertions.Assertions.assertFalse;
import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class IsProductInWishlistStepDefs extends AbstractBaseTestContainers {
    @Autowired
    private JpaWishlistRepository jpaWishlistRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private CheckIfContainsProductInWishlistUseCase checkIfContainsProductInWishlistUseCase;

    private Wishlist wishlist;
    private Boolean response;
    private Exception capturedException;

    @Before
    public void setup() {
        jpaWishlistRepository.deleteAll();
    }

    @Given("a wishlist with ID {string} exists")
    public void a_wishlist_with_id_exists(String string) {
        wishlist = new Wishlist(string, "wishlist_name", "wishlist_owner", new ArrayList<>());
        wishlistRepository.save(wishlist);
    }

    @Given("the wishlist contains the product with ID {string}")
    public void the_wishlist_contains_the_product_with_id(String string) {
        wishlist.addProduct(new Product(string, "Laptop"));
        wishlistRepository.save(wishlist);
    }

    @Given("the wishlist does not contain the product with ID {string}")
    public void the_wishlist_does_not_contain_the_product_with_id(String string) {
        wishlist.setProducts(new ArrayList<>());
        wishlistRepository.save(wishlist);
    }

    @Given("no wishlist with ID {string} exists")
    public void no_wishlist_with_id_exists(String string) {
        jpaWishlistRepository.deleteAll();
    }

    @When("I check if the wishlist {string} contains the product {string}")
    public void i_check_if_the_wishlist_contains_the_product(String string, String string2) {
        try {
            response = checkIfContainsProductInWishlistUseCase.execute(string, string2);
        } catch (final Exception e) {
            capturedException = e;
        }
    }

    @Then("the result should be true")
    public void the_result_should_be_true() {
        assertTrue(response);
    }

    @Then("the result should be false")
    public void the_result_should_be_false() {
        assertFalse(response);
    }

    @Then("a WishlistNotFoundException should be thrown")
    public void a_wishlist_not_found_exception_should_be_thrown() {
        assertNotNull(capturedException);
        assertInstanceOf(WishlistNotFoundException.class, capturedException);
    }
}

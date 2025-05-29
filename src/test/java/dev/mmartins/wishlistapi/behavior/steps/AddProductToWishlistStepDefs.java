package dev.mmartins.wishlistapi.behavior.steps;

import dev.mmartins.wishlistapi.AbstractBaseTestContainers;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.application.usecase.AddProductToWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Product;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.entrypoint.rest.AddProductRequest;
import dev.mmartins.wishlistapi.infrastructure.persistence.dao.JpaWishlistRepository;
import dev.mmartins.wishlistapi.unit.WishlistHelper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AddProductToWishlistStepDefs extends AbstractBaseTestContainers {
    @Autowired
    private JpaWishlistRepository jpaWishlistRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    private Exception capturedException;

    @Before
    public void setup() {
        jpaWishlistRepository.deleteAll();
    }

    @Given("the wishlist with id {string} exists but is invalid")
    public void the_wishlist_with_id_exists_but_is_invalid(String string) {
        var wishlist = WishlistHelper.mockWishlist(20);
        wishlist.setId(string);
        wishlistRepository.save(wishlist);
    }

    @Given("the wishlist with id {string} exists and has no products")
    public void the_wishlist_with_id_exists_and_has_no_products(String string) {
        Wishlist wishlist = new Wishlist(string, "wishlist_name", "wishlist_owner", List.of());
        wishlistRepository.save(wishlist);
    }

    @Given("the wishlist with id {string} does not exist")
    public void the_wishlist_with_id_does_not_exist(String string) {
        jpaWishlistRepository.deleteById(UUID.fromString(string));
    }

    @Given("the wishlist with id {string} exists and contains the product with id {string}")
    public void the_wishlist_with_id_exists_and_contains_the_product_with_id(String string, String string2) {
        var product = new Product(string2, "product_" + string2);
        Wishlist wishlist = new Wishlist(string, "wishlist_name", "wishlist_owner", List.of(product));
        wishlistRepository.save(wishlist);
    }

    @When("I add the product with id {string} to the wishlist {string}")
    public void i_add_the_product_with_id_to_the_wishlist(String string, String string2) {
        try {
            addProductToWishlistUseCase.execute(new AddProductRequest(string2, string));
        } catch (final Exception e) {
            capturedException = e;
        }
    }

    @When("I try to add the product with id {string} to the wishlist {string}")
    public void i_try_to_add_the_product_with_id_to_the_wishlist(String string, String string2) {
        try {
            addProductToWishlistUseCase.execute(new AddProductRequest(string2, string));
        } catch (final Exception e) {
            capturedException = e;
        }
    }

    @Then("the wishlist {string} should contain the product with id {string}")
    public void the_wishlist_should_contain_the_product_with_id(String string, String string2) {
        var wishlist = wishlistRepository.findById(string)
                .orElseThrow(() -> new WishlistNotFoundException(string));

        assertNotNull(wishlist);
        assertNotNull(wishlist.getProducts());
        assertEquals(string2, wishlist.getProducts().stream().findFirst().get().getId());
    }

    @Then("an error {string} should be thrown")
    public void an_error_should_be_thrown(String string) {
        assertNotNull(capturedException);
        assertThat(capturedException.getMessage(), containsString(string));
    }
}

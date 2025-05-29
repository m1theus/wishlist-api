package dev.mmartins.wishlistapi.behavior.steps;

import dev.mmartins.wishlistapi.application.exception.WishlistLimitExceededException;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.application.usecase.ValidateWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.unit.WishlistHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ValidateWishlistStepDefs {
    private Wishlist wishlist;
    private Exception capturedException;

    private final ValidateWishlistUseCase validateWishlistUseCase = new ValidateWishlistUseCase();

    @Given("a wishlist with less than the maximum allowed products")
    public void a_wishlist_with_less_than_the_maximum_allowed_products() {
        wishlist = WishlistHelper.mockWishlist(0);
    }

    @When("I validate the wishlist")
    public void i_validate_the_wishlist() {
        try {
            validateWishlistUseCase.execute(wishlist);
        } catch (final Exception e) {
            capturedException = e;
        }
    }

    @Then("no error should be thrown")
    public void no_error_should_be_thrown() {
        assertNull(capturedException);
    }

    @Given("the wishlist is null")
    public void the_wishlist_is_null() {
        wishlist = null;
    }

    @Then("a WishlistNotFoundException should be thrown with message {string}")
    public void a_wishlist_not_found_exception_should_be_thrown_with_message(String string) {
        assertNotNull(capturedException);
        assertInstanceOf(WishlistNotFoundException.class, capturedException);
        assertEquals(string, capturedException.getMessage());
    }

    @Given("a wishlist that has reached the maximum product limit")
    public void a_wishlist_that_has_reached_the_maximum_product_limit() {
        wishlist = WishlistHelper.mockWishlist(20);
    }

    @Then("a WishlistLimitExceededException should be thrown with message {string}")
    public void a_wishlist_limit_exceeded_exception_should_be_thrown_with_message(String string) {
        assertNotNull(capturedException);
        assertInstanceOf(WishlistLimitExceededException.class, capturedException);
        assertEquals(string, capturedException.getMessage());
    }
}

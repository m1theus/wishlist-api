package dev.mmartins.wishlistapi.behavior.steps;

import dev.mmartins.wishlistapi.AbstractBaseTestContainers;
import dev.mmartins.wishlistapi.application.usecase.CreateWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.entrypoint.rest.WishlistRequest;
import dev.mmartins.wishlistapi.infrastructure.persistence.dao.JpaWishlistRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class CreateWishlistStepDefs extends AbstractBaseTestContainers {
    @Autowired
    private JpaWishlistRepository jpaWishlistRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private CreateWishlistUseCase createWishlistUseCase;

    private WishlistRequest wishlist;
    private Wishlist response;
    private Exception capturedException;

    @Before
    public void setup() {
        jpaWishlistRepository.deleteAll();
    }

    @Given("I have valid wishlist data:")
    public void i_have_valid_wishlist_data(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().getFirst();
        wishlist = new WishlistRequest(
                row.get("name"),
                row.get("owner")
        );
    }


    @When("I submit a request to create the wishlist")
    public void i_submit_a_request_to_create_the_wishlist() {
        try {
            response = createWishlistUseCase.execute(wishlist);
        } catch (final Exception e) {
            capturedException = e;
        }
    }

    @Then("the wishlist should be created successfully")
    public void the_wishlist_should_be_created_successfully() {
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
    }

    @Then("the wishlist should be saved in the repository")
    public void the_wishlist_should_be_saved_in_the_repository() {
        var saved = wishlistRepository.findById(response.getId());
        Assertions.assertNotNull(saved);
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(response.getName(), saved.get().getName());
        Assertions.assertEquals(response.getOwner(), saved.get().getOwner());
    }

    @Then("the response should contain the created wishlist data")
    public void the_response_should_contain_the_created_wishlist_data() {
        Assertions.assertNotNull(response);
    }

    @Given("I have invalid wishlist data:")
    public void i_have_invalid_wishlist_data(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().getFirst();
        wishlist = new WishlistRequest(
                row.get("name"),
                row.get("owner")
        );
    }

    @Then("the wishlist creation should fail and a IllegalArgumentException should be thrown")
    public void the_wishlist_creation_should_fail_with_a_validation_error() {
        var wishlistList = wishlistRepository.findAll();
        for (Wishlist saved : wishlistList) {
            Assertions.assertNotEquals(wishlist.name(), saved.getName());
        }
        Assertions.assertInstanceOf(IllegalArgumentException.class, capturedException);
    }
}

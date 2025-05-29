package dev.mmartins.wishlistapi.behavior.steps;

import dev.mmartins.wishlistapi.AbstractBaseTestContainers;
import dev.mmartins.wishlistapi.application.exception.WishlistNotFoundException;
import dev.mmartins.wishlistapi.application.usecase.ListAllWishlistsUseCase;
import dev.mmartins.wishlistapi.domain.entity.Product;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.domain.repository.WishlistRepository;
import dev.mmartins.wishlistapi.infrastructure.persistence.dao.JpaWishlistRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ListAllWishlistStepDefs extends AbstractBaseTestContainers {

    @Autowired
    private JpaWishlistRepository jpaWishlistRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ListAllWishlistsUseCase listAllWishlistsUseCase;

    private List<Wishlist> response;

    @Before
    public void setup() {
        jpaWishlistRepository.deleteAll();
    }

    @Given("no wishlists exist")
    public void no_wishlists_exist() {
        jpaWishlistRepository.deleteAll();
    }

    @Given("the following wishlists exist:")
    public void the_following_wishlists_exist(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            Wishlist wishlist = new Wishlist(
                    row.get("wishlistId"),
                    row.get("name"),
                    row.getOrDefault("owner", "default-owner"),
                    new ArrayList<>()
            );
            wishlistRepository.save(wishlist);
        }
    }

    @Given("the following products exist in wishlists:")
    public void the_following_products_exist_in_wishlists(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();

        for (Map<String, String> row : rows) {
            String wishlistId = row.get("wishlistId");
            String productId = row.get("productId");
            String productName = row.get("productName");

            Wishlist wishlist = wishlistRepository.findById(wishlistId)
                    .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found: " + wishlistId));

            wishlist.addProduct(new Product(productId, productName));
            wishlistRepository.save(wishlist);
        }
    }

    @When("I list all wishlists")
    public void i_list_all_wishlists() {
        response = listAllWishlistsUseCase.execute();
    }

    @Then("I should receive the following wishlists:")
    public void i_should_receive_the_following_wishlists(DataTable dataTable) {
        List<Map<String, String>> expected = dataTable.asMaps();

        assertThat(response).hasSize(expected.size());

        for (Map<String, String> expectedWishlist : expected) {
            String expectedId = expectedWishlist.get("wishlistId");
            String expectedName = expectedWishlist.get("name");

            Optional<Wishlist> found = response.stream()
                    .filter(w -> w.getId().equals(expectedId) && w.getName().equals(expectedName))
                    .findFirst();

            assertThat(found)
                    .withFailMessage("Expected wishlist not found: id=%s name=%s", expectedId, expectedName)
                    .isPresent();
        }
    }

    @Then("I should receive an empty list")
    public void i_should_receive_an_empty_list() {
        assertThat(response).hasSize(0);
    }
}

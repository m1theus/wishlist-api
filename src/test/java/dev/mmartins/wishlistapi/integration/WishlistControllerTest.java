package dev.mmartins.wishlistapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mmartins.wishlistapi.application.usecase.AddProductToWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.CheckIfContainsProductInWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.CreateWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.ListAllWishlistsUseCase;
import dev.mmartins.wishlistapi.application.usecase.RemoveProductFromWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import dev.mmartins.wishlistapi.entrypoint.rest.AddProductRequest;
import dev.mmartins.wishlistapi.entrypoint.rest.WishlistController;
import dev.mmartins.wishlistapi.entrypoint.rest.WishlistRequest;
import dev.mmartins.wishlistapi.unit.WishlistHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateWishlistUseCase createWishlistUseCase = mock(CreateWishlistUseCase.class);
    private ListAllWishlistsUseCase listAllWishlistsUseCase = mock(ListAllWishlistsUseCase.class);
    private AddProductToWishlistUseCase addProductToWishlistUseCase = mock(AddProductToWishlistUseCase.class);
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase = mock(RemoveProductFromWishlistUseCase.class);
    private CheckIfContainsProductInWishlistUseCase checkIfContainsProductInWishlistUseCase = mock(CheckIfContainsProductInWishlistUseCase.class);

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CreateWishlistUseCase createWishlistUseCase() {
            return mock(CreateWishlistUseCase.class);
        }

        @Bean
        public ListAllWishlistsUseCase listAllWishlistsUseCase() {
            return mock(ListAllWishlistsUseCase.class);
        }

        @Bean
        public AddProductToWishlistUseCase addProductToWishlistUseCase() {
            return mock(AddProductToWishlistUseCase.class);
        }

        @Bean
        public RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase() {
            return mock(RemoveProductFromWishlistUseCase.class);
        }

        @Bean
        public CheckIfContainsProductInWishlistUseCase checkIfContainsProductInWishlistUseCase() {
            return mock(CheckIfContainsProductInWishlistUseCase.class);
        }
    }

    @Autowired
    public void setUseCases(
            CreateWishlistUseCase create,
            ListAllWishlistsUseCase listAll,
            AddProductToWishlistUseCase add,
            RemoveProductFromWishlistUseCase remove,
            CheckIfContainsProductInWishlistUseCase check
    ) {
        this.createWishlistUseCase = create;
        this.listAllWishlistsUseCase = listAll;
        this.addProductToWishlistUseCase = add;
        this.removeProductFromWishlistUseCase = remove;
        this.checkIfContainsProductInWishlistUseCase = check;
    }

    @Test
    void shouldListAllWishlists() throws Exception {
        var wishlist = new Wishlist("1", "wishlist_name", "wishlist_owner", List.of());
        when(listAllWishlistsUseCase.execute()).thenReturn(List.of(wishlist));

        mockMvc.perform(get("/wishlists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("wishlist_name"))
                .andExpect(jsonPath("$[0].owner").value("wishlist_owner"))
                .andExpect(jsonPath("$[0].products").isArray())
                .andExpect(jsonPath("$[0].products").isEmpty());
    }

    @Test
    void shouldFindAProductInWishlist() throws Exception {
        var wishlist = WishlistHelper.mockWishlist(2);
        when(checkIfContainsProductInWishlistUseCase.execute(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(get("/wishlists/{wishlistId}/products/{productId}", wishlist.getId(), "product_0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("exists").value(true));
    }

    @Test
    void shouldAddProductToWishlists() throws Exception {
        var wishlist = WishlistHelper.mockWishlist(1);
        String productId = wishlist.getProducts().stream().findFirst().get().getId();
        var request = new AddProductRequest(wishlist.getId(), productId);
        when(addProductToWishlistUseCase.execute(any())).thenReturn(wishlist);

        mockMvc.perform(post("/wishlists/{wishlistId}/products", wishlist.getId())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("wishlist_id"))
                .andExpect(jsonPath("$.name").value("wishlist_name"))
                .andExpect(jsonPath("$.owner").value("wishlist_owner"))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products[0].id").value(productId));
    }

    @Test
    void shouldRemoveProductFromWishlist() throws Exception {
        var wishlist = WishlistHelper.mockWishlist(1);
        doNothing().when(removeProductFromWishlistUseCase).execute(anyString(), anyString());

        mockMvc.perform(delete("/wishlists/{wishlistId}/products/{productId}", wishlist.getId(), "product_0"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAddProductToWishlist() throws Exception {
        var request = new WishlistRequest("wishlist_name", "wishlist_id");
        var wishlist = new Wishlist("1", "wishlist_name", "wishlist_owner", List.of());
        when(createWishlistUseCase.execute(any())).thenReturn(wishlist);

        mockMvc.perform(post("/wishlists")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("wishlist_name"))
                .andExpect(jsonPath("$.owner").value("wishlist_owner"))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products").isEmpty());
    }
}

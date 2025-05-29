package dev.mmartins.wishlistapi.entrypoint.rest;

import dev.mmartins.wishlistapi.application.usecase.AddProductToWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.CheckIfContainsProductInWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.CreateWishlistUseCase;
import dev.mmartins.wishlistapi.application.usecase.ListAllWishlistsUseCase;
import dev.mmartins.wishlistapi.application.usecase.RemoveProductFromWishlistUseCase;
import dev.mmartins.wishlistapi.domain.entity.Wishlist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/wishlists")
@RestController
public class WishlistController {
    private final CreateWishlistUseCase createWishlistUseCase;
    private final AddProductToWishlistUseCase addProductToWishlistUseCase;
    private final ListAllWishlistsUseCase listAllWishlistsUseCase;
    private final RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;
    private final CheckIfContainsProductInWishlistUseCase checkIfContainsProductInWishlistUseCase;

    public WishlistController(final CreateWishlistUseCase createWishlistUseCase,
                              final ListAllWishlistsUseCase listAllWishlistsUseCase,
                              final AddProductToWishlistUseCase addProductToWishlistUseCase,
                              final RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase,
                              final CheckIfContainsProductInWishlistUseCase checkIfContainsProductInWishlistUseCase) {
        this.createWishlistUseCase = createWishlistUseCase;
        this.listAllWishlistsUseCase = listAllWishlistsUseCase;
        this.addProductToWishlistUseCase = addProductToWishlistUseCase;
        this.removeProductFromWishlistUseCase = removeProductFromWishlistUseCase;
        this.checkIfContainsProductInWishlistUseCase = checkIfContainsProductInWishlistUseCase;
    }

    @Operation(
            summary = "List all products from a wishlist",
            description = "Retrieve all products in the specified wishlist"
    )
    @GetMapping
    public ResponseEntity<List<Wishlist>> listAllWishlists() {
        return ResponseEntity.ok(listAllWishlistsUseCase.execute());
    }

    @Operation(
            summary = "Check if a product exists in a specific wishlist",
            description = "Returns true if the product exists in one or more wishlists"
    )
    @GetMapping("/{wishlistId}/products/{productId}")
    public ResponseEntity<IsProductInWishlistResponse> listWishlistsByProductId(@PathVariable("wishlistId") final String wishlistId,
                                                                   @PathVariable("productId") final String productId) {

        final var output = checkIfContainsProductInWishlistUseCase.execute(wishlistId, productId);
        return ResponseEntity.ok().body(new IsProductInWishlistResponse(output));
    }

    @Operation(
            summary = "Create a new wishlist",
            description = "Create a wishlist with name, products, and optional created date"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wishlist created successfully")
    })
    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody WishlistRequest wishlist) {
        final var output = createWishlistUseCase.execute(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @Operation(
            summary = "Add a product to a wishlist",
            description = "Add a product to a wishlist from given wishlist ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully to the wishlist"),
            @ApiResponse(responseCode = "400", description = "Wishlist no found"),
            @ApiResponse(responseCode = "409", description = "Product already exists in the wishlist"),
    })
    @PostMapping("/{wishlistId}/products")
    public ResponseEntity<Wishlist> addProductToWishlist(@PathVariable("wishlistId") final String wishlistId,
                                                         @RequestBody final AddProductRequest addProductRequest) {
        final var output = addProductToWishlistUseCase.execute(addProductRequest.With(wishlistId));
        return ResponseEntity.ok(output);
    }

    @Operation(
            summary = "Remove a product from a wishlist",
            description = "Delete a product from the given wishlist by product ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed successfully")
    })
    @DeleteMapping("/{wishlistId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable("wishlistId") final String wishlistId,
                                                          @PathVariable("productId") final String productId) {
        removeProductFromWishlistUseCase.execute(wishlistId, productId);
        return ResponseEntity.noContent().build();
    }
}

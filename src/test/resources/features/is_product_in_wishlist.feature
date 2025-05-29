Feature: Check if a product is in the wishlist

  Scenario: Product is contained in the wishlist
    Given a wishlist with ID "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" exists
    And the wishlist contains the product with ID "7db0c325-1be8-46af-aad9-711b32eb6a1a"
    When I check if the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" contains the product "7db0c325-1be8-46af-aad9-711b32eb6a1a"
    Then the result should be true

  Scenario: Product is not contained in the wishlist
    Given a wishlist with ID "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" exists
    And the wishlist does not contain the product with ID "4b2a9681-6e9e-4d44-8893-1d42c1b84734"
    When I check if the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" contains the product "4b2a9681-6e9e-4d44-8893-1d42c1b84734"
    Then the result should be false

  Scenario: Wishlist does not exist
    Given no wishlist with ID "dfcdb95f-4279-49aa-baf1-cfb7bed49079" exists
    When I check if the wishlist "dfcdb95f-4279-49aa-baf1-cfb7bed49079" contains the product "7db0c325-1be8-46af-aad9-711b32eb6a1a"
    Then a WishlistNotFoundException should be thrown

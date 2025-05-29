Feature: Add a product to a wishlist

  Scenario: Successfully add a product to an existing wishlist
    Given the wishlist with id "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" exists and has no products
    When I add the product with id "7db0c325-1be8-46af-aad9-711b32eb6a1a" to the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb"
    Then the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" should contain the product with id "7db0c325-1be8-46af-aad9-711b32eb6a1a"

  Scenario: Fail to add a product to a non-existent wishlist
    Given the wishlist with id "dfcdb95f-4279-49aa-baf1-cfb7bed49079" does not exist
    When I try to add the product with id "9902b5a6-88ff-410f-802b-6ffd5e5e9767" to the wishlist "dfcdb95f-4279-49aa-baf1-cfb7bed49079"
    Then an error "Wishlist with id dfcdb95f-4279-49aa-baf1-cfb7bed49079 not found" should be thrown

  Scenario: Fail to add a product that already exists in the wishlist
    Given the wishlist with id "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" exists and contains the product with id "7db0c325-1be8-46af-aad9-711b32eb6a1a"
    When I try to add the product with id "7db0c325-1be8-46af-aad9-711b32eb6a1a" to the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb"
    Then an error "Product 7db0c325-1be8-46af-aad9-711b32eb6a1a already in wishlist" should be thrown

  Scenario: Fail to add a product when the wishlist validation fails
    Given the wishlist with id "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" exists but is invalid
    When I try to add the product with id "4b2a9681-6e9e-4d44-8893-1d42c1b84734" to the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb"
    Then an error "Wishlist has reached limit" should be thrown

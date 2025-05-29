Feature: Remove a product from a wishlist

  Scenario: Successfully remove a product from an existing wishlist
    Given a wishlist with ID "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" containing the following products:
      | productId                            | productName     |
      | 7db0c325-1be8-46af-aad9-711b32eb6a1a | Laptop          |
      | 4b2a9681-6e9e-4d44-8893-1d42c1b84734 | Smartphone      |
    When I remove the product with ID "7db0c325-1be8-46af-aad9-711b32eb6a1a" from the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb"
    Then the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" should not contain the product "7db0c325-1be8-46af-aad9-711b32eb6a1a"
    And the wishlist "18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb" should still contain the product "4b2a9681-6e9e-4d44-8893-1d42c1b84734"

  Scenario: Attempt to remove a product from a non-existent wishlist
    Given no wishlist exists with ID "dfcdb95f-4279-49aa-baf1-cfb7bed49079"
    When I attempt to remove the product with ID "7db0c325-1be8-46af-aad9-711b32eb6a1a" from wishlist "dfcdb95f-4279-49aa-baf1-cfb7bed49079"
    Then a WishlistNotFoundException should be thrown with the message "Wishlist with id dfcdb95f-4279-49aa-baf1-cfb7bed49079 not found"

  Scenario: Attempt to remove a product that is not in the wishlist
    Given a wishlist with ID "1413081c-12b4-4796-99e0-98964831ca96" containing the following products:
      | productId                            | productName |
      | 9902b5a6-88ff-410f-802b-6ffd5e5e9767 | Book        |
    When I remove the product with ID "e815fe25-d6c1-4bae-8798-d91de59d2d6b" from the wishlist "1413081c-12b4-4796-99e0-98964831ca96"
    Then the wishlist "1413081c-12b4-4796-99e0-98964831ca96" should still contain the product "9902b5a6-88ff-410f-802b-6ffd5e5e9767"
    And the wishlist "1413081c-12b4-4796-99e0-98964831ca96" should not contain the product "e815fe25-d6c1-4bae-8798-d91de59d2d6b"

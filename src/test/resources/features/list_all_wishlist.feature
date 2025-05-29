Feature: List all wishlists

  Scenario: Wishlists exist in the system
    Given the following wishlists exist:
      | wishlistId                           | name            | owner           |
      | 18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb | John's Wishlist | John            |
      | dfcdb95f-4279-49aa-baf1-cfb7bed49079 | Mary's Wishlist | Mary            |
    And the following products exist in wishlists:
      | wishlistId                           | productId                               | productName     |
      | 18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb | 7db0c325-1be8-46af-aad9-711b32eb6a1a    | Laptop          |
      | 18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb | 4b2a9681-6e9e-4d44-8893-1d42c1b84734    | Smartphone      |
      | dfcdb95f-4279-49aa-baf1-cfb7bed49079 | 9902b5a6-88ff-410f-802b-6ffd5e5e9767    | Book            |
      | dfcdb95f-4279-49aa-baf1-cfb7bed49079 | e815fe25-d6c1-4bae-8798-d91de59d2d6b    | Headphones      |
    When I list all wishlists
    Then I should receive the following wishlists:
      | wishlistId                           | name            | owner           |
      | 18bc37a8-eae0-4ce4-b587-4d4e5e84c4fb | John's Wishlist | John            |
      | dfcdb95f-4279-49aa-baf1-cfb7bed49079 | Mary's Wishlist | Mary            |

  Scenario: No wishlists exist in the system
    Given no wishlists exist
    When I list all wishlists
    Then I should receive an empty list

Feature: Create a wishlist

  Scenario: Successfully create a new wishlist with valid data
    Given I have valid wishlist data:
      | name          | owner           |
      | Shopping List | Matheus         |
    When I submit a request to create the wishlist
    Then the wishlist should be created successfully
    And the wishlist should be saved in the repository
    And the response should contain the created wishlist data

  Scenario: Fail to create a wishlist with invalid data
    Given I have invalid wishlist data:
      | name | owner           |
      |      | Unknown  |
    When I submit a request to create the wishlist
    Then the wishlist creation should fail and a IllegalArgumentException should be thrown

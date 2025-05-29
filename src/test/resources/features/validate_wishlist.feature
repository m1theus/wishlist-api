Feature: Validate a wishlist before performing operations

  Scenario: Valid wishlist passes validation
    Given a wishlist with less than the maximum allowed products
    When I validate the wishlist
    Then no error should be thrown

  Scenario: Wishlist is null
    Given the wishlist is null
    When I validate the wishlist
    Then a WishlistNotFoundException should be thrown with message "Wishlist with id Wishlist cannot be null not found"

  Scenario: Wishlist exceeds the maximum allowed products
    Given a wishlist that has reached the maximum product limit
    When I validate the wishlist
    Then a WishlistLimitExceededException should be thrown with message "Wishlist has reached limit"

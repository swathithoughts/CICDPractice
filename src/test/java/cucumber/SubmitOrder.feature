@tag
Feature: Purchase the order from Ecommerce website
  I want to use this template for my feature file

	Background: 
	Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on confirmation page.

    Examples: 
      | name  						| 		password 		|	productName |
      | anshika@gmail.com |     Iamking@000 |	ZARA COAT 3	|
package saucedemotests.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saucedemotests.core.SauceDemoBaseWebTest;
import saucedemotests.enums.TestData;

public class ProductsTests extends SauceDemoBaseWebTest {
    public final String BACKPACK_TITLE = "Sauce Labs Backpack";
    public final String SHIRT_TITLE = "Sauce Labs Bolt T-Shirt";

    @BeforeEach
    public void beforeTest(){
        authenticateWithStandardUser();
    }

    // Извеждаме повторния код за логин
    private void authenticateWithStandardUser() {
        loginPage.navigate();
        loginPage.submitLoginForm(TestData.STANDARD_USER_USERNAME.getValue(), TestData.STANDARD_USER_PASSWORD.getValue());
        inventoryPage.waitForPageTitle();
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart(){
        inventoryPage.addProductsByTitle(BACKPACK_TITLE, SHIRT_TITLE);
        inventoryPage.clickShoppingCartLink();
        Assertions.assertEquals(2, shoppingCartPage.getShoppingCartItems().size(), "The products were not added correctly to the cart.");
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation(){
        inventoryPage.addProductsByTitle(BACKPACK_TITLE);
        inventoryPage.clickShoppingCartLink();
        shoppingCartPage.clickCheckout();

        checkoutYourInformationPage.fillShippingDetails("John", "Doe", "12345");
        checkoutYourInformationPage.clickContinue();

        Assertions.assertEquals(1, checkoutOverviewPage.getShoppingCartItems().size(), "The number of products does not match.");

        String totalLabel = checkoutOverviewPage.getTotalLabelText();
        Assertions.assertTrue(totalLabel.contains("Total"), "The total amount is not calculated correctly.");
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm(){
        inventoryPage.addProductsByTitle(BACKPACK_TITLE, SHIRT_TITLE);
        inventoryPage.clickShoppingCartLink();
        shoppingCartPage.clickCheckout();

        checkoutYourInformationPage.fillShippingDetails("John", "Doe", "12345");
        checkoutYourInformationPage.clickContinue();

        checkoutOverviewPage.clickFinish();
        Assertions.assertTrue(checkoutCompletePage.getPageUrl().contains("checkout-complete.html"), "The order was not completed successfully.");

        checkoutCompletePage.navigate();
        Assertions.assertTrue(inventoryPage.getShoppingCartItemsNumber() == 0, "The cart is not empty after completing the order.");
    }
}
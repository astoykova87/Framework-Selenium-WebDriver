package com.saucedemo.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static testframework.DriverManager.getDriver;

public class LoginPage extends BaseSauceDemoPage {
    public LoginPage() {
        super("/");
    }

    // Locators
    private final By usernameLocator = By.xpath("//input[@data-test='username']");
    private final By passwordLocator = By.xpath("//input[@data-test='password']");
    private final By loginButtonLocator = By.xpath("//input[@id='login-button']");

    // Actions
    public void submitLoginForm(String username, String pass) {
        WebElement usernameInput = driver().findElement(usernameLocator);
        usernameInput.sendKeys(username);

        WebElement password = driver().findElement(passwordLocator);
        password.sendKeys(pass);

        WebElement loginButton = driver().findElement(loginButtonLocator);
        loginButton.click();
    }
    private By lockedOutMessageLocator = By.cssSelector("h3[data-test='error']");
    public void assertLockedOutMessage() {
        String expectedMessage = "Epic sadface: Sorry, this user has been locked out.";
        String actualMessage = driver().findElement(lockedOutMessageLocator).getText();
        Assertions.assertEquals(expectedMessage, actualMessage, "The locked-out message is incorrect or not displayed.");
    }

}
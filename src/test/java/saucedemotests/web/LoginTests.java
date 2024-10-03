package saucedemotests.web;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import saucedemotests.core.SauceDemoBaseWebTest;
import saucedemotests.enums.TestData;

public class LoginTests extends SauceDemoBaseWebTest {


    @ParameterizedTest
    @EnumSource(value = TestData.class, names = {
            "STANDARD_USER_USERNAME",
            "STANDARD_USER_LOCKED_OUT_USER",
            "STANDARD_USER_PROBLEM_USER",
            "STANDARD_USER_PERFORMANCE_GLITCH_USER",
            "STANDARD_USER_VISUAL_USER"
    })
    public void userAuthenticated_when_differentCredentialsProvided(TestData username){
        loginPage.navigate();
        loginPage.submitLoginForm(username.getValue(), TestData.STANDARD_USER_PASSWORD.getValue());

        if (username == TestData.STANDARD_USER_LOCKED_OUT_USER) {
            loginPage.assertLockedOutMessage();
        } else {
            inventoryPage.waitForPageTitle();
            inventoryPage.assertNavigated();
        }
    }
}

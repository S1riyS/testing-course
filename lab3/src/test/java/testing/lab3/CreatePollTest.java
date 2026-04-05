package testing.lab3;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.AskPage;
import testing.lab3.pages.HomePage;

import java.io.IOException;
import java.time.Duration;

public class CreatePollTest {

    private static Utils utils;
    private static WebDriver driver;
    private static HomePage homePage;
    private static AskPage askPage;
    private static AuthCookieConfig authCookieConfig;

    @BeforeAll
    public static void setUp() throws IOException {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        homePage = new HomePage(driver);
        askPage = new AskPage(driver);

        authCookieConfig = AuthCookieConfig.load();
        authCookieConfig.applySessionCookie(driver);
    }

    @AfterAll
    public static void tearDown() {
        if (utils != null) {
            utils.quitDriver();
        }
    }

    @Test
    public void createPollTest() {
        homePage.clickAskButton();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/ask"));

        askPage.createPoll();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe("https://otvet.mail.ru/"));
    }
}

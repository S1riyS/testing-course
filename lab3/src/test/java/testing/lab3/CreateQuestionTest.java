package testing.lab3;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.AskPage;
import testing.lab3.pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

public class CreateQuestionTest {

    private static Utils utils;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static HomePage homePage;
    private static AskPage askPage;
    private static AuthCookieConfig authCookieConfig;

    @BeforeEach
    public void setUp() throws IOException {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        wait = utils.getWait();
        homePage = new HomePage(driver);
        askPage = new AskPage(driver);

        authCookieConfig = AuthCookieConfig.load();
        authCookieConfig.applySessionCookie(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void createQuestionTest() {
        homePage.clickAskButton();
        wait.until(ExpectedConditions.urlContains("/ask"));

        String title = askPage.createQuestion();

        wait.until(ExpectedConditions.urlToBe("https://otvet.mail.ru/"));
        assertEquals(title, homePage.getFirstQuestionTitleText());
    }

    @Test
    public void createInvalidQuestionTest() {
        homePage.clickAskButton();
        wait.until(ExpectedConditions.urlContains("/ask"));

        askPage.createInvalidQuestion();

        wait.until(ExpectedConditions.visibilityOf(askPage.getInvalidQuestionMessag()));
        assertTrue(askPage.isInvalidQuestionMessageDisplayed());
    }
}

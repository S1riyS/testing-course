package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.HomePage;
import testing.lab3.pages.QuestionPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewQuestionTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private HomePage homePage;
    private QuestionPage questionPage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        wait = utils.getWait();
        js = utils.getJs();
        homePage = new HomePage(driver);
        questionPage = new QuestionPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void viewFromFeedTest() {
        homePage.clickFirstQuestion();

        wait.until(ExpectedConditions.urlContains("/question/"));

        assertTrue(questionPage.isQuestionTitleDisplayed());
        assertTrue(questionPage.isQuestionTextDisplayed());
    }

    @Test
    public void scrollQuestionPageTest() {
        homePage.clickFirstQuestion();
        wait.until(ExpectedConditions.urlContains("/question/"));
        js.executeScript("window.scrollBy(0, 500)");
        js.executeScript("window.scrollBy(0, -250)");
        assertTrue(questionPage.isQuestionTitleDisplayed());
        assertTrue(questionPage.isQuestionTextDisplayed());
    }
}

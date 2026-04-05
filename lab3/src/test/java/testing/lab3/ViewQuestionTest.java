package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import testing.lab3.pages.HomePage;
import testing.lab3.pages.QuestionPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewQuestionTest {

    private WebDriver driver;
    private JavascriptExecutor js;
    private HomePage homePage;
    private QuestionPage questionPage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
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
        assertTrue(driver.getCurrentUrl().contains("/question/"));
        assertTrue(questionPage.isQuestionTitleDisplayed());
        assertTrue(questionPage.isQuestionTextDisplayed());
    }

    @Test
    public void scrollQuestionPageTest() {
        homePage.clickFirstQuestion();
        js.executeScript("window.scrollBy(0, 500)");
        js.executeScript("window.scrollBy(0, -250)");
        assertTrue(questionPage.isQuestionTitleDisplayed());
        assertTrue(questionPage.isQuestionTextDisplayed());
    }
}

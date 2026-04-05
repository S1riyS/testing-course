package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import testing.lab3.pages.HomePage;
import testing.lab3.pages.SearchPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchQuestionTest {

    private Utils utils;
    private WebDriver driver;
    private JavascriptExecutor js;
    private HomePage homePage;
    private SearchPage searchPage;

    @BeforeEach
    public void setUp() {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        js = utils.getJs();
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchQuestionTest() {
        homePage.searchQuestion("Тортик рецепты");
        searchPage.chooseQuestion();
    }

    @Test
    public void searchInvalidQuestionTest() {
        homePage.searchInvalidQuestion();
        assertFalse(driver.getCurrentUrl().contains("/search"));
    }

    @Test
    public void searchAndScrollTest() {
        homePage.searchQuestion("Программирование Java");
        js.executeScript("window.scrollBy(0, 500)");
        js.executeScript("window.scrollBy(0, -250)");
        assertTrue(driver.getCurrentUrl().contains("search"));
    }
}

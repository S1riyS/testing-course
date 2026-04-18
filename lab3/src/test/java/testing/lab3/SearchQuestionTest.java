package testing.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.HomePage;
import testing.lab3.pages.SearchPage;

public class SearchQuestionTest {

    private Utils utils;
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private SearchPage searchPage;

    @BeforeEach
    public void setUp() {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        wait = utils.getWait();
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
        String query = "Тестирование UI";

        homePage.searchQuestion(query);
        wait.until(ExpectedConditions.urlContains("/search"));

        assertEquals(query, searchPage.getSearchQueryHeaderText());
        assertTrue(searchPage.getFeedElementsCount() > 0);
    }

    @Test
    public void searchInvalidQuestionTest() {
        String query = "     ";

        homePage.searchQuestion(query);
        wait.until(ExpectedConditions.urlContains("/search"));

        assertTrue(searchPage.getNotFoundMessageText().contains("Ничего не найдено"));
    }
}

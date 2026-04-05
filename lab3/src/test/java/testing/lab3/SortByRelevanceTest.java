package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortByRelevanceTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        wait = utils.getWait();
        homePage = new HomePage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void sortByPopularTest() {
        homePage.sortByRelevance("popular");
        assertTrue(homePage.getRelevanceSortButtonText().contains("Популярное"));
        wait.until(ExpectedConditions.urlContains("/popular"));
    }

    @Test
    public void sortByDebatedTest() {
        homePage.sortByRelevance("debated");
        assertTrue(homePage.getRelevanceSortButtonText().contains("Обсуждаемое"));
        wait.until(ExpectedConditions.urlContains("/debated"));
    }
}

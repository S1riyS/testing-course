package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import testing.lab3.pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortByRelevanceTest {

    private WebDriver driver;
    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
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
        assertTrue(driver.getCurrentUrl().contains("/popular"));
    }

    @Test
    public void sortByDebatedTest() {
        homePage.sortByRelevance("debated");
        assertTrue(homePage.getRelevanceSortButtonText().contains("Обсуждаемое"));
        assertTrue(driver.getCurrentUrl().contains("/debated"));
    }
}

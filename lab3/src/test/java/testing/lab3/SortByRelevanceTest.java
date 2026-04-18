package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Dimension;
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

    @ParameterizedTest
    @CsvSource({
            "1280, 800", // Default resolution
            "430, 932", // iPhone 14 Pro max
    })
    public void sortByPopularTest(int width, int height) {
        // Resize
        driver.manage().window().setSize(new Dimension(width, height));
        driver.get("https://otvet.mail.ru/");
        wait.until(ExpectedConditions.urlMatches("https://otvet.mail.ru/"));
        homePage = new HomePage(driver);

        homePage.sortByRelevance("popular");
        assertTrue(homePage.getRelevanceSortButtonText().contains("Популярное"));
        wait.until(ExpectedConditions.urlContains("/popular"));

        assertTrue(homePage.getFeedElementsCount() > 0);
    }
}

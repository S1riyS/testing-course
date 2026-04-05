package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterByRankTest {

    private Utils utils;
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        utils = new Utils();
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
    public void filterByExaltedTest() {
        homePage.filterByRank("exalted");
        assertTrue(homePage.getRankFilterButtonText().contains("Возвысившиеся"));
        wait.until(ExpectedConditions.urlContains("otvet.mail.ru"));
    }

    @Test
    public void filterByComprehendingTest() {
        homePage.filterByRank("comprehending");
        assertTrue(homePage.getRankFilterButtonText().contains("Постигающие"));
        wait.until(ExpectedConditions.urlContains("otvet.mail.ru"));
    }
}

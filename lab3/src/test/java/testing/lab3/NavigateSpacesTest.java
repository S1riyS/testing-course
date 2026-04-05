package testing.lab3;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testing.lab3.pages.HomePage;
import testing.lab3.pages.SpacePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigateSpacesTest {

    private WebDriver driver;
    private HomePage homePage;
    private SpacePage spacePage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        homePage = new HomePage(driver);
        spacePage = new SpacePage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void navigateToITSpaceTest() {
        homePage.navigateToSpace("it");
        assertTrue(spacePage.isHeadingDisplayed());
    }

    @Test
    public void navigateToOtherSpaceTest() {
        homePage.navigateToSpace("other");
        assertTrue(spacePage.isHeadingDisplayed());
    }

    @Test
    public void navigateToLoveSpaceTest() {
        homePage.navigateToSpace("truelove");
        assertTrue(spacePage.isHeadingDisplayed());
    }

    @Test
    public void navigateToSpacesPageTest() {
        homePage.clickSpacesLink();

        // Wait for the URL to contain "/spaces"
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/spaces"));
    }
}

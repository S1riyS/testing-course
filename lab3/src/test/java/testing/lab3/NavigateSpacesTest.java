package testing.lab3;

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
    private WebDriverWait wait;
    private HomePage homePage;
    private SpacePage spacePage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        wait = utils.getWait();
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
        wait.until(ExpectedConditions.urlContains("/spaces"));
    }
}

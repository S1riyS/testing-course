package testing.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.HomePage;
import testing.lab3.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private ProfilePage profilePage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        wait = utils.getWait();
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void viewProfileTest() {
        homePage.clickFirstProfileLink();
        wait.until(ExpectedConditions.urlContains("/profile/"));

        // Key elements:
        assertTrue(profilePage.isUserNameDisplayed());
        assertTrue(profilePage.isRankDisplayed());
        assertTrue(profilePage.isKarmaBlockDisplayed());

        // Tabs:
        assertTrue(profilePage.isPostsTabDisplayed());
        assertTrue(profilePage.isAnswersTabDisplayed());
        assertTrue(profilePage.isAwardsTabDisplayed());

    }
}

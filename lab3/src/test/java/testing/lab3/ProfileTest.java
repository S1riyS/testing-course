package testing.lab3;

import java.net.URI;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testing.lab3.pages.HomePage;
import testing.lab3.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTest {

    private WebDriver driver;
    private HomePage homePage;
    private ProfilePage profilePage;

    @BeforeEach
    public void setUp() {
        Utils utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
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
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/profile/"));

        // Name matches URL
        String usernameFromUrl = usernameFromProfileUrl(driver.getCurrentUrl());
        assertEquals(usernameFromUrl, profilePage.getUserName());

        // Key elements of profile are displayed
        assertTrue(profilePage.isUserNameDisplayed());
        assertTrue(profilePage.isRankDisplayed());
        assertTrue(profilePage.isKarmaBlockDisplayed());
    }

    private static String usernameFromProfileUrl(String url) {
        String path = URI.create(url).getPath();
        if (!path.startsWith("/profile/")) {
            throw new IllegalArgumentException("Expected /profile/<username> in URL: " + url);
        }

        String rest = path.substring("/profile/".length());
        int slash = rest.indexOf('/');
        return slash < 0 ? rest : rest.substring(0, slash);
    }
}

package testing.lab3;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testing.lab3.pages.QuestionPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnswerQuestionTest {

    // In order not to trash questions of real people we will use our own question
    // private static final String QUESTION_URL =
    // "https://otvet.mail.ru/question/269416126";
    private static final String QUESTION_URL = "https://otvet.mail.ru/question/269592428";

    private static Utils utils;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static QuestionPage questionPage;
    private static AuthCookieConfig authCookieConfig;

    @BeforeEach
    public void setUp() throws IOException {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        wait = utils.getWait();
        questionPage = new QuestionPage(driver);

        authCookieConfig = AuthCookieConfig.load();
        authCookieConfig.applySessionCookie(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void answerOnQuestionValidTest() {
        driver.navigate().to(QUESTION_URL);

        String reply = "Test answer from Selenium WebDriver!" + System.currentTimeMillis();
        questionPage.setQuestionAnswer(reply);
        questionPage.sendAnswer();

        wait.until(ExpectedConditions.visibilityOf(questionPage.getMyReply()));
        assertTrue(questionPage.isMyReplyDisplayed());
    }

    @Test
    public void answerOnQuestionInvalidTest() {
        driver.navigate().to(QUESTION_URL);

        questionPage.setQuestionAnswer("          ");

        assertFalse(questionPage.isSendAnswerButtonEnabled());
    }
}

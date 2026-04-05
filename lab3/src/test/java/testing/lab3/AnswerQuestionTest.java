package testing.lab3;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import testing.lab3.pages.QuestionPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnswerQuestionTest {

    // In order not to trash questions of real people we will use our own question
    private static final String QUESTION_URL = "https://otvet.mail.ru/question/269416126";

    private static Utils utils;
    private static WebDriver driver;
    private static QuestionPage questionPage;
    private static AuthCookieConfig authCookieConfig;

    @BeforeAll
    public static void setUp() throws IOException {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        questionPage = new QuestionPage(driver);

        authCookieConfig = AuthCookieConfig.load();
        authCookieConfig.applySessionCookie(driver);
    }

    @AfterAll
    public static void tearDown() {
        if (utils != null) {
            utils.quitDriver();
        }
    }

    @Test
    public void answerOnQuestionValidTest() {
        driver.navigate().to(QUESTION_URL);

        questionPage.setQuestionAnswer("Test answer from Selenium WebDriver!");
        assertEquals(true, questionPage.isSendAnswerButtonEnabled());

        questionPage.sendAnswer();
    }

    @Test
    public void answerOnQuestionInvalidTest() {
        driver.navigate().to(QUESTION_URL);

        questionPage.setQuestionAnswer("          ");
        assertEquals(false, questionPage.isSendAnswerButtonEnabled());
    }
}

package testing.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends Page {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    private static final By FIRST_QUESTION_LINK =
            By.xpath("(//a[contains(@href, '/question/')])[1]");

    /**
     * После выдачи поиска открывает первый вопрос из результатов.
     */
    public void chooseQuestion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(FIRST_QUESTION_LINK));
        link.click();
    }

    public void clickFirstSearchResult() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.elementToBeClickable(FIRST_QUESTION_LINK)).click();
    }
}

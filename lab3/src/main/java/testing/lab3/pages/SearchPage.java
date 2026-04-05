package testing.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage extends Page {

    private final WebDriverWait wait;

    public SearchPage(WebDriver driver, WebDriverWait wait) {
        super(driver);
        this.wait = wait;
    }

    private static final By FIRST_QUESTION_LINK =
            By.xpath("(//a[contains(@href, '/question/')])[1]");

    /**
     * После выдачи поиска открывает первый вопрос из результатов.
     */
    public void chooseQuestion() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(FIRST_QUESTION_LINK));
        link.click();
    }
}

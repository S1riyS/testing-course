package testing.lab3.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpacePage extends Page {

    public SpacePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1")
    private WebElement heading;

    @FindBy(xpath = "//a[contains(@href, '/question/')]")
    private WebElement firstQuestion;

    @FindBy(xpath = "//div[contains(@class, 'space')]//a[contains(@href, '/profile/')]")
    private WebElement firstAuthorLink;

    public String getHeadingText() {
        return heading.getText();
    }

    public boolean isHeadingDisplayed() {
        return heading.isDisplayed();
    }

    public void clickFirstQuestion() {
        firstQuestion.click();
    }

    public void clickFirstAuthorLink() {
        firstAuthorLink.click();
    }
}

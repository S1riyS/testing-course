package testing.lab3.pages;

import java.util.List;

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

    @FindBy(xpath = "//span[preceding::h1]")
    private WebElement description;

    @FindBy(xpath = "//div[contains(@class, 'vkuiInternalCardGrid') and .//div[@role='link']]/*")
    private List<WebElement> feedElements;

    public boolean isHeadingDisplayed() {
        return heading.isDisplayed();
    }

    public String getHeadingText() {
        return heading.getText();
    }

    public boolean isDescriptionDisplayed() {
        return description.isDisplayed();
    }

    public Integer getFeedElementsCount() {
        return feedElements.size();
    }
}

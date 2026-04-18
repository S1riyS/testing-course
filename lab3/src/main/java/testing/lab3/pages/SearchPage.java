package testing.lab3.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage extends Page {

    public SearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1")
    private WebElement searchQueryHeader;

    @FindBy(xpath = "//*[@id='app']/div[2]/div[2]/main/div/div[4]/div/span[1]")
    private WebElement notFoundMessage;

    @FindBy(xpath = "//div[contains(@class, 'vkuiInternalCardGrid')]/*")
    private List<WebElement> feedElements;

    public String getSearchQueryHeaderText() {
        return searchQueryHeader.getText();
    }

    public String getNotFoundMessageText() {
        return notFoundMessage.getText();
    }

    public Integer getFeedElementsCount() {
        return feedElements.size();
    }
}

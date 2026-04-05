package testing.lab3.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage extends Page {

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='app']/div[2]/div[2]/main/div/div[2]/div/div[2]/div[2]/div[1]/h1")
    private WebElement userName;

    @FindBy(xpath = "//*[@id='app']/div[2]/div[2]/main/div/div[2]/div/div[2]/div[2]/div[1]/div/div[1]")
    private WebElement userRank;

    @FindBy(xpath = "//*[@id='app']/div[2]/div[2]/main/div/div[2]/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div")
    private WebElement karmaBlock;

    public boolean isUserNameDisplayed() {
        return userName.isDisplayed();
    }

    public String getUserName() {
        return userName.getText();
    }

    public boolean isRankDisplayed() {
        return userRank.isDisplayed();
    }

    public boolean isKarmaBlockDisplayed() {
        return karmaBlock.isDisplayed();
    }
}

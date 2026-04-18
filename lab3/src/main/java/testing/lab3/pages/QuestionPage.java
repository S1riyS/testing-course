package testing.lab3.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QuestionPage extends Page {

    public QuestionPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1")
    private WebElement questionTitle;

    @FindBy(xpath = "//span[@component='Paragraph']//p")
    private WebElement questionText;

    @FindBy(xpath = "//span[@for='editor']/div//p")
    private WebElement questionAnswerInput;

    @FindBy(xpath = "//button[contains(., 'Отправить')]")
    private WebElement sendAnswerButton;

    @FindBy(xpath = "(//span[contains(@class, '_ReplyItemMy')])[1]")
    private WebElement myReply;

    @FindBy(xpath = "(//div[.//span[contains(.,'Видео по теме')]]/following-sibling::div)[1]")
    private WebElement videosOnTopic;

    public void setQuestionAnswer(String answer) {
        questionAnswerInput.click();
        questionAnswerInput.sendKeys(answer);
    }

    public void sendAnswer() {
        sendAnswerButton.click();
    }

    public boolean isQuestionTitleDisplayed() {
        return questionTitle.isDisplayed();
    }

    public boolean isQuestionTextDisplayed() {
        return questionText.isDisplayed();
    }

    public boolean isSendAnswerButtonEnabled() {
        return sendAnswerButton.isEnabled();
    }

    public boolean isVideosOnTopicDisplayed() {
        return videosOnTopic.isDisplayed();
    }

    public WebElement getMyReply() {
        return myReply;
    }

    public boolean isMyReplyDisplayed() {
        return myReply.isDisplayed();
    }
}

package testing.lab3.pages;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AskPage extends Page {

    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RND = new Random();

    public AskPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='app']/div[2]/div/main/div/div/div/div/div[3]/form/div[3]/div/div/div[3]/div[1]/button")
    private WebElement pollInset;

    @FindBy(xpath = "//*[@id='app']/div[2]/div/main/div/div/div/div/div[4]/div/span/h2/textarea")
    private WebElement pollTitleTextArea;

    @FindBy(xpath = "//textarea[@name='ask_title']")
    private WebElement questionTitleTextArea;

    @FindBy(xpath = "//*[@id='editor-ask-form-editor']/div/p")
    private WebElement questionTextArea;

    // Space select

    @FindBy(xpath = "//button[contains(normalize-space(), 'Выбрать')]")
    private WebElement spaceSelect;

    @FindBy(xpath = " //*[@id='post_create_spaces_list_search']")
    private WebElement spaceSearchInput;

    @FindBy(xpath = "//span[contains(normalize-space(), 'Программирование')]")
    private WebElement programmingSpaceOption;

    @FindBy(xpath = "//button[contains(normalize-space(), 'Опубликовать')]")
    private WebElement questionPublicationButton;

    @FindBy(xpath = "//*[@id='app']/div[2]/div/main/div/div/div/div/div[4]/div/button")
    private WebElement addPollOptionButton;

    @FindBy(xpath = "//*[@id='app']/div[2]/div/main/div/div/div/div/div[4]/div/div[1]/div[1]/div/div[2]/div/span/div/span/span/textarea")
    private WebElement firstPollOption;

    @FindBy(xpath = "//*[@id='app']/div[2]/div/main/div/div/div/div/div[4]/div/div[1]/div[2]/div/div[2]/div/span/div/span/span/textarea")
    private WebElement secondPollOption;

    @FindBy(xpath = "//*[@id='app']/div[2]/div/main/div/div/div/div/div[4]/div/div[1]/div[3]/div/div[2]/div/span/div/span/span/textarea")
    private WebElement thirdPollOption;

    public void selectProgrammingSpace() {
        spaceSelect.click();

        spaceSearchInput.click();
        spaceSearchInput.sendKeys("Программирование");
        programmingSpaceOption.click();

        spaceSelect.click(); // Closes space selection element

    }

    public void fillQuestion(String title, String text) {
        questionTitleTextArea.click();
        questionTitleTextArea.sendKeys(title);
        questionTextArea.click();
        questionTextArea.sendKeys(text);
    }

    public void createPoll() {
        String title = "Тестовый опрос (Selenium WebDriver) " + randomSuffix(8);
        this.fillQuestion(title, "Тест опроса");

        pollInset.click();

        pollTitleTextArea.click();
        pollTitleTextArea.sendKeys("Заголовок тестового опроса");

        // Fill options
        firstPollOption.click();
        firstPollOption.sendKeys("Вариант 1");
        secondPollOption.click();
        secondPollOption.sendKeys("Вариант 2");
        addPollOptionButton.click();
        thirdPollOption.click();
        thirdPollOption.sendKeys("Вариант 3");

        // Scroll down to space selection
        this.scroll(600);
        selectProgrammingSpace();

        // Publish
        questionPublicationButton.click();
    }

    public void createQuestion() {
        String title = "Тестовый вопрос (Selenium WebDriver) " + randomSuffix(8);
        this.fillQuestion(title, "## Header\nBody of my impeccable question!");

        // Scroll down to space selection
        this.scroll(600);
        selectProgrammingSpace();

        // Publish
        questionPublicationButton.click();
    }

    public void createInvalidQuestion() {
        this.fillQuestion("\t\t\t", "   ");

        // Scroll down to space selection
        this.scroll(600);
        selectProgrammingSpace();
    }

    public Boolean isPublicationButtonEnabled() {
        return questionPublicationButton.isEnabled();
    }

    public static String randomSuffix(Integer n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(ALPHANUM.charAt(RND.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
}
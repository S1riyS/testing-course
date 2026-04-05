package testing.lab3.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // --- Navigation ---

    /** Ссылка «Спросить» / форма вопроса или кнопка «Создать» в нижней навигации */
    @FindBy(xpath = "//a[contains(@href, '/ask')] | //button[contains(., 'Создать')]")
    private WebElement askButton;

    @FindBy(xpath = "//a[contains(@href, '/spaces')]")
    private WebElement spacesLink;

    /** Кнопка «Войти» в шапке (актуальная вёрстка без ph-whiteline) */
    @FindBy(xpath = "//button[contains(normalize-space(), 'Войти')]")
    private WebElement enterButton;

    // --- Sort by relevance elements ---

    @FindBy(xpath = "//*[@id='app']/div[2]/div[2]/main/div/div[2]/div[1]/div[1]")
    private WebElement relevanceButton;

    @FindBy(xpath = "//span[contains(., 'Популярному')]")
    private WebElement relevancePopularButton;

    @FindBy(xpath = "//span[contains(., 'Обсуждаемому')]")
    private WebElement relevanceDebatedButton;

    @FindBy(xpath = "//*[@id='app']/div[2]/div[2]/main/div/div[2]/div[1]/div[1]/span[1]")
    private WebElement relevanceButtonText;

    // --- Filter by rank elements ---

    @FindBy(xpath = "//span[contains(., 'Все ранги')]")
    private WebElement allRanksButton;

    @FindBy(xpath = "//span[contains(., 'Возвысившиеся')]")
    private WebElement rankExaltedButton;

    @FindBy(xpath = "//span[contains(., 'Постигающие')]")
    private WebElement rankComprehendingButton;

    @FindBy(xpath = "//*[@id='app']/div[2]/div[2]/main/div/div[2]/div[1]/div[2]/span[1]")
    private WebElement rankFilterButtonText;

    // --- Auth iframe elements (форма Mail.ru в iframe) ---

    @FindBy(xpath = "//input[@name='username']")
    private WebElement accountNameInput;

    @FindBy(xpath = "//div[@data-test-id='error-footer-text']//small[@data-test-id='required']")
    private WebElement accountNameError;

    @FindBy(xpath = "//div[@id='root']//form//div[2]//div[2]//div//div//div//div//div//div//div[3]//div//div//div//div")
    private WebElement mailChoseDropdown;

    @FindBy(xpath = "//div[@id='react-select-2-option-0']//div//div//div[2]//span")
    private WebElement secondDropdownOption;

    @FindBy(xpath = "//div[@id='root']//form//button")
    private WebElement enterModelPageButton;

    @FindBy(xpath = "//div[@id='root']//form//div[2]//div//div//div//div//div//div//div//div//div//div[2]//div[6]//div//div//input")
    private WebElement firstNumber;

    @FindBy(xpath = "//div[@id='root']//form//div[2]//div//div//div//div//div//div//div//div//div//div[2]//div[7]//div//div//input")
    private WebElement secondNumber;

    @FindBy(xpath = "//div[@id='root']//form//div[2]//div//div//div//div//div//div//div//div//span//input")
    private WebElement codeInput;

    @FindBy(xpath = "//div[@id='root']//form//div[2]//div//div[2]//div//button")
    private WebElement submitEnterButton;

    // --- Search (placeholder «Призвать силу поиска…») ---

    @FindBy(xpath = "//input[contains(@placeholder, 'поиск') or contains(@placeholder, 'Поиск')]")
    private WebElement searchInput;

    /** Кнопка запуска поиска рядом с полем */
    @FindBy(xpath = "//input[contains(@placeholder, 'поиск')]/following::button[1]")
    private WebElement magnifierButton;

    // --- Feed tabs ---

    @FindBy(xpath = "//button[contains(., 'Новое')]")
    private WebElement newTabButton;

    @FindBy(xpath = "//button[contains(., 'Все ранги')]")
    private WebElement allRanksTabButton;

    // --- Feed content ---

    @FindBy(xpath = "(//a[contains(@href, '/question/')])[1]")
    private WebElement firstQuestionInFeed;

    // --- User menu / logout ---

    @FindBy(xpath = "//div[contains(@class,'whiteline')]//span[2] | //div[@id='ph-whiteline']//span[2] | //header//button[contains(@class,'avatar')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//span[contains(., 'Выйти')] | //button[contains(., 'Выйти')]")
    private WebElement exitButton;

    // --- Heading ---

    @FindBy(xpath = "//h1")
    private WebElement heading;

    // --- Spaces sidebar links ---

    @FindBy(xpath = "//a[contains(@href, '/space/it')]")
    private WebElement itSpace;

    @FindBy(xpath = "//a[contains(@href, '/space/other')]")
    private WebElement otherSpace;

    @FindBy(xpath = "//a[contains(@href, '/space/truelove')]")
    private WebElement loveSpace;

    // --- Profile link ---

    @FindBy(xpath = "(//a[contains(@href, '/profile/')])[1]")
    private WebElement firstProfileLink;

    // --- Actions ---

    public void clickEnterButton() {
        enterButton.click();
    }

    public void enterLoginAndChooseMailType(String login) {
        mailChoseDropdown.click();
        secondDropdownOption.click();
        accountNameInput.sendKeys(login);
        enterModelPageButton.click();
    }

    public void enterNumbersAndCode(String first, String second) {
        firstNumber.click();
        firstNumber.sendKeys(first);
        secondNumber.click();
        secondNumber.sendKeys(second);
    }

    public void submitLogin() {
        submitEnterButton.click();
        driver.switchTo().defaultContent();
    }

    public boolean isAccountErrorDisplayed() {
        return accountNameError.isDisplayed();
    }

    public void logout() {
        logoutButton.click();
        exitButton.click();
    }

    public void clickAskButton() {
        askButton.click();
    }

    public void clickAllRanksButton() {
        allRanksButton.click();
    }

    public void clickSpacesLink() {
        spacesLink.click();
    }

    public void searchQuestion(String query) {
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(query);
        magnifierButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    public void searchInvalidQuestion() {
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys("\t");
        magnifierButton.click();
    }

    public void clickNewTab() {
        newTabButton.click();
    }

    public void clickAllRanksTab() {
        allRanksTabButton.click();
    }

    public void clickFirstQuestion() {
        firstQuestionInFeed.click();
    }

    public void navigateToSpace(String spaceName) {
        switch (spaceName) {
            case "it" -> itSpace.click();
            case "other" -> otherSpace.click();
            case "truelove" -> loveSpace.click();
            default -> throw new IllegalArgumentException("Unknown space: " + spaceName);
        }
    }

    public void filterByRank(String rankName) {
        this.clickAllRanksButton();
        switch (rankName) {
            case "exalted" -> rankExaltedButton.click();
            case "comprehending" -> rankComprehendingButton.click();
            default -> throw new IllegalArgumentException("Unknown rank: " + rankName);
        }
    }

    public void sortByRelevance(String relevance) {
        this.relevanceButton.click();
        switch (relevance) {
            case "popular" -> relevancePopularButton.click();
            case "debated" -> relevanceDebatedButton.click();
            default -> throw new IllegalArgumentException("Unknown relevance: " + relevance);
        }
    }

    public String getRankFilterButtonText() {
        return rankFilterButtonText.getText();
    }

    public String getRelevanceSortButtonText() {
        return relevanceButtonText.getText();
    }

    public void clickFirstProfileLink() {
        firstProfileLink.click();
    }

    public String getHeadingText() {
        return heading.getText();
    }

    public boolean isSearchInputDisplayed() {
        return searchInput.isDisplayed();
    }
}

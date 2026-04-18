package testing.lab3.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // --- Navigation ---

    @FindBy(xpath = "//a[contains(@href, '/ask')] | //button[contains(., 'Создать')]")
    private WebElement askButton;

    @FindBy(xpath = "//a[contains(@href, '/spaces')]")
    private WebElement spacesLink;

    @FindBy(xpath = "//button[contains(normalize-space(), 'Войти')]")
    private WebElement enterButton;

    // --- Sort by relevance elements ---

    @FindBy(xpath = "//div[@role='button' and descendant::span[contains(text(),'Новое')]]")
    private WebElement relevanceButton;

    @FindBy(xpath = "//span[contains(., 'Популярному')]")
    private WebElement relevancePopularButton;

    @FindBy(xpath = "//span[contains(., 'Обсуждаемому')]")
    private WebElement relevanceDebatedButton;

    @FindBy(xpath = "//div[@role='button']//span")
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

    // --- Search (placeholder «Призвать силу поиска…») ---

    @FindBy(xpath = "//input[contains(@placeholder, 'поиск') or contains(@placeholder, 'Поиск')]")
    private WebElement searchInput;

    @FindBy(xpath = "//input[contains(@placeholder, 'поиск')]/following::button[1]")
    private WebElement magnifierButton;

    // --- Feed content ---

    @FindBy(xpath = "//div[contains(@class, 'vkuiInternalCardGrid') and .//div[@role='link']]/*")
    private List<WebElement> feedElements;

    @FindBy(xpath = "(//a[contains(@href, '/question/')])[1]")
    private WebElement firstQuestionInFeed;

    @FindBy(xpath = "(//span[contains(concat(' ', normalize-space(@class), ' '), ' _PostTitle')])[1]")
    private WebElement firstQuestionTitle;

    // --- User menu / logout ---

    @FindBy(xpath = "//div[contains(@class,'whiteline')]//span[2] | //div[@id='ph-whiteline']//span[2] | //header//button[contains(@class,'avatar')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//span[contains(., 'Выйти')] | //button[contains(., 'Выйти')]")
    private WebElement exitButton;

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
    }

    public void searchInvalidQuestion() {
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys("\t");
        magnifierButton.click();
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

    public String getFirstQuestionTitleText() {
        return firstQuestionTitle.getText();
    }

    public Integer getFeedElementsCount() {
        return feedElements.size();
    }
}

// *[@id="app"]/div[2]/div[2]/main/div/div[4]
// *[@id="app"]/div[2]/div[3]/main/div/div[4]
package org.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InterviewPage {

    WebDriver driver;
    public InterviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='new-messages-count']")
    public WebElement newMessagesCount;

    @FindBy(xpath = "//span[text()='Request Increase']")
    public WebElement requestIncreaseButton;

    @FindBy(xpath = "//li[contains(@class,'has-sub-menu')]//a[contains(@href, 'app.html')]")
    public WebElement appMenuLink;

    @FindBy(xpath = "//h6[@id='time']")
    public WebElement timeLabel;

    @FindBy(xpath = "//div[@class='balance-title']")
    public List<WebElement> balanceTitle;

    @FindBy(xpath = "//div[@class='logged-user-info-w']/div[@class='logged-user-role']")
    public WebElement loggedUserRole;

    @FindBy(xpath = "//a[contains(@class, 'btn-primary')]//span[text()='Add Account']")
    public WebElement addAccountButton;

    @FindBy(xpath = "//div[@class='element-actions']//a[contains(@class,'btn-success')]//span[text()='Make Payment']")
    public WebElement makePaymentButton;

    @FindBy(xpath = "//div[@class='top-menu-controls']//input[@placeholder='Start typing to search...']")
    public WebElement topMenuSearchInput;

    @FindBy(xpath = "//span[@data-type='card-type']")
    public List<WebElement> cardTypeLabel;
}

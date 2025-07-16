package org.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    WebDriver driver;
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='name']")
    private WebElement nameField;

    @FindBy(css = "input#mailId")
    private WebElement emailField;

    @FindBy(xpath = "//input[@id='mobile']")
    private WebElement mobileField;

    @FindBy(xpath = "//input[@value='female']")
    private WebElement femaleRadioButton;

    @FindBy(xpath = "//input[@value='male']")
    private WebElement maleRadioButton;

    @FindBy(xpath = "//select[@id='state']")
    private WebElement stateDropDown;

    @FindBy(xpath = "//textarea[@id='message']")
    private WebElement messageInputField;

    @FindBy(xpath = "//input[@name='subscribe']")
    private WebElement subscribeCheckBox;

    @FindBy(xpath = "//button[normalize-space()='Submit']")
    private WebElement submitButton;

    public void enterName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterMobile(String mobile) {
        mobileField.sendKeys(mobile);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            maleRadioButton.click();
        } else if (gender.equalsIgnoreCase("female")) {
            femaleRadioButton.click();
        }
    }

    public void selectState(String state) {
        Select dropdown = new Select(stateDropDown);
        dropdown.selectByVisibleText(state);
    }

    public void enterMessage(String message) {
        messageInputField.clear();
        messageInputField.sendKeys(message);
    }

    public void checkSubscribeBox() {
        if (!subscribeCheckBox.isSelected()) {
            subscribeCheckBox.click();
        }
    }

    public void clickSubmit() {
        submitButton.click();
    }

}


import org.testng.annotations.Test;
package org.automationscripts;

import com.epam.healenium.SelfHealingDriver;
import org.modal.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.pages.InterviewPage;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class HealeniumTest extends BaseTest {

    @Test
    public void interviewPortalTest(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Recommended for CI
options.addArguments("--disable-dev-shm-usage"); // Recommended for Docker
options.addArguments("--disable-gpu"); // Optional, safer for CI
options.addArguments("--window-size=1920,1080"); // Optional but helps
        WebDriver delegate = new ChromeDriver(options);
        SelfHealingDriver driver = SelfHealingDriver.create((WebDriver) delegate);

        driver.get("http://myinterview.today/healenium/index2.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        InterviewPage interviewPage = new InterviewPage(driver);

        String messageText = interviewPage.newMessagesCount.getText();
//        Assert.assertEquals(messageText.trim(), "7");

        String buttonText = interviewPage.requestIncreaseButton.getText();
        Assert.assertTrue(buttonText.contains("Increase"));

        String cardLink = interviewPage.appMenuLink.getText();
        Assert.assertTrue(cardLink.contains("Credit cards"));

        String timeLabelText = interviewPage.timeLabel.getText();
        Assert.assertTrue(timeLabelText.contains("Your nearest branch closes in"));

        List<String> balanceTitles = interviewPage.balanceTitle.stream().map(WebElement::getText).map(String::trim).toList();
        Assert.assertTrue(balanceTitles.containsAll(Arrays.asList("Total Balance", "Credit Available", "Due Today")));

        String userRole = interviewPage.loggedUserRole.getText();
        Assert.assertEquals(userRole.trim(), "CUSTOMER");

        String addAccountButtonText = interviewPage.addAccountButton.getText();
        Assert.assertEquals(addAccountButtonText.trim(), "Add Account");

        String makePaymentButtonText = interviewPage.makePaymentButton.getText();
        Assert.assertEquals(makePaymentButtonText.trim(), "Make Payment");

        interviewPage.topMenuSearchInput.click();
        interviewPage.topMenuSearchInput.sendKeys("Testing Healenium");

        List<String> cardTypes = interviewPage.cardTypeLabel.stream().map(WebElement::getText).toList();
        Assert.assertTrue(cardTypes.containsAll(Arrays.asList("Credit cards", "Debit cards")));
    }
}
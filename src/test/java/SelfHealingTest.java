

import com.aventstack.extentreports.Status;
import com.epam.healenium.SelfHealingDriver;

import org.testng.annotations.Test;
import org.modal.BaseTest;
import org.modal.HealingCollector;
import org.modal.HealingLogAppender;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.pages.HomePage;
import org.testng.annotations.BeforeMethod;


import java.time.Duration;

public class SelfHealingTest extends BaseTest {

    @Test
    public void testWithHealenium(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Recommended for CI
options.addArguments("--disable-dev-shm-usage"); // Recommended for Docker
options.addArguments("--disable-gpu"); // Optional, safer for CI
options.addArguments("--window-size=1920,1080"); // Optional but helps
        WebDriver delegate = new ChromeDriver(options);
        SelfHealingDriver driver = SelfHealingDriver.create((WebDriver) delegate);

        driver.get("file:///C:/Users/Planit/Downloads/FormApp2.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        HomePage homePage = new HomePage(driver);
        homePage.enterName("ajay");
        homePage.enterEmail("ajay9999@gmail.com");
//        homePage.enterMobile("2343456712");
//        homePage.selectGender("male");
        homePage.selectState("Telangana");
        homePage.enterMessage("this is my valid data");
        homePage.clickSubmit();
    }
   @Test
  
    public void testWithHealenium1(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Recommended for CI
options.addArguments("--disable-dev-shm-usage"); // Recommended for Docker
options.addArguments("--disable-gpu"); // Optional, safer for CI
options.addArguments("--window-size=1920,1080"); // Optional but helps
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();
        HealingLogAppender.setTestName(testName);
        WebDriver delegate = new ChromeDriver(options);
        SelfHealingDriver driver = SelfHealingDriver.create((WebDriver) delegate);
        driver.get("C:\\Users\\Planit\\Downloads\\FormApp2%20-%20newversion.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        HomePage homePage= new HomePage(driver);
        homePage.enterName("ajay");
        homePage.enterEmail("ajay9999@gmail.com");
//        homePage.enterMobile("2343456712");
//        homePage.selectGender("male");
        homePage.selectState("Telangana");
        homePage.enterMessage("this is my valid data");
        homePage.clickSubmit();
        HealingCollector.getInstance().saveToJson("healing.json");
        driver.quit();
//        extent.flush();

    }

//    @Test
//    public void TestWithoutHealenium(){
//        CreateWebDriver.getINSTANCE().setDriver("chrome");
//        WebDriver driver=CreateWebDriver.getINSTANCE().getDriver();
//        String testName = new Object() {}.getClass().getEnclosingMethod().getName();
//
//
//        HealingLogAppender.setTestName(testName);
//
//        driver.get("C:\\Users\\Planit\\Downloads\\FormApp2.html");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        HomePage homePage= new HomePage(driver);
//        homePage.enterName("ajay");
//        homePage.enterEmail("ajay9999@gmail.com");
//        homePage.enterMobile("2343456712");
//        homePage.selectGender("male");
//        homePage.selectState("Telangana");
//        homePage.enterMessage("this is my valid data");
//        homePage.checkSubscribeBox();
//        homePage.clickSubmit();
//        HealingCollector.getInstance().saveToJson("healing1.json");
//        driver.quit();
//
//    }
}

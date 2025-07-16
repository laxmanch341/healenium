import com.epam.healenium.SelfHealingDriver;
import org.driverFactory.CreateWebDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.modal.HealingCollector;
import org.modal.HealingLogAppender;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.pages.HomePage;
import java.time.Duration;

public class TestRunner {
    @AfterEach
    public void tearDown() {
        HealingLogAppender.clearTestContext();
    }
    @Test
    public void TestWithHealenium(){

        ChromeOptions options = new ChromeOptions();
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();

        HealingLogAppender.setTestName(testName);

        WebDriver delegate = new ChromeDriver(options);
        SelfHealingDriver driver = SelfHealingDriver.create((WebDriver) delegate);
        driver.get("C:\\Users\\Planit\\Downloads\\FormApp2.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        HomePage homePage= new HomePage(driver);
        homePage.enterName("ajay");
        homePage.enterEmail("ajay9999@gmail.com");
        homePage.enterMobile("2343456712");
        homePage.selectGender("male");
        homePage.selectState("Telangana");
        homePage.enterMessage("this is my valid data");
        homePage.clickSubmit();
        HealingCollector.getInstance().saveToJson("healing.json");
        driver.quit();

    }
    @Test
    public void TestWithHealenium1(){

        ChromeOptions options = new ChromeOptions();
        String testName = new Object() {}.getClass().getEnclosingMethod().getName();
        HealingLogAppender.setTestName(testName);
        WebDriver delegate = new ChromeDriver(options);
        SelfHealingDriver driver = SelfHealingDriver.create((WebDriver) delegate);
        driver.get("C:\\Users\\Planit\\Downloads\\FormApp2.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        HomePage homePage= new HomePage(driver);
        homePage.enterName("ajay");
        homePage.enterEmail("ajay9999@gmail.com");
        homePage.enterMobile("2343456712");
        homePage.selectGender("male");
        homePage.selectState("Telangana");
        homePage.enterMessage("this is my valid data");
        homePage.clickSubmit();
       // HealingCollector.getInstance().saveToJson("healing.json");
        driver.quit();

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

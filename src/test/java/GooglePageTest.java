import org.testng.annotations.Test;
import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;

public class GooglePageTest {
@Test
    public static void main (String []args) throws MalformedURLException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origin=*");
        WebDriver delegate = new ChromeDriver(options);
        SelfHealingDriver driver = SelfHealingDriver.create((WebDriver) delegate);
        driver.get("https://www.google.com");
        driver.findElement(By.xpath("//*[@class='gLFyf']")).sendKeys("Hello");
        driver.findElement(By.xpath("//a[text()='About']")).click();
        driver.quit();

    }
}

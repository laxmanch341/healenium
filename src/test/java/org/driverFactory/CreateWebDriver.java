package org.driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateWebDriver {

    private WebDriver driver;

    private static CreateWebDriver INSTANCE;

    private CreateWebDriver() {
    }

    public static CreateWebDriver getINSTANCE() {
       if (INSTANCE ==null){
           INSTANCE = new CreateWebDriver();
       }
       return INSTANCE;
    }

    public void setDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid Browser");
        }

    }
    public WebDriver getDriver(){
        return driver;
    }
}

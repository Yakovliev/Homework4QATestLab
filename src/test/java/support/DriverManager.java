package support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.EventHandler;

import java.util.concurrent.TimeUnit;

public class DriverManager {

    protected EventFiringWebDriver driver;
    protected GeneralActions actions;

    private WebDriver getDriver(String browser) {

        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver",
                        System.getProperty("user.dir") + "//resourses//geckodriver.exe");
                return new FirefoxDriver();
            case "edge":
            case "microsoft edge":
                System.setProperty("webdriver.edge.driver",
                        System.getProperty("user.dir") + "//resourses//MicrosoftWebDriver.exe");
                return new EdgeDriver();
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//resourses//chromedriver.exe");
                return new ChromeDriver();
        }
    }

    @Parameters({"browser"})
    @BeforeClass
    public void setConfiguredDriver(String browser) {

        driver = new EventFiringWebDriver(getDriver(browser));
        driver.register(new EventHandler());

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        actions = new GeneralActions(driver);
    }

/*
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
*/
}

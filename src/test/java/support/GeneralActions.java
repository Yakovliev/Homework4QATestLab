package support;

import model.ProductData;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import java.time.Duration;
import java.util.List;

public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openAdminLoginForm() {
        driver.get(Properties.getBaseAdminUrl());
    }

    public void goToShop() {
        driver.navigate().to(Properties.getBaseUrl());
    }

    public void goToAllProducts() {
        waitForContentLoad();

        WebElement allProductsLink = driver.findElements(By.cssSelector("a.all-product-link.pull-xs-left.pull-md-right.h4"))
                .get(0);

        allProductsLink.click();
    }

    public WebElement checkProduct(ProductData product) {

        while (true) {
            try {
                waitForContentLoad();

                WebElement fElement = driver.findElement(By.linkText(product.getName()));
                Assert.assertEquals(fElement.getText(), product.getName());
                return fElement;

            }
            catch (Exception  e) {

                try {
                    WebElement nextButton = driver.findElements(By.cssSelector("a.next.js-search-link")).get(0);
                    nextButton.click();
                }
                catch (Exception e2) {
                    break;
                }
            }
        }

        return null;
    }

    public void goToProduct(ProductData product) {
        waitForContentLoad();

        WebElement productLink = checkProduct(product);

        if (productLink != null)
            productLink.click();
        else
            throw new IllegalArgumentException("Something went wrong with the link of the product");
    }

    public void checkParameters(ProductData product) {

        waitForContentLoad();

        Assert.assertEquals(driver.getTitle(), product.getName());

        WebElement qty = driver.findElement(By.cssSelector("div.product-quantities > span"));

        String[] qtyString = qty.getText().split(" ");

        Assert.assertEquals(qtyString[0], product.getQty().toString());

        WebElement price = driver.findElement(By.cssSelector("div.current-price > span"));

        Assert.assertEquals(price.getAttribute("content"), product.getPrice().replaceAll(",", "\\."));

    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {

        waitForContentLoad();

        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("passwd"));

        emailInput.sendKeys(login);
        passwordInput.sendKeys(password);

        passwordInput.submit();
    }

    public void goToProducts() {

        waitForContentLoad();

        WebElement catalog = driver.findElement(By.id("subtab-AdminCatalog"));
        WebElement adminProducts = driver.findElement(By.id("subtab-AdminProducts"));

        Actions actions = new Actions(driver);
        actions.moveToElement(catalog).pause(Duration.ofSeconds(1)).moveToElement(adminProducts)
                .pause(Duration.ofSeconds(1)).click(adminProducts).build().perform();
    }

    public void createProduct(ProductData newProduct) {

        waitForContentLoad();

        WebElement newProductButton = driver.findElement(By.id("page-header-desc-configuration-add"));
        newProductButton.click();

        waitForContentLoad();

        WebElement productName = driver.findElement(By.id("form_step1_name_1"));
        productName.sendKeys(newProduct.getName());


        WebElement quantity = driver.findElement(By.id("form_step1_qty_0_shortcut"));

        quantity.sendKeys(Keys.BACK_SPACE);
        quantity.sendKeys(newProduct.getQty().toString());



        WebElement price = driver.findElement(By.id("form_step1_price_shortcut"));

        while (!price.getAttribute("value").equals("")) {
            price.sendKeys(Keys.BACK_SPACE);
        }
        price.sendKeys(newProduct.getPrice());


        WebElement switchInput = driver.findElement(By.className("switch-input "));
        switchInput.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
        WebElement growlCloseAfterSwitch = driver.findElement(By.className("growl-close"));
        //growlClose.click();

        Actions actions = new Actions(driver);
        actions.moveToElement(growlCloseAfterSwitch).pause(Duration.ofSeconds(1))
                .click(growlCloseAfterSwitch).build().perform();

        actions.pause(Duration.ofSeconds(1)).build().perform();

        WebElement saveButton = driver.findElement(By.id("submit"));
        saveButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));

        WebElement growlCloseAfterSaveButtonClick = driver.findElement(By.className("growl-close"));
        actions.moveToElement(growlCloseAfterSaveButtonClick).pause(Duration.ofSeconds(1))
                .click(growlCloseAfterSaveButtonClick).build().perform();
    }

    public void waitForContentLoad() {

        ExpectedCondition<Boolean> pageLoadCondition = driver1 -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
        wait.until(pageLoadCondition);
    }
}

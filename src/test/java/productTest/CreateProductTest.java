package productTest;

import model.ProductData;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import support.DriverManager;
import support.Properties;

public class CreateProductTest extends DriverManager {

    public WebDriver driver;

    private ProductData product = ProductData.generate();

    @Test(dataProvider = "getAuthorizationData", dataProviderClass = support.AuthorizationData.class)
    public void createNewProduct(String login, String password) {

        actions.openAdminLoginForm();
        actions.login(login, password);

        actions.goToProducts();

        actions.createProduct(product);

    }

    @Test(dependsOnMethods = {"createNewProduct"})
    public void checkProductVisibility() {
        actions.goToShop();

        actions.goToAllProducts();

        actions.goToProduct(product);

        actions.checkParameters(product);
    }
}

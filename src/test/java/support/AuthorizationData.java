package support;

import org.testng.annotations.DataProvider;

public class AuthorizationData {

    @DataProvider
    public static Object[][] getAuthorizationData() {
        return new Object[][] {
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };

    }
}

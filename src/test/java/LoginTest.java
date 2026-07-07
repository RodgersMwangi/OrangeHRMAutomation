import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private String username=configReader.getProperty("admin.username");
    private String password=configReader.getProperty("admin.password");

    @Test
    public void loginWithValidCredentials(){
        loginPage=new LoginPage(page);
        loginPage.login(username, password);
        assertThat(page.locator("//h6[text()='Dashboard']")).isVisible();
    }

    @Test
    public void loginWithInvalidCredentials(){
        loginPage=new LoginPage(page);
        loginPage.login(username,"password");
        assertThat(page.getByText("Invalid Credentials"));
    }
}

import base.BaseTest;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.LoginPage;
import pages.PIMPage;
import util.DataFaker;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AdminFeatureTest extends BaseTest {
    private LoginPage loginPage;
    private String username=configReader.getProperty("admin.username");
    private String password=configReader.getProperty("admin.password");
    private PIMPage pimPage;
    private AdminPage adminPage;
    private String firstName= DataFaker.firstName;
    private String lastName=DataFaker.lastName;
    private String id=DataFaker.id;
    private String name=firstName+" "+lastName;
    private String adminUserName=firstName+lastName;
    private String adminPassword=DataFaker.userPassword;


    @Test
    public void addAdmin(){
        Locator btnAdmin=page.locator("//span[text()='PIM']");
        Locator btnAdd=page.locator("//button[text()=' Add ']");
        loginPage=new LoginPage(page);
        loginPage.login(username, password);
        assertThat(page.locator("//h6[text()='Dashboard']")).isVisible();
        pimPage=new PIMPage(page);
        pimPage.addEmployee(firstName,lastName,id);
        assertThat(page.getByText(firstName)).isVisible();
        adminPage=new AdminPage(page);
        adminPage.addEmployee(name, firstName, adminUserName, adminPassword);
        assertThat(page.locator("//h5[normalize-space()='System Users']")).isVisible();

        //page.pause();
    }
}

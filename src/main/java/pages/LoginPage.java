package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private final Locator txtUsername;
    private final Locator txtPassword;
    private final Locator btnSubmit;

    public LoginPage(Page page){
        super(page);
        this.txtUsername=page.locator("input[name='username']");
        this.txtPassword=page.locator("input[name='password']");
        this.btnSubmit=page.locator("button[type='submit']");
    }

    public void login(String username, String password){
        txtUsername.fill(username);
        txtPassword.fill(password);
        btnSubmit.click();
    }
}

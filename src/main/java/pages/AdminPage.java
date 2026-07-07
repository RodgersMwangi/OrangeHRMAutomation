package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdminPage extends BasePage {
    private final Locator btnAdmin;
    private final Locator btnAddAdmin;
    private final Locator btnDropDown;
    private final Locator adminDropDownbtn;
    private final Locator txtEmployeeName;
    private final Locator statusDropDown;
    private final Locator btnstatusDropDown;
    private final Locator txtUsername;
    private final Locator txtPassword;
    private final Locator txtConfirmPass;
    private final Locator btnSave;

    public AdminPage(Page page){
        super(page);
        this.btnAdmin=page.locator("//span[text()='Admin']");
        this.btnAddAdmin=page.locator("//button[normalize-space()='Add']");
        this.btnDropDown=page.locator("//label[normalize-space()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text-input')]");
        this.adminDropDownbtn=page.locator("//label[normalize-space()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Admin']");
        this.txtEmployeeName=page.locator("input[placeholder*='hints']");
        this.statusDropDown=page.locator("//span[normalize-space()='Enabled']/ancestor::div[contains(@class, 'oxd-select-option')]");
        this.btnstatusDropDown=page.locator("//label[normalize-space()='Status']/ancestor::div[contains(@class, 'oxd-input-group')]//div[contains(@class,'oxd-select-text-input')]");
        this.txtUsername=page.locator("//label[normalize-space()='Username']/ancestor::div[ contains(@class,'oxd-input-group')]//input[contains(@class,'oxd-input')]");
        this.txtPassword=page.locator("//label[normalize-space()='Password']/ancestor::div[ contains(@class,'oxd-input-group')]//input[contains(@class,'oxd-input')]");
        this.txtConfirmPass=page.locator("//label[normalize-space()='Confirm Password']/ancestor::div[ contains(@class,'oxd-input-group')]//input[contains(@class,'oxd-input')]");
        this.btnSave=page.locator("//button[normalize-space()='Save']");
    }

    public void addEmployee(String employeeName, String hint, String username, String password){
        btnAdmin.click();
        btnAddAdmin.click();
        btnDropDown.click();
        adminDropDownbtn.click();
        txtEmployeeName.fill(hint);
        page.getByText(employeeName).click();
        btnstatusDropDown.click();
        statusDropDown.click();
        txtUsername.fill(username);
        txtPassword.fill(password);
        txtConfirmPass.fill(password);
        btnSave.click();
    }
}

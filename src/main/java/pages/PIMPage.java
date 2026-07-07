package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PIMPage extends BasePage {
    private final Locator btnPIM;
    private final Locator btnAdd;
    private final Locator txtFirstName;
    private final Locator txtLastName;
    private final Locator txtId;
    private final Locator btnSave;


    public PIMPage(Page page){
        super(page);
        this.btnPIM=page.locator("//span[text()='PIM']");
        this.btnAdd=page.locator("//button[text()=' Add ']");
        this.txtFirstName=page.locator("input[name='firstName']");
        this.txtLastName=page.locator("input[name='lastName']");
        this.txtId=page.locator("//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");
        this.btnSave=page.locator("button[type='submit']");


    }

    public void addEmployee(String firstName, String lastName, String id){
        btnPIM.click();
        btnAdd.click();
        txtFirstName.fill(firstName);
        txtLastName.fill(lastName);
        txtId.fill(id);
        btnSave.click();


    }


}

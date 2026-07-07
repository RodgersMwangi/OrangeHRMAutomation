package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
    private Playwright playwright;
    private Browser browser;

    public Browser createBrowser(){
        playwright=Playwright.create();
        return browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    public void closeBrowser(){
        if(browser!=null){
            browser.close();
        }
        if(playwright!=null){
            playwright.close();
        }
    }
}

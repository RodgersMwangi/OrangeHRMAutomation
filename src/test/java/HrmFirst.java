import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.WaitUntilState;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;


public class HrmFirst {
    private static ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private final String url="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private final String username="Admin";
    private final String password="admin123";

    // 2. Wrap the session-specific variables in ThreadLocal containers
    private static ThreadLocal<BrowserContext> contextThread=new ThreadLocal<>();
    private static ThreadLocal<Page> pageThread = new ThreadLocal<>();


    @BeforeMethod
    public void setUp(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //context-Create a fresh, isolated context (like an incognito window) for each data row esp dataprovider
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        // 4. Park them safely in this specific thread's locker
        playwrightThread.set(playwright);
        browserThread.set(browser);
        contextThread.set(context);
        pageThread.set(page);

        page.setDefaultNavigationTimeout(60000);
        page.setDefaultTimeout(60000);
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);
    }

    @Test
    public void loginPageValidator(){
        // 5. Use .get() whenever you want to interact with this thread's specific Page instance
        pageThread.get().navigate(url);
        String title=pageThread.get().title();
        System.out.println("The page title is "+title);
        assertTrue(title.contains("OrangeHRM"), "Expected title to contain OrangeHRM but got "+title);
    }

    @Test
    public void login(){
        pageThread.get().navigate(url);
        pageThread.get().fill("input[name='username']", username);
        pageThread.get().locator("input[name='password']").fill(password);
        pageThread.get().locator("button[type='submit']").click();
        assertThat(pageThread.get()).hasURL(Pattern.compile(".*dashboard*."));
        System.out.println(pageThread.get().url());
    }

    @Test
    public void wrongCredentialsLogin(){
        pageThread.get().navigate(url);
        pageThread.get().fill("input[name='username']", "adm");
        pageThread.get().locator("input[name='password']").fill("newPass");
        pageThread.get().locator("button[type='submit']").click();
        assertThat(pageThread.get().getByText("Invalid credentials")).isVisible();
    }

    @Test(dataProvider = "LoginData")
    public void emptyFieldsLogin(String admin, String pass){
        pageThread.get().navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        //This option -DOM...- tells Playwright to stop waiting for tracking pixels or heavy footer images to load, which completely sidesteps the 30-second
        //it's best to use it only for methods where a new page is not being loaded or you don't care like testing wrong credentials
        pageThread.get().fill("input[name='username']",admin);
        pageThread.get().fill("input[name='password']", pass);
        pageThread.get().locator("button[type='submit']").click();
        //assertThat(page.getByText("Required")).isVisible();
        if(admin.isEmpty()){
            assertThat(pageThread.get().locator(".oxd-input-group")
                    .filter(new Locator.FilterOptions()
                            .setHasText("Username")).getByText("Required")).isVisible();
        }
        if(pass.isEmpty()){
            assertThat(pageThread.get().locator(".oxd-input-group")
                    .filter(new Locator.FilterOptions()
                            .setHasText("Password")).getByText("Required")).isVisible();
        }
    }

    @DataProvider(name="LoginData", parallel = true)
    public Object[][] getLoginData(){
        return new Object[][]{
                {"", "password"},
                {"Admin", ""},
                {"",""}
        };
    }

    @AfterMethod
    public void teardown(){
        if (contextThread.get() != null) {
            contextThread.get().close();
        }
        if (browserThread.get() != null) {
            browserThread.get().close();
        }
        if (playwrightThread.get() != null) {
            playwrightThread.get().close();
        }
        // 5. Hard wipe memory maps to prevent thread pollution
        playwrightThread.remove();
        browserThread.remove();
        contextThread.remove();
        pageThread.remove();
    }



}

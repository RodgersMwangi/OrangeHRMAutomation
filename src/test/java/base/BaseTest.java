package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import util.ConfigReader;
import util.ExtentManager;
import java.lang.reflect.Method;
import java.util.Base64;

public class BaseTest {
    protected Browser browser;
    protected BrowserFactory browserFactory;
    protected Page page;
    protected ConfigReader configReader=ConfigReader.getInstance();

    private static ExtentReports extent;

    @BeforeSuite
    public void setupReport(){
        extent= ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setUp(Method method){
        ExtentTest test= extent.createTest(method.getName());
        ExtentManager.setTest(test);
        /*
        It grabs the test method name (e.g., verifyLogin) using reflection,
        tells Extent to start a brand-new test log entry with that name,
        and drops that tracker into ThreadLocal storage (ExtentManager.setTest(test))
         */

        browserFactory=new BrowserFactory();
        browser=browserFactory.createBrowser();
        page=browser.newPage();
        page.navigate(configReader.getProperty("orangeHrm.url"));
        page.setDefaultTimeout(60000);
        page.setDefaultNavigationTimeout(60000);
        PlaywrightAssertions.setDefaultAssertionTimeout(60000);
    }

    @AfterMethod
    public void teardown(ITestResult result){
        ExtentTest test = ExtentManager.getTest();
        if (result.getStatus() == ITestResult.FAILURE){
            test.fail("Test Failed: " + result.getThrowable().getMessage());
            // Capture screenshot on failure and attach to report
            try {
                byte[] buffer = page.screenshot(new Page.ScreenshotOptions().setFullPage(true)); //capture full page screenshot
                String base64Screenshot = Base64.getEncoder().encodeToString(buffer); //encodes that image as a Base64 text string
                /*
                usually, the image is saved locally and accessed through its path, but this only works locally.
                with base64, the image literal becomes a piece of text that can be embedded directly inside the HTML file's code itself:
                That means you can easily share the entire html and it will render correctly on any device.
                 */
                test.fail("Failure Screenshot",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            } catch (Exception e) {
                test.warning("Failed to attach screenshot: " + e.getMessage());
            }
        }else if(result.getStatus()==ITestResult.SKIP){
            test.skip("Test skipped");
        }else if(result.getStatus()==ITestResult.SUCCESS){
            test.pass("Tests passed successfully");
        }
        browserFactory.closeBrowser();
        ExtentManager.removeTest();
        //wipe the thread-local tracking data clean for the next potential execution thread.

    }

    @AfterSuite
    public void flushReport() {
        if (extent != null) {
            extent.flush(); // This generates the actual HTML file
        }
    }
}

package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /*
    synchronized ensures that whenever two tests try to initialize the report engine
    at the same time, they are forced to wait in line
     */
    public static synchronized ExtentReports getInstance(){
        if(extent==null){
            //set report file path
            //spartreporter handles the html file creation
            ExtentSparkReporter sparkReporter=new ExtentSparkReporter("target/ExtentReport.html");

            //customize report view
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Playwright Automation Report");
            sparkReporter.config().setReportName("Regression Suite Results");

            extent=new ExtentReports();
            extent.attachReporter(sparkReporter);

            //Add environment metadata
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Tool", "Playwright Java");
        }
        return extent;
    }

    //fetch the extentTest instance belonging to the exact thread that called it.
    public static ExtentTest getTest(){
        return extentTest.get();
    }
    //assign the newly created extentTest object to the current thread executing it
    public static void setTest(ExtentTest test){
        extentTest.set(test);
    }
    //clean the ExtentTest reference from the current thread storage
    public static void removeTest(){
        extentTest.remove();
    }
}

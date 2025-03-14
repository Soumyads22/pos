package generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {
	
	public static final String CONFIG_PATH = "./config.properties";
	public static ExtentReports extent;
	public WebDriver driver;
	public WebDriverWait wait;
	public ExtentTest test;
	public static final String XL_PATH= "./data/input.xlsx";
	
	
	
	
	@BeforeSuite
	public void initReport()
	{
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
		extent.attachReporter(spark);
		
	}
	
	@AfterSuite
	public void generateReport()
	{
		extent.flush();
	}
	
	@Parameters("property")
	@BeforeMethod
	public void preCondition(Method method, @Optional(CONFIG_PATH) String property) throws MalformedURLException
	{
		String testName = method.getName();
		test = extent.createTest(testName);
		
		String grid = Utility.getProperty(property, "GRID");
		String grid_url = Utility.getProperty(property, "GRID_URL");
		String browser = Utility.getProperty(property, "BROWSER");
		String appURL =Utility.getProperty(property, "APP_URL");
		String ITO = Utility.getProperty(property, "ITO");
		String ETO = Utility.getProperty(property, "ETO");
	
		
		if(grid.equalsIgnoreCase("yes"))
		{
			
			if(browser.equalsIgnoreCase("chrome"))
			{
			test.info("open chrome browser in Remote system");	
			driver= new RemoteWebDriver(new URL(grid_url), new ChromeOptions());
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				test.info("open firefox browser in Remote system");	
				driver= new RemoteWebDriver(new URL(grid_url), new FirefoxOptions());
			}
			else
			{
				test.info("open edge browser in Remote system");	
				driver= new RemoteWebDriver(new URL(grid_url), new EdgeOptions());
			}
		}
		else
		{
			if(browser.equalsIgnoreCase("chrome"))
			{
			test.info("open chrome browser in local system");	
			driver= new ChromeDriver();
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				test.info("open firefox browser in local system");	
				driver= new FirefoxDriver();
			}
			else
			{
				test.info("open chrome browser in local system");	
				driver= new EdgeDriver();
			}
		}
		
		
		driver.get(appURL);
		test.info("Enter the UR:"  + appURL );
		
		driver.manage().window().maximize();
		test.info("maximize the browser");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ITO)));
		test.info("implicit wait:"+ITO);
		
		wait= new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(ETO)));
		test.info("explicit wait:"+ETO);
		
		
	}
	

	@AfterMethod
	public void postCondition(ITestResult testResult) throws IOException
	{
		String testname = testResult.getName();
		int status = testResult.getStatus();
		if(status==1)
		{
			test.pass("the test is Passed");
			
		}
		else
		{ 
			String timeStamp = Utility.getTimeStamp();			
			Utility.takeScreenshot(driver, "target/"+testname+timeStamp+".png");
			test.fail("the test is failed",MediaEntityBuilder.createScreenCaptureFromPath(testname+timeStamp+".png").build());
		}
		test.info("close the browser" + status);
		driver.quit();
	}
}

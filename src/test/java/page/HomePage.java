package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class HomePage 

{
@FindBy(xpath="//h3[starts-with(text(), 'Welcome to OSPOS')]")
private WebElement welcomeMessage;

public HomePage(WebDriver driver)
{
	PageFactory.initElements(driver, this);
}

public boolean verifyHomePageIsDisplayed(WebDriverWait wait)
{
	try
	{
		wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
		Reporter.log("home page is displayed, true");
		return true;
	}
    catch (Exception e)
	{
    	Reporter.log("home page is  not displayed, true");
    	return false;
	}
	
}
}

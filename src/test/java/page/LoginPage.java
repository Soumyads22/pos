package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 

{
	@FindBy(id="input-username")
	private WebElement unTR;
	
	@FindBy(id="input-password")
	private WebElement pwTR;
	
	@FindBy(name="login-button")
	private WebElement goBTN;
	
	public LoginPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
		
	public void setUserName(String un)
	{
		unTR.sendKeys(un);
	}

	public void setPassword(String password)
	{
		pwTR.sendKeys(password);
	}
	
	public void clickgoButton()
	{
		goBTN.click();;
	}
}

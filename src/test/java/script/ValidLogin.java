package script;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Utility;
import page.HomePage;
import page.LoginPage;

public class ValidLogin extends BaseTest 

{
	@Test(priority=1)
	public void testValidLogin()
	{
		//Get Test data
		String un = Utility.getXLCellData(XL_PATH, "ValidLogin", 1,0);
		String pw = Utility.getXLCellData(XL_PATH, "ValidLogin", 1,1);
		test.info("enter valid username :" + un);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName(un);
		
		test.info("enter the valid password :" + pw);
		loginPage.setPassword(pw);
		
		
		loginPage.clickgoButton();
		test.info("click on login button");
		
		
		HomePage homePage = new HomePage(driver);
		boolean result = homePage.verifyHomePageIsDisplayed(wait);
		Assert.assertTrue(result);
		test.info("home page should be displayed ");
	}

}

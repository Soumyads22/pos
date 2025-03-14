package script;

import org.jspecify.annotations.Nullable;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;

public class Demo3 extends BaseTest {
	
	@Test
	public void testC()
	{
		
		String title = driver.getTitle();
        test.info("testC" +title);
		
		Reporter.log("testC   ",true);
		Assert.fail();
		
	}

}

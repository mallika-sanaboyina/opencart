package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;
import testBase.BaseClass;

public class TC_002_LoginTest  extends BaseClass{

	@Test(groups= {"sanity","master"})   //master is all test cases 
	public void verify_Login() {
	  logger.info("****Starting TC_002 Login test****");
	   logger.debug("capturing application debug logs");
	  try   //just to handle exception keeping try block
	  {
		  
	   //Homepage
	   HomePage hp=new HomePage(driver);
   	  Thread.sleep(4000);
       hp.clickMyAccount();
    Thread.sleep(3000);

	   logger.info("Clicked on my account");
	   hp.clickLogin(); //login from homepage(my account)
	   logger.info("Clicked on login");

       //Login page
	   LoginPage lp=new LoginPage(driver);
	   logger.info("entering valid email and password");
	   lp.setEmail(p.getProperty("email"));
	   lp.setPassWord(p.getProperty("password"));
       lp.clickLogin();//login button
	   logger.info("clicked on login button");
	 

	   //my account page verifying
	   MyAccount ma=new MyAccount(driver);
	   boolean targetpage=ma.isMyAccountPageExists();
	   if(targetpage==true) {
		   logger.info("login test passed");
		   Assert.assertTrue(true);
	   }
	   
	   else
	   {
		   Assert.fail();
		   logger.info("login test failed");

	   }
	  }
	catch(Exception e) {
		Assert.fail();
	}
	  
	   logger.info("Finished TC_002 Login test ");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

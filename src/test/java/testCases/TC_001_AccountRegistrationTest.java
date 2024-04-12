package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass {
  
    @Test(groups= {"regression","master"})
    public void verify_account_registration() throws InterruptedException  {
    	//first need to deal with home page as we go to acnt reg form
    	//homepage is exists i n other package and current test case from other package so creating package and need to import
    	logger.info("*********TC_001_AccountRegistrationTest starting *************");
    	logger.debug("**********debug logs*******");
    	try {
    	
    	HomePage hp=new HomePage(driver);
    
    	hp.clickMyAccount();
    	logger.info("clicked on MyAccount");
    	hp.clickRegister();
    	logger.info("clicked on Registration");

    	//Thread.sleep(6000);
    	logger.info("Entering customer details.. ");
    	AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
    	//as the reg data cannot be use twice,we can generate text using randomly method as below and generates text randomly at execution(in jjava we have such type of methods)
        //	regpage.setFirstname("abc); 
    	regpage.setFirstname(randomestring().toUpperCase());
    	regpage.setLastname(randomestring().toUpperCase());
    	regpage.setEmail(randomestring()+"@gmail.com"); //randomly generated text
    	regpage.setTelephone(randomenumber());
    	//regpage.setPassword(randomAlphaNumeric());
    	//Thread.sleep(3000);

    	//if we have set and confirm pwd fields on page then
     String password=randomAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password); 
    	regpage.setPrivacyPolicy();
		regpage.clickContinue();
		logger.info("clicked on continue...."); 
		
		
    	String confmsg=regpage.getConfirmationMsg();
    	logger.info("Validating expected message..");
    	Assert.assertEquals(confmsg, "Your Account Has Been Created!");
    	}
    	/*if(confmsg.equals(Your Account Has Been Created!!!!)
    	{
    		logger.info("test passed");
    		Assert.assertTrue(true);
    	}
    	else 
    	{
    		logger.error("failed");
    		Assert.fail();
    		
    	} */
    	catch(Exception e)
    	{
    		logger.error("test failed");
    		Assert.fail();
    	}
    	logger.info("**** finished TC_001_AccountRegistrationTest  *****");
    
    }
}
//if yu need debug level logs,you can mention  Root level="debug">
	
	
	
	
	


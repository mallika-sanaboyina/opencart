package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_003_LoginDDT extends BaseClass{

	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class) 
	//if dp is part of same class then no need identfy class but here dp is from other class so need to provider name here
	public void verify_loginDDT(String email,String password,String exp) 
	{
		logger.info("..........Starting TC_003_LoginDDT ......");
		 try {
		HomePage hp=new HomePage(driver);
	       hp.clickMyAccount();
		   hp.clickLogin(); //login from homepage(my account)

	       //Login page
		   LoginPage lp=new LoginPage(driver);
		   lp.setEmail(email);  //as we do get data from excel we passing variables
		   lp.setPassWord(password);
	       lp.clickLogin();//login button
	       //myaccount page
	       MyAccount ma=new MyAccount(driver);
		   boolean targetpage=ma.isMyAccountPageExists();
           if(exp.equalsIgnoreCase("Valid")) {   //if my data is valid and target page is also true then passes
        	   if(targetpage==true) {
        		   ma.clickLogout();//as we need to take another input,need to logout
        		   Assert.assertTrue(true);
        		    }
        	   else
        	   {
        		   Assert.assertTrue(false);//data is valid but target page is not true then false
        	   }
           }
           if(exp.equalsIgnoreCase("Invalid")) {  //when data is invalid 
        	   if(targetpage==true) { //but logged in successfully but is shouldnt
        		   ma.clickLogout();
        		   Assert.assertTrue(false); 
        	   }
        	   else {
        		   Assert.assertTrue(true); //as data is invalid, and targetpage is false so passes
        	   }
           }
		 }
		 catch(Exception e) {
			 Assert.fail();
		 }
		 logger.info("finished data driven testcase");
		
		
	}
	
	
	
}

package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; //log4j
import org.apache.logging.log4j.Logger;//log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
//if we want to reuse some methods in other test cases,then we can extend these to other class so creating base class 
	static public WebDriver driver;
	public Logger logger;  //spl class called logger class from apache log4j2 loading log4j2 file
    public Properties p;


	  @BeforeClass(groups= {"sanity","regression","master"})  //adding grpups for setup and teardown too aswe want to execute everytime for every group
	  @Parameters({"os","browser"})
		public void setup(String os,String br) throws InterruptedException, IOException {
		//loading properties file using p
		 FileReader file=new FileReader(".//src//test//resources//Config.properties");
         p=new Properties();
         p.load(file);
         
		 
		  //loading log4j2 xml file

		  logger= LogManager.getLogger(this.getClass());// this will get the current class name and that used to get logs and mentioned apenderref=file in logj2 file
		
		
		  if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		  		{
			  DesiredCapabilities capabilities=new DesiredCapabilities();
//os
			  if(os.equalsIgnoreCase("windows")) {
				  capabilities.setPlatform(Platform.WIN10);
				    }
			  else if(os.equalsIgnoreCase("mac")) {
				  capabilities.setPlatform(Platform.MAC);
			  }
			  else {
				  System.out.println("No matching os");
			  }
//broswer (we can use if also but trying switch)
			  switch(br.toLowerCase())
			  {
			  case "chrome":capabilities.setBrowserName("chrome");break;
			  case "edge":capabilities.setBrowserName("MicrosoftEdge");break;
               default: System.out.println("no matching browser"); return;
			  }
			
			  driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);;
			  
			}
		  else if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			//launching browser based on condition - locally
				switch(br.toLowerCase())
				{
				case "chrome": driver=new ChromeDriver(); break;
				case "edge": driver=new EdgeDriver(); break;
				default: System.out.println("No matching browser..");
							return;
				}
		  }
		  		
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  //launching broswer based on condition
		  switch(br.toLowerCase()) {
		 case "chrome":driver=new ChromeDriver(); 
		 break;
		 case "edge":driver=new EdgeDriver(); 
		 break;
		 default:System.out.println("No matching broswer"); //if driver is not matching then we cant do the actions 
		 return; 
		

		 }
			//driver=new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
			driver.get(p.getProperty("appURL"));
			//driver.get("https://demo.opencart.com/index.php?route=common/home&language=en-gb");
			driver.manage().window().maximize();
			Thread.sleep(5000);
		}
	    @AfterClass(groups= {"sanity","regression","master"})  //we need mention groups here too as it needs to teardown or any to excute @before after class i not enogh)
	    public void teardown() {
	    	driver.close();
	    }
	    public String randomestring() {
			String generatedstring=RandomStringUtils.randomAlphabetic(5);
			return generatedstring;
		}
		public String randomenumber() {
			String generatedstring=RandomStringUtils.randomNumeric(10);
			return generatedstring;
		}
		public String randomAlphaNumeric() {
			String str=RandomStringUtils.randomAlphabetic(3);
			String num=RandomStringUtils.randomNumeric(3);
			return (str+"@"+num);
		}
		
		public String captureScreen(String tname) throws IOException {

			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
					
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			
			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
			File targetFile=new File(targetFilePath);
			
			sourceFile.renameTo(targetFile);
				
			return targetFilePath;

		}

	    
	    
}

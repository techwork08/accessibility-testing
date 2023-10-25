package allyTest;

import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.deque.axe.AXE;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonAllyTest {
	
	static WebDriver driver;
	private static final URL url = AmazonAllyTest.class.getResource("/axe.min.js");
	
	@BeforeClass
	public static void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
	}
	
	@Test
	public void amazonAllyTest() {
		JSONObject jSonResponse = new AXE.Builder(driver, url).analyze(); 
		JSONArray violations = jSonResponse.getJSONArray("violations");
		
		if(violations.length() == 0) {
			System.out.println("No errors");
		}else {
			AXE.writeResults("AmazonAllyTest", jSonResponse);
			Assert.assertTrue(false,AXE.report(violations));
		}
			
	}
	@Test
	public void accessibilitytestingWithWebElement() {
		JSONObject jSonRespons	= new AXE.Builder(driver, url).analyze(driver.findElement(By.linkText("Customer Service")));
		JSONArray violations = jSonRespons.getJSONArray("violations");
		
		if(violations.length() == 0) {
			Assert.assertTrue(true,"No violations found");
		}else {
			AXE.writeResults("accessibilitytestingWithWebElement", jSonRespons);
			Assert.assertTrue(false,AXE.report(violations));
		}
		
	}
	@Test
	public void testAccessibilityWithSelector() {
		
	       JSONObject jsonResponse = new AXE.Builder(driver, url).include("title").include("p").analyze();
	       JSONArray violations  = jsonResponse.getJSONArray("violations");
	       
	       if(violations.length() == 0) {
	    	   assertTrue(true, "No violations found");
	       }else {
	    	   AXE.writeResults("testAccessibilityWithSelector", jsonResponse);
	    	   Assert.assertTrue(false, AXE.report(violations));
	       }
	          
	}
	@Test
	public void testAccessibilityWithIncludesAndExcludes() {
		
		JSONObject responseJSON = new AXE.Builder(driver, url)
				.include("body")
				.exclude("h1")
				.analyze();

		JSONArray violations = responseJSON.getJSONArray("violations");

		if (violations.length() == 0) {
			Assert.assertTrue(true, "No violations found");
		} else {
			AXE.writeResults("testAccessibilityWithIncludesAndExcludes", responseJSON);
			Assert.assertTrue(false, AXE.report(violations));
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
}

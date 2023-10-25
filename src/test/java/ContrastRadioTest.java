import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ContrastRadioTest {
	static WebDriver driver;

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.example.com");
		 WebElement textElement = driver.findElement(By.className("text"));
		 
		 String backgroundColor = textElement.getCssValue("background-color");
		 int[] backgroundColorRGB = getRGBValues(backgroundColor);
	
		 String foregroundColor = textElement.getCssValue("color");
		 int[] foregroundColorRGB = getRGBValues(foregroundColor);
	
		 
		 double contrastRatio = calculateContrastRatio(backgroundColorRGB, foregroundColorRGB);
		 
		 Assert.assertTrue(contrastRatio >= 4.5, "Contrast ratio for text and images of text is less than 4.5:1");
		 
		 if(textElement.getCssValue("font-size").equals("24px") && textElement.getCssValue("font-weight").equals("normal")) {
			  Assert.assertTrue(contrastRatio >= 3, "Contrast ratio for text and images of text larger than 24px and normal weight is less than 3:1");
			  }
			  else if(textElement.getCssValue("font-size").equals("19px") && textElement.getCssValue("font-weight").equals("bold")) {
			  Assert.assertTrue(contrastRatio >= 3, "Contrast ratio for text and images of text larger than 19px and bold weight is less than 3:1");
			  }
			 
			//  driver.quit();
			  }
	
	
	
	private static double calculateContrastRatio(int[] backgroundColorRGB, int[] foregroundColorRGB) {
		 double luminance1 = calculateLuminance(backgroundColorRGB);
		 double luminance2 = calculateLuminance(foregroundColorRGB);
		 double contrastRatio = (luminance1 + 0.05) / (luminance2 + 0.05);
		 return contrastRatio;
		 }
		

	private static double calculateLuminance(int[] rgbValues) {
		 for(int i=0; i<rgbValues.length; i++) {
		 rgbValues[i] = rgbValues[i] / 255;
		 if(rgbValues[i] <= 0.03928) {
		 rgbValues[i] = (int) (rgbValues[i] / 12.92);
		 }
		 else {
		 rgbValues[i] = (int) Math.pow(((rgbValues[i] + 0.055)/1.055), 2.4);
		 }
		 }
		  return (0.2126 * rgbValues[0]) + (0.7152 * rgbValues[1]) + (0.0722 * rgbValues[2]);
		 }
	
	private static int[] getRGBValues(String cssColorValue) {
		 int[] rgbValues = new int[3];
		 String[] commaSeparatedValues = cssColorValue.replaceAll("[a-zA-Z() ]", "").split(",");
		 for(int i=0; i<3; i++) {
		 rgbValues[i] = Integer.parseInt(commaSeparatedValues[i]);
		 }
		 return rgbValues;
		 }

//	public static void remoteClient() {
//		 //Assuming the remote client is a Sauce Labs instance
//		 DesiredCapabilities caps = DesiredCapabilities.chrome();
//		 caps.setCapability("username", "your_Sauce_Labs_username");
//		 caps.setCapability("accessKey", "your_Sauce_Labs_access_key");
//		 caps.setCapability("platform", "Windows 10");
//		 caps.setCapability("version", "latest");
//		 RemoteWebDriver driver = new RemoteWebDriver(new URL("https://ondemand.saucelabs.com/wd/hub"), caps);
//	}

}

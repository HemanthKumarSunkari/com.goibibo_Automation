
package com.goibibo.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.goibibo.util.TestUtil;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait;
	public static JavascriptExecutor js;

	/*
	 * constructor that intialises confiq.properties file
	 */
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\goibibo\\config\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * intializes the driver and browser
	 * navigate to given url
	 */
	@BeforeMethod
	public static void initialization() {
		TestUtil.log().info("Trying to launch the "+ prop.getProperty("browser")+" Driver");
		Browser.initialization(prop.getProperty("browser"));

		js = (JavascriptExecutor) driver;
		TestUtil.log().info("Intializing the JavaScript Executor");
		driver.manage().window().maximize();
		TestUtil.log().info("Maximizing the browser window");
		driver.manage().deleteAllCookies();
		TestUtil.log().info("Deleting all browser cookies");
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		TestUtil.log().info("Trying to navigate to :" + prop.getProperty("url"));
		driver.get(prop.getProperty("url"));
		TestUtil.log().info("Navigated to : " + prop.getProperty("url"));

	}
	
	

	/*
	 * quits and closes the browser after every test case
	 */
	@AfterMethod
	public static void tearDown() {
		if (driver != null) {
			TestUtil.log().info("Closing the browser window");
			driver.close();
			
			TestUtil.log().info("Closing the complete browser session");
			driver.quit();
			
		}
	}
	
	
	/*
	 * Explicit Wait for particular instance 
	 */
	public static WebElement Wait(WebElement e) {
		wait = new WebDriverWait(driver, TestUtil.EXPLICIT_WAIT);
		TestUtil.log().info("Driver is trying to find element :" + e);
		return wait.until(ExpectedConditions.visibilityOf(e));

	}
}

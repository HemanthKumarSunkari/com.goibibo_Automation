package com.goibibo.base;

import java.util.Arrays;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.goibibo.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser extends TestBase {

	/*
	 * method to initialize any browser using WebDriverMananger. 
	 * Browser param is
	 * obtained from config.properties file.
	 */

	public static void initialization(String browserName) {

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
			options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
			driver = new ChromeDriver(options);
			TestUtil.log().info("Chrome browser is launched");

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			TestUtil.log().info("Firefox browser is launched");
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			TestUtil.log().info("IE browser is launched");
		} else {
			driver = null;
			TestUtil.log().error("driver is null");

		}

	}
}

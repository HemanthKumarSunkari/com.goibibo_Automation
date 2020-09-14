package com.goibibo.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.goibibo.base.TestBase;
import com.goibibo.util.TestUtil;

public class SingleTripFlight extends TestBase {
	
	/*
	 * constructor that intialize "this" class with "driver"
	 */
	public SingleTripFlight() {
		PageFactory.initElements(driver, this);
	}
	
	/*
	 * locators 
	 */

	@FindBy(xpath = "//*[@id=\"PRICE\"]/span/i")
	WebElement price;

	@FindBy(xpath = "//span[contains(@class,'ico20') and contains(@class, 'quicks') and contains(@class, 'fb')]")
	List<WebElement> fares;

	@FindBy(xpath = "//div[contains(@data-cy, 'duration') and contains(@class, 'quicks')]")
	List<WebElement> duration;

	@FindBy(xpath = "//span[contains(@data-cy, 'depTime') and contains(@class, 'quicks')]")
	List<WebElement> departureTime;

	@FindBy(xpath = "//*[contains(@data-cy,'bookBtn')]")
	List<WebElement> bookBtn;

	
	/*
	 * Action methods
	 */
	int index = 0;
	
	/*
	 * method to change the list based on price
	 * Here it is checking whether it starts from least price or not 
	 * if not, changing the toggle  
	 */
	public void turnPriceToggleUp() {
		String clName = price.getAttribute("class");
		
		if (!clName.contains("icon-arrow2-up")) {
			TestUtil.log().info("Intially , Prices are starting from highest fare value");
			price.click();
			TestUtil.log().info("Now , Prices are going to start with lowest fare value");
		}
		
		TestUtil.log().info("Prices are started from least fare value");
	}

	
	/*
	 * method to find the cheapest flight fare
	 */
	public void findCheapFlightFare() {
		int len = fares.size();
		int min = 0, max = 0;
		min = Integer.parseInt(fares.get(0).getText().replaceAll(",", ""));
		
		for (int i = 1; i < len; i++) {
			max = Integer.parseInt(fares.get(i).getText().replaceAll(",", ""));
			
			if (max < min) {
				min = max;
				index = i;
			}
		}
		TestUtil.log().info("Cheapest fare of among all Flight = ₹"+  min);
	}

	/*
	 * method to shortest flight duration
	 */
	public void findShortestFlightDuration() {

		int len = duration.size();
		int maxD, minD = 0;
		minD = Integer.parseInt(duration.get(0).getText().replaceAll(" ", "").replaceAll("h", "").replaceAll("m", ""));
				
		for (int i = 1; i < len; i++) {
			maxD = Integer.parseInt(duration.get(i).getText().replaceAll(" ", "").replaceAll("h", "").replaceAll("m", ""));
			if (maxD < minD) {
				minD = maxD;
			}
		}
		
		int hour = minD/100;
		int minute = minD - (hour*100);
		
		TestUtil.log().info("Shortest duration among all flights = " + hour+"hr "+minute+"min");
	}

	
	/*
	 * method to select a flight with lowest fare
	 */
	public FlightReviewPage selectLowFareFlight() {

		TestUtil.log().info("Checking and changing the flights list with prices starting from least");
		turnPriceToggleUp();
		
		TestUtil.log().info("Trying to find the least priced flight");
		findCheapFlightFare();
		
		TestUtil.log().info("Trying to find the flight with shortest duration");
		findShortestFlightDuration();
		
		
		TestUtil.log().info("Index of selected flight = " + index);
		bookBtn.get(index).click();
		
		
		return new FlightReviewPage();

	}

}

package com.goibibo.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.goibibo.base.TestBase;
import com.goibibo.util.TestUtil;

public class LandingPage extends TestBase {

	/*
	 * constructor that intialize "this" class with "driver"
	 */
	public LandingPage() {
		PageFactory.initElements(driver, this);
	}

	/*
	 * locators
	 */
	@FindBy(id = "gosuggest_inputSrc")
	WebElement from;

	@FindBy(xpath = "//*[@id='react-autosuggest-1']")
	WebElement autodropDown;

	@FindBy(id = "gosuggest_inputDest")
	WebElement to;

	@FindBy(id = "oneway")
	WebElement SingleTripTabButton;

	@FindBy(xpath = "//*[@id='roundTrip']")
	WebElement roundTripTabButton;

	@FindBy(xpath = "//*[@id='oneway']")
	WebElement oneWayTabButton;

	@FindBy(xpath = "//span[contains(@class,'iconText')][contains(text(),'Flights')]")
	WebElement flightsTab;

	@FindBy(xpath = "//input[@id='departureCalendar']")
	WebElement departCal;

	@FindBy(xpath = "//input[@id='returnCalendar']")
	WebElement retunCal;

	@FindBy(xpath = "//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")
	WebElement forwardArrow;

	@FindBy(xpath = "//div[@class='fareCalFlt ']/div/div[2]/div")
	WebElement calMonthYear;

	@FindBy(id = "gi_search_btn")
	WebElement searchButton;

	/*
	 * Action methods
	 */
	public boolean validateFlightTab() {
		TestUtil.log().info("Validating whether we are in flights tab or not");
		return flightsTab.isEnabled();
	}

	public void selectRoundTripTab() {
		TestUtil.log().info("Selecting and verifying Round Trip tab");
		roundTripTabButton.click();
		Assert.assertEquals(roundTripTabButton.isEnabled(), true, "RoundTrip tab is not selected");

	}

	public RoundTripFlights enterRoundTripDetails() throws ParseException, InterruptedException {

		selectRoundTripTab();
		from.sendKeys(prop.getProperty("from"));
		Wait(autodropDown);
		from.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

		to.sendKeys(prop.getProperty("to"));
		Wait(autodropDown);
		to.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

		departCal.click();

		calender(prop.getProperty("departDate"));

		retunCal.click();

		calender(prop.getProperty("returnDate"));

		searchButton.click();

		return new RoundTripFlights();
	}

	public String calender(String d) throws ParseException {

		String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		// extract Day, Month, Year
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date mydate = df.parse(d);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(mydate);

		int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int month = cal.get(java.util.Calendar.MONTH);
		int year = cal.get(java.util.Calendar.YEAR);

		
		String travelMonth = months[month] + " " + year;

		while (!calMonthYear.getText().equals(travelMonth)) {
			forwardArrow.click();
		}

		// int monthNum = month +1;
		// driver.findElement(By.xpath("//div[@id='fare_" + year + monthNum + day +
		// "']")).click();
		driver.findElement(By.xpath("//*[contains(@class, 'calDate')][contains(text(),'" + day + "')]")).click();

		return day + "/" + months[month] + "/" + year;
	}

	public SingleTripFlight enterSingleTripDetails() throws ParseException {

		TestUtil.log().info("Entering FROM airport as - " + prop.getProperty("from").toUpperCase());
		from.sendKeys(prop.getProperty("from"));
		Wait(autodropDown);
		from.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

		TestUtil.log().info("Entering TO airport as - " + prop.getProperty("to").toUpperCase());
		to.sendKeys(prop.getProperty("to"));
		Wait(autodropDown);
		to.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

		TestUtil.log().info("Selecting calander for picking date");
		departCal.click();

		TestUtil.log().info("Selecting the date as - ");
		String d = calender(prop.getProperty("departDate"));
		TestUtil.log().info("Selected date is : " + d);

		TestUtil.log().info("Clicking on SEARCH button");
		searchButton.click();

		return new SingleTripFlight();
	}

	/*
	 * method to verify whether user is currently in single trip tab or not
	 */
	public boolean selectSingleTripTab() {
		TestUtil.log().info("Trying to find whether we are in Single_Trip tab or NOT");
		boolean stb = SingleTripTabButton.isEnabled();
		while (stb != true) {
			TestUtil.log().info("Changing to Single_Trip tab");
			SingleTripTabButton.click();
			stb = selectSingleTripTab();
			
		}

		return stb;
	}

}

package com.goibibo.testcases;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.goibibo.base.TestBase;
import com.goibibo.pages.FlightReviewPage;
import com.goibibo.pages.LandingPage;
import com.goibibo.pages.SingleTripFlight;
import com.goibibo.util.TestUtil;

public class SingleTripTest extends TestBase {

	/*
	 * change invocationcount, to run the same method for multiple times.
	 * Add threadPoolSiz & timeOut to run test case parallelly 
	 */
	@Test(invocationCount = 1)
	public static void SingleTripPaymentTest() throws ParseException {
		LandingPage lp = new LandingPage();
		
		TestUtil.log().info("Validating Flight tab");
		Assert.assertEquals(lp.validateFlightTab(), true, "Fligt tab is not selected");
		
		TestUtil.log().info("Selecting One Way flight tab");
		Assert.assertEquals(lp.selectSingleTripTab(), true, "OneWay tab is not selected");
		
		TestUtil.log().info("Enter all  details and search for flight ");
		SingleTripFlight sf = lp.enterSingleTripDetails();
		
		TestUtil.log().info("Selecting lowerst price flight from list");
		FlightReviewPage fr = sf.selectLowFareFlight();
		
		TestUtil.log().info("Select paypal payment methods after slecting no insurance & entering passenger details");
		boolean epd = fr.selectPaypalWithoutInsurance();
		Assert.assertEquals(epd, true, "Payment amount is other than INR");
		TestUtil.log().info("Payment Amount is INR format");

	}

}

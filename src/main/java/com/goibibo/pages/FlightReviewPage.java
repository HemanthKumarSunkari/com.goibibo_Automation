package com.goibibo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.goibibo.base.TestBase;
import com.goibibo.util.TestUtil;

public class FlightReviewPage extends TestBase {

	

	public FlightReviewPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[contains(text(),'I am willing to risk my trip')]")
	WebElement noInsuranceBtn;

	@FindBy(id = "Adulttitle1")
	WebElement Title;

	@FindBy(id = "AdultfirstName1")
	WebElement firstName;

	@FindBy(id = "AdultmiddleName1")
	WebElement middleName;

	@FindBy(id = "AdultlastName1")
	WebElement lastName;

	@FindBy(id = "email")
	WebElement email;

	@FindBy(id = "mobile")
	WebElement mobile;

	@FindBy(xpath = "//button[contains(@class,'button')]//div[1]")
	WebElement proceedBtn;

	@FindBy(xpath = "//*[@id=\"addonCard\"]/button")
	WebElement proceedToPaymentBtn;

	@FindBy(id = "tab_paypal")
	WebElement paypal;

	@FindBy(xpath = "//label[contains(@for, 'radio0INR')]")
	WebElement inrAmount;
	
	
	public void selectNoInsurance() {
		TestUtil.log().info("Scrolling down to find : " + noInsuranceBtn.getText());
		js.executeScript("arguments[0].scrollIntoView();", noInsuranceBtn);
		Wait(noInsuranceBtn).click();
		TestUtil.log().info("Selected No Insurance - check box");
		
	}
	
	
	public void enterPassengerDetails() {

		TestUtil.log().info("Scrolling down to find Passenger Details - proceed btn");
		js.executeScript("arguments[0].scrollIntoView();", proceedBtn);
		

		TestUtil.log().info("Entered Passenger details are : ");
		
		Title.sendKeys(prop.getProperty("pTitle"));
		TestUtil.log().info("Entered Title : "+ prop.getProperty("pTitle"));

		firstName.sendKeys(prop.getProperty("pFName"));
		TestUtil.log().info("Entered First Name : "+ prop.getProperty("pFName"));

		middleName.sendKeys(prop.getProperty("pMName"));
		TestUtil.log().info("Entered Middle Name : "+ prop.getProperty("pMName"));

		lastName.sendKeys(prop.getProperty("pLName"));
		TestUtil.log().info("Entered Last Name : "+ prop.getProperty("pLName"));

		email.sendKeys(prop.getProperty("PEmail"));
		TestUtil.log().info("Entered Email : "+ prop.getProperty("PEmail"));

		mobile.sendKeys(prop.getProperty("pMobile"));
		TestUtil.log().info("Entered Mobile Number : "+ prop.getProperty("pMobile"));

		proceedBtn.click();
		TestUtil.log().info("Clicked on Proceed button");

	}
	
	
	public boolean selectPaypalPaymentMethod() {
		try {
			// driver.switchTo().alert().accept();
			TestUtil.log().info("closing the popup");
			driver.findElement(By.xpath("//button[@class='button blue large fb padLR30']")).click();
			
			TestUtil.log().info("Scrolling down to find PROCEED TO PAYMENT button");
			js.executeScript("arguments[0].scrollIntoView();", proceedToPaymentBtn);
			TestUtil.log().info("Clicking on Proceed to payment button");
			proceedToPaymentBtn.click();
		} catch (Exception e) {
			System.out.println("There is no option to select seats and SkipToPayment button");
		}
		
		TestUtil.log().info("Scrolling down to find PROCEED TO PAYMENT button");
		js.executeScript("arguments[0].scrollIntoView();", proceedToPaymentBtn);
		TestUtil.log().info("Wait untill PAYPAL payment method is loaded");
		Wait(paypal);
		TestUtil.log().info("Selecting paypal as payment method");
		paypal.click();
		
		TestUtil.log().info("Verifying payment is in INR amount or not");
		return inrAmount.isEnabled();
	}
	
	
	public boolean selectPaypalWithoutInsurance() {
		selectNoInsurance();
		enterPassengerDetails();
		return selectPaypalPaymentMethod();
	}

}

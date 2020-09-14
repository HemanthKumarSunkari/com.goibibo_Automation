package com.goibibo.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.goibibo.base.TestBase;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	public static long EXPLICIT_WAIT = 20;

	public void switchToFrame() {
		driver.switchTo().frame("mainpanel");
	}

	public static void captureScreenshot() throws IOException {
		
		
		TestUtil.log().info("Trying to take failure screenshot of current page");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String currentDir = System.getProperty("user.dir");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_MM_SS_a");
		String date = sdf.format(d);

		String path = currentDir + "\\Screenshot\\" + date + ".png";

		FileUtils.copyFile(scrFile, new File(path));
		TestUtil.log().info("Screenshot is added to : " + path);
	}

//	@DataProvider(name = "dp")
//	public Object[][] getData(Method m) {
//
//		String sheetName = m.getName();
//		int rows = excel.getRowCount(sheetName);
//		int cols = excel.getColumnCount(sheetName);
//
//		Object[][] data = new Object[rows - 1][1];
//
//		Hashtable<String, String> table = null;
//
//		for (int rowNum = 2; rowNum <= rows; rowNum++) { 
//
//			table = new Hashtable<String, String>();
//
//			for (int colNum = 0; colNum < cols; colNum++) {
//
//				// data[0][0]
//				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
//				data[rowNum - 2][0] = table;
//			}
//
//		}
//
//		return data;
//
//	}

	/*
	 * logger method that return with current class name
	 */
	public static Logger log() {
		return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}

}

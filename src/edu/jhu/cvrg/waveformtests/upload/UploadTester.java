/**
 * 
 */
package edu.jhu.cvrg.waveformtests.upload;

import java.util.concurrent.TimeUnit;
import java.awt.Robot;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import edu.jhu.cvrg.waveformtests.BaseFunctions;
import edu.jhu.cvrg.waveformtests.LogfileManager;
import edu.jhu.cvrg.waveformtests.TestNameEnum;
import edu.jhu.cvrg.waveformtests.UIComponentChecks;

/**
 * @author bbenite1
 *
 */
public class UploadTester extends BaseFunctions implements UIComponentChecks{

	/**
	 * @param site
	 * @param viewPath
	 * @param welcomePath
	 * @param logfileLocation
	 */
	public UploadTester(String site, String viewPath, String welcomePath,
			String username, String password) {
		super(site, viewPath, welcomePath, username, password);

		// TODO More code may be required here in the future
	}
	
	public void uploadFile() throws IOException {
		String newFolderBox = "A0684:formUpload:txtFoldername";
		String addFolder = "A0684:formUpload:btnAdd";
		String uploadFolderLocation = "SeleniumTest";
		
		try {
			// Test creating a new folder
			portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			WebElement newFolderInput = portletDriver.findElement(By.id(newFolderBox));
			
			newFolderInput.clear();
			
			portletLogMessages.add("The Text Input for the new folder creation has been successfully cleared");
			logger.incrementUploadSuccess();
			
			newFolderInput.sendKeys(uploadFolderLocation);
			
			portletLogMessages.add("Entering name in the the Text Input has been successful");
			logger.incrementUploadSuccess();
			
			portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			portletDriver.findElement(By.id(addFolder)).click();
			
			portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			portletLogMessages.add("New folder created");
			logger.incrementUploadSuccess();
			
		} catch (NoSuchElementException ne) {
			seleniumLogMessages.add("An element was not found in the DOM in Analysis Algorithms, here is more information:  " + LogfileManager.extractStackTrace(ne));
			logger.incrementUploadFails();
		} catch (StaleElementReferenceException se) {
			seleniumLogMessages.add("An element was trying to be accessed but it most likely got refreshed since the last time the page updated.\n  This is due to the fact that elements are dynamically removed and recreated, so even if they have the same characteristics it is still a new element.\n  Here is more information:  " + LogfileManager.extractStackTrace(se));
			logger.incrementUploadFails();
		} finally {
			logger.addToLog(portletLogMessages, TestNameEnum.UPLOAD);
			
			if(!(seleniumLogMessages.isEmpty())) {
				logger.addToLog(seleniumLogMessages, TestNameEnum.SELENIUM);
			}
		}
		
		// Test uploading to an existing folder
	}
	
	@Override
	public void validateButtons() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * Non-applicable on this portlet only
	 */
	@Override
	public void validateRecordList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectSingleECG() {
		// TODO Auto-generated method stub
		
	}

}

/**
 * 
 */
package edu.jhu.cvrg.waveformtests.upload;

import java.util.concurrent.TimeUnit;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.seleniummain.BrowserEnum;
import edu.jhu.cvrg.seleniummain.LogfileManager;
import edu.jhu.cvrg.seleniummain.TestNameEnum;

/**
 * @author bbenite1
 *
 */
public class UploadTester extends BaseFunctions {

	/**
	 * @param site
	 * @param viewPath
	 * @param welcomePath
	 * @param logfileLocation
	 */
	
	public UploadTester(String site, String viewPath, String welcomePath, String userName, String passWord, boolean loginRequired, BrowserEnum whichBrowser) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired, whichBrowser);
	}
	
	public UploadTester(String site, String viewPath, String welcomePath,
			String username, String password, boolean loginRequired) {
		super(site, viewPath, welcomePath, username, password, loginRequired);

	}
	
	public UploadTester(String site, String viewPath, String welcomePath, String userName, String passWord, WebDriver existingDriver) {
		super(site, viewPath, welcomePath, userName, passWord, existingDriver);
	}
	
	public void uploadFile() throws IOException {
		String newFolderBox = "A0684:formUpload:txtFoldername";
		String addFolder = "A0684:formUpload:btnAdd";
		String uploadFolderLocation = "SeleniumTest";
		
		try {
			// Test creating a new folder
			portletDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			
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
			logger.addToLog(portletLogMessages, TestNameEnum.WAVEFORMUPLOAD);
			
			if(!(seleniumLogMessages.isEmpty())) {
				logger.addToLog(seleniumLogMessages, TestNameEnum.SELENIUM);
			}
		}
		
		// TODO:  Perhaps finding a way to upload a file may be possible in the future, but as of right now Selenium does not allow for this
		// When an operating system 
	}

}

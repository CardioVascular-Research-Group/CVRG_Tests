/**
 * 
 */
package edu.jhu.cvrg.waveformtests.upload;

import java.util.concurrent.TimeUnit;
import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import edu.jhu.cvrg.waveformtests.BaseFunctions;
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
	
	public void uploadFile() {
		String newFolderBox = "A0684:formUpload:txtFoldername";
		String addFolder = "A0684:formUpload:btnAdd";
		String uploadFolderLocation = "SeleniumTest";
		
		// Test uploading to a new folder
		
		WebElement newFolderInput = portletDriver.findElement(By.id(newFolderBox));
		
		newFolderInput.clear();
		newFolderInput.sendKeys(uploadFolderLocation);
		
		portletDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		portletDriver.findElement(By.id(addFolder)).click();
		
		portletDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		// Test uploading to an existing folder
	}
	
	private void selectFile(String folder) {
		
		String choose = "A0684:formUpload:uploader_input";
		String uploadAll = "aui_3_4_0_1_807";
		String removeAll = "aui_3_4_0_1_826";
		
		//portletDriver.findElement(By.id(choose)).sendKeys("");
		
		// The Robot class could possibly be used to directly move the cursor to the desired location within
		// the OS dialog box
		
		
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

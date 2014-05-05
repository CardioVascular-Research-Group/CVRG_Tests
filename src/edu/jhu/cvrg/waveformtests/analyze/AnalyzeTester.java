package edu.jhu.cvrg.waveformtests.analyze;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.seleniummain.BrowserEnum;
import edu.jhu.cvrg.seleniummain.LogfileManager;
import edu.jhu.cvrg.seleniummain.TestNameEnum;
import edu.jhu.cvrg.waveformtests.UIComponentChecks;

public class AnalyzeTester extends BaseFunctions implements UIComponentChecks{

	public AnalyzeTester(String site, String viewPath, String welcomePath, String userName, String passWord, boolean loginRequired, BrowserEnum whichBrowser) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired, whichBrowser);
	}
	
	public AnalyzeTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		// TODO Auto-generated constructor stub
	}
	
	public AnalyzeTester(String site, String viewPath, String welcomePath, String userName, String passWord, WebDriver existingDriver) {
		super(site, viewPath, welcomePath, userName, passWord, existingDriver);
	}
	
	public void analyzeOneECG() throws IOException {
		try {
			
			validateFolderTree();
			selectSingleECG();
			validateCheckBoxes();
			
			// click the start analysis button and wait for it to finish
			portletDriver.findElement(By.id("A6680:formAnalyze:j_idt27")).click();
			
			WebDriverWait analysisWait = new WebDriverWait(portletDriver, 15); 
		
			analysisWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui-progressbar-label']")));
			
			// log resulting message here
			if(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='ui-messages-info-summary']")).apply(portletDriver).isDisplayed()) {
				portletLogMessages.add("The analysis was successful:  " + portletDriver.findElement(By.xpath("//span[@class='ui-messages-info-summary']")).getText());
				logger.incrementAnalyzeSuccess();
			}
			else if(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='ui-messages-error-summary']")).apply(portletDriver).isDisplayed()) {
				WebElement errorMessage = portletDriver.findElement(By.xpath("//span[@class='ui-messages-error-summary']"));
				portletLogMessages.add("An error has been found on this analysis, the message is:  " + errorMessage.getText());
				logger.incrementAnalyzeFails();
			}
			else if(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='ui-messages-warn-summary']")).apply(portletDriver).isDisplayed()) {
				WebElement warningMessage = portletDriver.findElement(By.xpath("//span[@class='ui-messages-warn-summary']"));
				portletLogMessages.add("An warning has been found on this analysis, the message is:  " + warningMessage.getText());
				logger.incrementAnalyzeFails();
			}
			else {
				portletLogMessages.add("The analysis took too long");
				logger.incrementAnalyzeFails();
			}
		} catch (NoSuchElementException ne) {
			seleniumLogMessages.add("An element was not found in the DOM in Analysis Algorithms, here is more information:  " + LogfileManager.extractStackTrace(ne));
			logger.incrementAnalyzeFails();
		} catch (StaleElementReferenceException se) {
			seleniumLogMessages.add("An element was trying to be accessed but it most likely got refreshed since the last time the page updated.\n  This is due to the fact that elements are dynamically removed and recreated, so even if they have the same characteristics it is still a new element.\n  Here is more information:  " + LogfileManager.extractStackTrace(se));
			logger.incrementAnalyzeFails();
		} finally {
			logger.addToLog(portletLogMessages, TestNameEnum.WAVEFORMANALYZE);
			
			if(!(seleniumLogMessages.isEmpty())) {
				logger.addToLog(seleniumLogMessages, TestNameEnum.SELENIUM);
			}
		}
	}

	
	public void selectSingleECG() throws NoSuchElementException, StaleElementReferenceException{
		
		List<WebElement> leafNodes = portletDriver.findElements(By.className("ui-icon-note"));
		WebElement firstNode = leafNodes.get(0);
		
		firstNode.click();
		portletDriver.findElement(By.id("A6680:formAnalyze:btnDisplay")).click();	
	}

	@Override
	public void validateButtons() {
		// TODO validate all buttons
		
	}
	
	public void validateCheckBoxes() throws NoSuchElementException, StaleElementReferenceException {
			List<WebElement> checkBoxes = portletDriver.findElements(By.xpath("//div[@class='ui-chkbox-box ui-widget ui-corner-all ui-state-default']"));
			
			int size = checkBoxes.size() - 1;
			
			for (int i=0; i<size; i++) {
				try {
					// Using xpath, navigate to the next table cell which contains the algorithm name and extract it
					System.out.println("i = " + i);
					List<WebElement> algorithmElement = checkBoxes.get(i).findElements(By.xpath("//parent::div/parent::td/parent::tr"));
					String algorithmName = algorithmElement.get(i).getAttribute("data-rk");
					
					System.out.println("Name of algorithm = " + algorithmName);
				
					checkBoxes.get(i).click();
					portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					
					if(i == 0) {
						checkBoxes.get(i).click();
					}
					else {
						// For the check boxes within the table, each time one of them is clicked, a new div tag is created.  
						// That new div tag must be captured in order to unclick the checkbox.
						portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						WebElement tempCheckBox = portletDriver.findElement(By.xpath("//div[@class='ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active']"));
						tempCheckBox.click();
						portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					
					portletLogMessages.add("The checkbox selection for " + algorithmName + " is working");
					logger.incrementAnalyzeSuccess();
		

				} catch (StaleElementReferenceException se) {
					seleniumLogMessages.add("A checkbox was not able to be clicked.  This was due to the corresponding element being altered or removed and then replaced with a new one.  This means that element being accessed is no longer valid.\n  Here is more information:  \n" + LogfileManager.extractStackTrace(se));
					logger.incrementAnalyzeFails();
				} finally {
					// again, since new tags are created, the List of checkBox elements must be refreshed
					checkBoxes = portletDriver.findElements(By.xpath("//div[@class='ui-chkbox-box ui-widget ui-corner-all ui-state-default']"));
					portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				}
			}
			
			checkBoxes.get(7).click();
		
	}

}

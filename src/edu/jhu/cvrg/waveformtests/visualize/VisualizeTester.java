/**
 * 
 */
package edu.jhu.cvrg.waveformtests.visualize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.seleniummain.BrowserEnum;
import edu.jhu.cvrg.waveformtests.WaveformTestProperties;
import edu.jhu.cvrg.waveformtests.UIComponentChecks;

/**
 * @author bbenite1
 *
 */
public class VisualizeTester extends BaseFunctions implements UIComponentChecks{
	
	private DisplayPanelEnum currentPage;
	private WaveformTestProperties testProps;
	
	public VisualizeTester(String site, String viewPath, String welcomePath, String userName, String passWord, boolean loginRequired, BrowserEnum whichBrowser) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired, whichBrowser);
	}
	
	public VisualizeTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		
		currentPage = DisplayPanelEnum.INITIAL;
		testProps = WaveformTestProperties.getInstance();
		
	}
	
	public VisualizeTester(String site, String viewPath, String welcomePath, String userName, String passWord, WebDriver existingDriver) {
		super(site, viewPath, welcomePath, userName, passWord, existingDriver);
	}
	
	public void testVisualizeViews() {
		goToPage();
		selectECGFromTree();
		validateButtons();
		
		pickLead();
	}
	
	public void pickLead() {
		// TODO:  Select a lead graph from the dygraph screen.  Figure out some way to get the 
		//			dygraphs to work in Selenium
	}

	public void selectECGFromTree() {
		// expand the folder tree to expose the node for selection
		this.validateFolderTree();	
		
		selectSingleECG();
		pickECGForDisplay();
				
	}

	public void pickECGForDisplay() {
		List<WebElement> ecgRows = portletDriver.findElements(By.xpath("//td[@role='gridcell']"));
		ecgRows.get(0).click();
		portletDriver.findElement(By.id("A1576:formVisualize:btnView12LeadECGTest")).click();
		portletDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		currentPage = DisplayPanelEnum.MULTILEAD;
	}

	@Override
	public void validateButtons() {
		// TODO Auto-generated method stub
		switch(currentPage) {
		case INITIAL:
			// Testing buttons on initial view
			break;
		case MULTILEAD:
			// primarily testing the navigation buttons
			ArrayList<String> navButtons = testProps.getMultiLeadNavButtons();
			
			for (String buttonID : navButtons) {
				portletDriver.findElement(By.id(buttonID)).click();
				portletDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			}
			
			String textInputID = testProps.getJumpToInputBox();
			String jumpButtonID = testProps.getJumpToButton();
			
			WebElement textInput = portletDriver.findElement(By.id(textInputID));
			textInput.click();
			textInput.sendKeys("5");
			portletDriver.findElement(By.id(jumpButtonID)).click();
			
			portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			break;
		case SINGLELEAD:
			// testing the navigation buttons again
			break;
		case ANNOTATION:
			// buttons on annotation screen
			break;
		default:
			break;	
		}
	}

	@Override
	public void selectSingleECG() {
		List<WebElement> leafNodes = portletDriver.findElements(By.className("ui-icon-note"));
		WebElement firstNode = leafNodes.get(0);
		
		firstNode.click();
		portletDriver.findElement(By.id("A1576:formVisualize:btnDisplay")).click();	
		
	}

}

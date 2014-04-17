package edu.jhu.cvrg.ceptests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.seleniummain.LogfileManager;
import edu.jhu.cvrg.authenticationtests.*;

public abstract class GenericCEPTester extends BaseFunctions {
	
	protected boolean step1Success = false;
	protected boolean step2Success = false;
	
	protected CEPTestProperties cepProps;

	public GenericCEPTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		
		cepProps = CEPTestProperties.getInstance();
	}
	
	// This overridden version uses the GlobusLogin class for testing (no need to reinvent the wheel)
	@Override
	public final void login(boolean newWindowNeeded) {
		GlobusLogin gLogin = new GlobusLogin(this.host, this.portletPage, this.welcomeScreen, this.username, this.password, this.loginNeeded);
		
		// test if login through Globus works.
		// Note:  Technically speaking the Globus doesn't doesn't explicitly require Globus so this can work with
		//        a non-Globus enabled machine too
		try {
			boolean isSuccess = gLogin.testGlobus();
			
			if(!isSuccess) {
				portletLogMessages.add("Login failed");
			}
			else {
				portletLogMessages.add("Login succeeded");
			}
			
		} catch (IOException e) {
			seleniumLogMessages.add("An IOException was found, here are the details:\n" + LogfileManager.extractStackTrace(e));
			e.printStackTrace();
		}
	}
	
	public abstract void runAllTests() throws CEPException, IOException;
	
	protected void emptySearch(String inputBoxID, String nextButtonID) {
		portletLogMessages.add("Entering nothing in the search, expecting a message:");
		
		portletDriver.findElement(By.id(inputBoxID)).clear();
		portletDriver.findElement(By.id(nextButtonID)).click();
		
		if(!(portletDriver.findElements(By.xpath("//div[@class='portlet-msg-error']")).isEmpty())) {
			portletLogMessages.add("An empty string was entered into the search field.\n  A message asking to enter data a value in the search field has successfully been given");
		}
		else {
			portletLogMessages.add("ERROR:  An empty string was entered into the search field, but no error message displayed");
		}
	}
	
	// The search methods will be completed up to the point where the checkStep2Success method is called.  An overridden version
	// can be added according the individual needs of each portlet
	
	protected void searchByFirstname(String inputBoxID, String step1NextID, String step2NextButtonID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "wolfgang";
		
		portletLogMessages.add("Searching by first name, first name used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue, step2NextButtonID);
	}
	
	protected void searchByLastname(String inputBoxID, String step1NextID, String step2NextButtonID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "benitez";
		
		portletLogMessages.add("Searching by last name, last name used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue, step2NextButtonID);
	}
	
	protected void searchByPubmedID(String inputBoxID, String step1NextID, String step2NextButtonID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "23442855";
		
		portletLogMessages.add("Searching by Pubmed ID, Pubmed ID used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue, step2NextButtonID);
	}
	
	protected void searchByFullname(String inputBoxID, String step1NextID, String step2NextButtonID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "Moyer, Daniel";
		
		portletLogMessages.add("Searching by full name, full name used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue, step2NextButtonID);
	}
	
	protected void gibberishSearch(String inputBoxID, String step1NextID, String step2NextButtonID) {
		String inputValue = "djadsalkdjajda";
		
		portletLogMessages.add("Putting in a junk value, value is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue, step2NextButtonID);
	}
	
	protected void searchByFirstInitial(String inputBoxID, String step1NextID, String step2NextButtonID) {
		String inputValue = "Moyer, D";
		
		portletLogMessages.add("Putting in a Last name and first initial, value is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue, step2NextButtonID);
	}
	
	protected void searchByTitle(String inputBoxID, String step1NextID, String step2NextButtonID) {
		String inputValue = "Stress fractures of the pelvis and legs in athletes: a review. ";
		
		portletLogMessages.add("Putting in a junk value, value is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue, step2NextButtonID);
	}
	
	// This will carry out both step 1 and 2, which are very similar in both upload and search
	protected void conductCommonTests(String inputBoxID, String step1NextID, String inputValue, String step2NextButtonID) {
		step1Success = this.checkStep1Success(inputBoxID, step1NextID, inputValue);
		
		if(step1Success) {
			portletLogMessages.add("Step 1 has been completed");
			
			// check to see if the data table reports that no search results were found
			if(!(portletDriver.findElements(By.xpath("//tr[@class='ui-widget-content ui-datatable-empty-message']")).isEmpty())) {
				portletLogMessages.add("No search results were found");
			}
			
			step2Success = this.checkStep2Success(step2NextButtonID);
		}
		else {
			portletLogMessages.add("ERROR:  Unable to reach data results page");
			step2Success = false;
		}
	}
	
	protected boolean checkStep1Success(String inputBoxID, String step1NextID, String inputValue) {
		// The main goal is to check for the existence of the datatable and the back and next buttons on the next page.  If there is a better way to test this then
		// this can be replaced with that check instead.
		portletDriver.findElement(By.id(inputBoxID)).clear();
		portletDriver.findElement(By.id(inputBoxID)).sendKeys(inputValue);
		portletDriver.findElement(By.id(step1NextID)).click();
		
		portletDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		boolean success = false;
		
		if(!(portletDriver.findElements(By.xpath("//table")).isEmpty())) {
			portletLogMessages.add("The datatable on the second step exists");
			
			if(!(portletDriver.findElements(By.xpath("//button"))).isEmpty() && (portletDriver.findElements(By.xpath("//button"))).size() == 2 ) {
				portletLogMessages.add("The Back and Next buttons exist");
				success = true;
			}
			else {
				portletLogMessages.add("Either one or both buttons are missing, or there are more than two on the page");
			}
		}
		else {
			portletLogMessages.add("The datatable is missing from the page");
		}
		
		return success;
	}
	
	protected boolean checkStep2Success(String step2NextButtonID) {
		boolean success = false;
		
		// first, if there are no results or nothing has been highlighted, check the next button and make sure it does not proceed to the next page
		try {
		portletDriver.findElement(By.id(step2NextButtonID)).click();
		portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
		if(!(portletDriver.findElements(By.xpath("//span[text='Please choose a single citation from the listing']")).isEmpty())) {
			portletLogMessages.add("There were no search results, and clicking Next produced the correct message");
			success = true;
		}
		else {
			if(!(portletDriver.findElements(By.xpath("//tr[@class='ui-widget-content ui-datatable-empty-message']")).isEmpty())) {
				portletLogMessages.add("ERROR:  There were no search results, but clicking Next allowed the page to proceed anyway");
			}
			else {
				// select an entry and then try again, it should proceed this time
				portletDriver.findElement(By.xpath("//tr[@data-rk='1']")).click();
				portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				
				if(!(portletDriver.findElements(By.xpath("//tr[@class='ui-widget-content ui-datatable-empty-message']")).isEmpty())) {
					portletLogMessages.add("ERROR:  Search results were selected, but clicking Next did not allow the page to proceed");
				}
				else {
					portletLogMessages.add("Search results were selected, and clicking Next did allow the page to proceed");
					success = true;
				}
			}
			
		}
		} catch(NoSuchElementException ne) {
			seleniumLogMessages.add("Selenium was unable to find an element, here is the full stack trace:\n" + LogfileManager.extractStackTrace(ne));
		}
		
		return success;
	}
	
	protected abstract void resetPage(String firstStepInputBoxID) throws CEPException;
	
	

}

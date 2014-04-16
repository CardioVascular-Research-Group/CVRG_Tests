package edu.jhu.cvrg.ceptests;

import java.io.IOException;

import org.openqa.selenium.By;

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
	
	public abstract void runAllTests() throws CEPException;
	
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
	// will complete the rest according the individual needs of each portlet
	
	protected void searchByFirstname(String inputBoxID, String step1NextID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "wolfgang";
		
		portletLogMessages.add("Searching by first name, first name used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue);
	}
	
	protected void searchByLastname(String inputBoxID, String step1NextID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "benitez";
		
		portletLogMessages.add("Searching by last name, last name used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue);
	}
	
	protected void searchByPubmedID(String inputBoxID, String step1NextID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "";
		
		portletLogMessages.add("Searching by Pubmed ID, Pubmed ID used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue);
	}
	
	protected void searchByFullname(String inputBoxID, String step1NextID) {
		// TODO:  Utilize the CEPTestProperties class for this
		String inputValue = "";
		
		portletLogMessages.add("Searching by full name, full name used is " + inputValue);
		
		this.conductCommonTests(inputBoxID, step1NextID, inputValue);
	}
	
	// This will carry out both step 1 and 2, which are very similar in both upload and search
	private void conductCommonTests(String inputBoxID, String step1NextID, String inputValue) {
		step1Success = checkStep1Success(inputBoxID, step1NextID, inputValue);
		
		if(step1Success) {
			portletLogMessages.add("Step 1 has been completed");
			step2Success = checkStep2Success();
		}
		else {
			portletLogMessages.add("ERROR:  Unable to reach data results page");
			step2Success = false;
		}
	}
	
	private boolean checkStep1Success(String inputBoxID, String step1NextID, String inputValue) {
		
		
		return false;
	}
	
	// The base version only selects the row itself.  An overridden version will complete this step
	protected void selectDatatableEntry() {
		
	}
	
	protected abstract boolean checkStep2Success();
	
	protected abstract void resetPage(String firstStepInputBoxID) throws CEPException;
	
	

}

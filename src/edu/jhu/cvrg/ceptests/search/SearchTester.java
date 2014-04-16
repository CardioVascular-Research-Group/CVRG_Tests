package edu.jhu.cvrg.ceptests.search;

import org.openqa.selenium.By;

import edu.jhu.cvrg.ceptests.CEPException;
import edu.jhu.cvrg.ceptests.GenericCEPTester;
import edu.jhu.cvrg.ceptests.TestScenarioEnum;

public final class SearchTester extends GenericCEPTester {

	public SearchTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void runAllTests() throws CEPException {
		
		String inputBoxID = "A0660:step1start:se";
		String step1NextButtonID = "A0660:step1start:step1next";
		
		// first, run different test case methods for each type of case
		for(TestScenarioEnum testCase : TestScenarioEnum.values()) {
			portletLogMessages.add("Cuurently searching by " + testCase + ", resetting page");
			
			this.resetPage(inputBoxID);
			
			switch(testCase) {
				case BLANK:
					emptySearch(inputBoxID, step1NextButtonID);
					break;
				case FIRSTNAME:
					break;
				case FULLNAME:
					break;
				case LASTNAME:
					break;
				case PUBMEDID:
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected void resetPage(String firstStepInputBoxID) throws CEPException {
		
		// Since the back buttons on each page go directly to the starting page, there is no need to iterate through the back buttons
			
			// check to see if it is on the search results page
			if(portletDriver.findElements(By.id("A0660:myform1:step2back2")).isEmpty()) {
				portletLogMessages.add("Currently on search results page, clicking back button");
				portletDriver.findElement(By.id("A0660:myform1:step2back2")).click();
			}
			// next, check to see if it is on the download page
			else if(portletDriver.findElements(By.id("A0660:finalcomplete:startover")).isEmpty()) {
				portletLogMessages.add("Currently on search results page, clicking back button");
				portletDriver.findElement(By.id("A0660:myform1:step2back2")).click();
			}	
			
			// finally, see if we arrived back at the main page, stop testing if we did not
			if(portletDriver.findElements(By.id(firstStepInputBoxID)).isEmpty()) {
				throw new CEPException("Unable to return to the original starting page, testing will now end");
			}
	}

	@Override
	protected boolean checkStep2Success() {
		return true;
		
	}
	

}

package edu.jhu.cvrg.ceptests.upload;

import java.io.IOException;

import edu.jhu.cvrg.ceptests.CEPException;
import edu.jhu.cvrg.ceptests.GenericCEPTester;
import edu.jhu.cvrg.ceptests.TestScenarioEnum;
import edu.jhu.cvrg.seleniummain.TestNameEnum;

public class UploadTester extends GenericCEPTester {

	public UploadTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runAllTests() throws CEPException, IOException {
		// TODO Auto-generated method stub
		String inputBoxID = "A0660:step1start:se";
		String step1NextButtonID = "A0660:step1start:step1next";
		
		portletLogMessages.add("Beginning CEP Search Portlet Tests");
		
		// first, run different test case methods for each type of case
		for(TestScenarioEnum testCase : TestScenarioEnum.values()) {
			portletLogMessages.add("*********************");
			portletLogMessages.add("Currently searching by " + testCase + ", resetting page");
			
			this.resetPage(inputBoxID);
			
			switch(testCase) {
				case BLANK:
					this.emptySearch(inputBoxID, step1NextButtonID);
					break;
				case FIRSTNAME:
					this.searchByFirstname(inputBoxID, step1NextButtonID);
					break;
				case FULLNAME:
					this.searchByFullname(inputBoxID, step1NextButtonID);
					break;
				case LASTNAME:
					this.searchByLastname(inputBoxID, step1NextButtonID);
					break;
				case PUBMEDID:
					this.searchByPubmedID(inputBoxID, step1NextButtonID);
					break;
				case GIBBERISH:
					this.gibberishSearch(inputBoxID, step1NextButtonID);
					break;
				case FIRSTINITIAL:
					this.searchByFirstInitial(inputBoxID, step1NextButtonID);
					break;
				case TITLE:
					this.searchByTitle(inputBoxID, step1NextButtonID);
					break;
				default:
					break;
			}
			portletLogMessages.add("*********************");
		}
		
		logger.addToLog(portletLogMessages, TestNameEnum.CEPUPLOAD);
		logger.addToLog(seleniumLogMessages, TestNameEnum.SELENIUM);

	}

	@Override
	protected void resetPage(String firstStepInputBoxID) throws CEPException {
		// TODO Auto-generated method stub

	}

}

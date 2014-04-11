package edu.jhu.cvrg.authenticationtests;

import org.openqa.selenium.By;

import edu.jhu.cvrg.seleniummain.BaseFunctions;

public class GlobusLogin extends BaseFunctions {

	protected GlobusLogin(String site, String viewPath, String welcomePath,
			String userName, String passWord) {
		super(site, viewPath, welcomePath, userName, passWord);
		// TODO Auto-generated constructor stub
	}
	
	public boolean testGlobus() {
		this.login();
		
		boolean loginSuccess = this.checkSuccess();
		
		if(loginSuccess) {
			portletLogMessages.add("Username and password accepted, proceeding to next screen");
			
			// TODO:  Check to see if page they logged onto loads
			
		}
		else {
			portletLogMessages.add("Error:  Username and/or password are incorrect");
			
			// TODO:  Send a popup message to the user telling them what they can do to correct this problem.
			
		}
		
		return loginSuccess;
	}
	
	public boolean testGlobus(String newUser, String newPassword) {
		this.username = newUser;
		this.password = newPassword;
		
		boolean success = this.testGlobus();
		
		// Since this user does not exist in Liferay yet, the terms of service should show up and be tested here
		// If it does not then record this as an error.  Do this only if login was successful.
		if(success) {
			
		}
		
		return success;
	}
	
	private boolean checkSuccess() {
		return false;
	}

	@Override
	public void selectSingleECG() {
		// TODO Auto-generated method stub

	}

}

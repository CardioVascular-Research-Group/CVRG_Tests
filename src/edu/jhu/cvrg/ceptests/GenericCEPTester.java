package edu.jhu.cvrg.ceptests;

import java.io.IOException;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.seleniummain.LogfileManager;
import edu.jhu.cvrg.authenticationtests.*;

public class GenericCEPTester extends BaseFunctions {

	public GenericCEPTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
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
				this.goToPage();
			}
			
		} catch (IOException e) {
			seleniumLogMessages.add("An IOException was found, here are the details:\n" + LogfileManager.extractStackTrace(e));
			e.printStackTrace();
		}
	}

}

package edu.jhu.cvrg.seleniummain;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.jhu.cvrg.authenticationtests.GlobusLogin;



public abstract class TestController {
	protected LogfileManager logger = LogfileManager.getInstance();
	
	protected Calendar todaysDate;
	protected DateFormat dateFormat;
	protected String hostname;
	protected String logfilePath;
	protected String username;
	protected String password;
	protected String initialWelcomePath = "web/guest/home";
	protected CommonProperties commonProps;
	protected BrowserEnum whichBrowser;

	/**
	 * @param args
	 */
	
	public TestController(String newHostname, String newLogfilePath, String newUsername, String newPassword) {
		hostname = newHostname;
		logfilePath = newLogfilePath;
		username = newUsername;
		password = newPassword;
		
		commonProps = CommonProperties.getInstance();
		
		whichBrowser = BrowserEnum.valueOf(commonProps.getBrowser());
		String driverLocation = commonProps.getBrowserDriver();
		
		// unfortunately, for Inernet Explorer and Chrome, Selenium needs this system property set to find the actual browser driver
		switch(whichBrowser) {
		case INTERNETEXPLORER:
			System.setProperty("webdriver.ie.driver", driverLocation);
			break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver", driverLocation);
			break;
		default:
			// Do nothing
			break;
		}
		
		dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	}
	
	public void testAuthentication() {
		
		try {
			setup();
			
			String mainUser = commonProps.getMainUser();
			String mainPassword = commonProps.getMainPassword();
			String newUser = commonProps.getAltUser();
			String newPassword = commonProps.getAltPassword();
			
			GlobusLogin gLogin = new GlobusLogin(hostname, initialWelcomePath, mainUser, mainPassword, true, whichBrowser);
			boolean loginComplete;
			
			loginComplete = gLogin.testGlobus();
			
			if(loginComplete) {
				gLogin.logout();
			}
			gLogin.close();
			
			
			loginComplete = gLogin.testGlobus(newUser, newPassword, true);
			
			if(loginComplete) {
				gLogin.logout();
			}
			gLogin.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void setUsername(String newUser) {
		username = newUser;
	}
	
	public void setPassword(String newPassword) {
		password = newPassword;
	}
	
	protected void setup() throws IOException {
		logger.setLogfileLocation(logfilePath);
		todaysDate = Calendar.getInstance();
		logger.addToLog("Main Selenium Test Begin:  " + dateFormat.format(todaysDate.getTime()));
	}

}

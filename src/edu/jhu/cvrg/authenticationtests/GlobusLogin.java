package edu.jhu.cvrg.authenticationtests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.seleniummain.TestNameEnum;

public class GlobusLogin extends BaseFunctions {

	public GlobusLogin(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		// TODO Auto-generated constructor stub
	}
	
	public GlobusLogin(String site, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		
		// assume that the welcome screen will be both of the paths in this case 
		super(site, welcomePath, welcomePath, userName, passWord, loginRequired);
		// TODO Auto-generated constructor stub
	}
	
	public boolean testGlobus(boolean newWindowNeeded) throws IOException {
		this.login(newWindowNeeded);
		
		boolean loginSuccess = this.checkInitialSuccess();
		
		portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		if(loginSuccess) {
			portletLogMessages.add("Username and password accepted, proceeding to next screen");
			
			// check for terms of service
			if(!(portletDriver.findElements(By.xpath("//input[@value='I Agree']")).isEmpty())) {
				loginSuccess = processTermsOfService();
			}
			
			// check to see if the user needs to fill in the custom fields
			if(!(portletDriver.findElements(By.id("institution")).isEmpty())) {
				WebElement institute = portletDriver.findElement(By.id("institution"));
				WebElement dept = portletDriver.findElement(By.id("department"));
				WebElement reason = portletDriver.findElement(By.id("reason"));
				
				institute.click();
				institute.clear();
				institute.sendKeys("JHU");
				
				dept.click();
				dept.clear();
				dept.sendKeys("ICM");
				
				reason.click();
				reason.clear();
				reason.sendKeys("Testing");
				
				portletDriver.findElement(By.xpath("//input[@value='Save']")).click();
				
				// check if any form error messages appeared
				if(!(portletDriver.findElements(By.xpath("//div[@class='portlet-msg-error']")).isEmpty())) {
					portletLogMessages.add("Error submitting custom fields, the error is:  " + portletDriver.findElement(By.xpath("//div[@class='portlet-msg-error']")).getText());
					loginSuccess = false;
				}
				else {
					portletLogMessages.add("Form submission successful, continuing on to login");
					loginSuccess = true;
					
					// check for terms of service
					if(!(portletDriver.findElements(By.xpath("//input[@value='I Agree']")).isEmpty())) {
						loginSuccess = processTermsOfService();
					}
					
					// log a success
					portletLogMessages.add("Login successful");
					
				}
			}
			
			
		}
		else {
			portletLogMessages.add("Error:  Username and/or password are incorrect");
		}
		
		logger.addToLog(portletLogMessages, TestNameEnum.GLOBUS);
		return loginSuccess;
	}
	
	public boolean testGlobus(String newUser, String newPassword, boolean newWindowNeeded) throws IOException {
		this.username = newUser;
		this.password = newPassword;
		
		System.out.println("New User = " + username);
		
		boolean success = this.testGlobus(newWindowNeeded);
		
		return success;
	}
	
	public boolean testGlobus() throws IOException {
		return this.testGlobus(false);
	}
	
	private boolean checkInitialSuccess() {
		portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// check if any login error messages appeared
		// A check for a list of elements is present since that will give an empty list instead of an exception.
		// This makes it easier for the program to continue to proceed
		if(!(portletDriver.findElements(By.xpath("//div[@class='portlet-msg-error']")).isEmpty())) {
			System.out.println("Portlet error message found saying " + portletLogMessages.add("Error submitting custom fields, the error is:  " + portletDriver.findElement(By.xpath("//div[@class='portlet-msg-error']")).getText()));
			return false;
		}
		
		return true;
	}
	
	private boolean processTermsOfService() {
		
		return true;
	}

}

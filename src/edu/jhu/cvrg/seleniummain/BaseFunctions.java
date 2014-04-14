/**
 * This package contains the basic tests and functions common to all portlet.  It is also where the main function that runs
 * the test suite resides.
 */
package edu.jhu.cvrg.seleniummain;

/**
 * 
 * This forms the framework for each of the main portlet.  It will contain necessary functions 
 * that all portlets will require in order to be properly tested.
 *
 * @author bbenite1
 */
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public abstract class BaseFunctions {
	
	protected String host;
	protected String portletPage;
	protected String welcomeScreen="web/guest/home";
	protected String username;
	protected String password;
	protected ArrayList<String> portletLogMessages;
	protected ArrayList<String> seleniumLogMessages;
	protected WebDriver portletDriver;
	protected LogfileManager logger = LogfileManager.getInstance();
	
	protected BaseFunctions(String site, String viewPath, String welcomePath, String userName, String passWord) {
		host = site;
		portletPage = viewPath;
		welcomeScreen = welcomePath;
		username = userName;
		password = passWord;
		
		portletLogMessages = new ArrayList<String>();
		seleniumLogMessages = new ArrayList<String>();
		
		// TODO:  For now firefox will be used.  Once browser support has been figured out for other browsers
		// (specifically third party ones for Chrome and Safari), this will be changed to a switch statement
		// to select a web driver (based on the enumeration value)
		portletDriver = new FirefoxDriver();
		
		portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		
		
	}
	
	// To prevent access to the default constructor
	private BaseFunctions() {
		
	}
	
	public void login(boolean newWindowNeeded) {
		if(newWindowNeeded) {
			portletDriver = new FirefoxDriver();
		}
		
		portletDriver.get(host + "/" + welcomeScreen);
		
		portletDriver.manage().window().maximize();
		
		portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
		
		portletDriver.findElement(By.id("sign-in")).click();
		
		portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		WebElement usernameBox = portletDriver.findElement(By.id("_58_login"));
		WebElement passwordBox = portletDriver.findElement(By.id("_58_password"));
		
		usernameBox.click();
		usernameBox.clear();
		usernameBox.sendKeys(username);
		
		passwordBox.click();
		passwordBox.clear();
		passwordBox.sendKeys(password);
		
		portletLogMessages.add("Logging in with username " + username);
		
		portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		portletDriver.findElement(By.xpath("//input[@value='Sign In']")).click();
		
		portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	
	public void login() {
		login(false);
	}
	
	public void logout() {
		portletDriver.findElement(By.xpath("//a[@href='/c/portal/logout']")).click();	
	}
	
	public void close() {
		portletDriver.close();
	}
	
	public void goToPage() {
		portletDriver.get(host + portletPage);
		
		portletDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	public void setURL(String newSite) {
		host = newSite;
	}
	
	public String getURL() {
		return host;
	}
	
	/**
	 * Checks selection and expansion/condensing capabilities in the folder tree.  Note:  This does not check the validity
	 * of anything stored in the tree
	 */
	public void validateFolderTree() {
		try {
			List<WebElement> folderArrows = portletDriver.findElements(By.className("ui-icon-triangle-1-e"));
			
			while(!(folderArrows.isEmpty())) { 
				
				for(WebElement folderArrow : folderArrows) {
					folderArrow.click();
					portletDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				}
				
				folderArrows = portletDriver.findElements(By.className("ui-icon-triangle-1-e"));
			}
			
			portletDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			
		} catch(StaleElementReferenceException ser) {
			ser.printStackTrace();
			return;
		} catch(IndexOutOfBoundsException iob) {
			iob.printStackTrace();
			return;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
				
	}	
	
	public void writeToLogfile() throws IOException {
		
	}
	
	public void setUsername (String newUser) {
		username = newUser;
	}
	
	public void setPassword (String newPassword) {
		password = newPassword;
	}
	
	public abstract void selectSingleECG();


}

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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.*;

import com.opera.core.systems.OperaDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


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
	protected boolean loginNeeded;
	protected CommonProperties commonProps;
	protected BrowserEnum browser;
	
	protected BaseFunctions(String site, String viewPath, String welcomePath, String userName, String passWord, boolean newWindowRequired, BrowserEnum whichBrowser) {
		host = site;
		portletPage = viewPath;
		welcomeScreen = welcomePath;
		username = userName;
		password = passWord;
		loginNeeded = newWindowRequired;
		browser = whichBrowser;
		commonProps = CommonProperties.getInstance();
		
		portletLogMessages = new ArrayList<String>();
		seleniumLogMessages = new ArrayList<String>();
		
		// TODO:  For now firefox will be used.  Once browser support has been figured out for other browsers
		// (specifically third party ones for Chrome and Safari), this will be changed to a switch statement
		// to select a web driver (based on the enumeration value)
		if(newWindowRequired) {
			
			this.loadNewBrowserTab();

			portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		
		
		
	}
	
	protected BaseFunctions(String site, String viewPath, String welcomePath, String userName, String passWord, boolean newWindowRequired) {
		this(site, viewPath, welcomePath, userName, passWord, newWindowRequired, BrowserEnum.FIREFOX);
	}
	
	protected BaseFunctions(String site, String viewPath, String welcomePath, String userName, String passWord, WebDriver existingDriver) {
		this(site, viewPath, welcomePath, userName, passWord, false);
		
		portletDriver = existingDriver;
		portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	// To prevent access to the default constructor
	private BaseFunctions() {
		
	}
	
	public void login(boolean newWindowNeeded) {
		
		if(newWindowNeeded) {
			this.loadNewBrowserTab();
		}
		
		if(!(host.contains("http://"))) {
			portletDriver.get("http://" + host + "/" + welcomeScreen);
		}
		else {
			portletDriver.get(host + "/" + welcomeScreen);
		}
		
		// do not maximize an Opera browser because the feature was never implemented
		try {
			portletDriver.manage().window().maximize();
		} catch(UnsupportedOperationException uo) {
			seleniumLogMessages.add("The Opera browser is being used and a method that has not been implemented in its driver has been used.  Here is more information:  \n" + LogfileManager.extractStackTrace(uo));
		}
		
		portletDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		
		portletDriver.findElement(By.id("sign-in")).click();
		
		portletDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		portletDriver.navigate().refresh();
		
		
		WebElement usernameBox = portletDriver.findElement(By.id("_58_login"));
		WebElement passwordBox = portletDriver.findElement(By.id("_58_password"));
		
		
		usernameBox.click();
		
		
		usernameBox.clear();
		
		
		usernameBox.sendKeys(username);
		
		
		passwordBox.click();
		
		
		passwordBox.clear();
		
		passwordBox.sendKeys(password);
		
		portletLogMessages.add("Logging in with username " + username);
		
		portletDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		System.out.println("Clicking sign in button");
		portletDriver.findElement(By.xpath("//input[@value='Sign In']")).click();
		
		portletDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
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
		if(!(host.contains("http://"))) {
			portletDriver.get("http://" + host + "/" + portletPage);
		}
		else {
			portletDriver.get(host + "/" + portletPage);
		}
		
		portletDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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
	
	public boolean loginNeeded() {
		return loginNeeded();
	}
	
	public void setLoginNeeded(boolean newFlag) {
		loginNeeded = newFlag;
	}
	
	public void setUsername (String newUser) {
		username = newUser;
	}
	
	public void setPassword (String newPassword) {
		password = newPassword;
	}
	
	public WebDriver getDriver() {
		return portletDriver;
	}
	
	protected void loadNewBrowserTab() {
		switch(this.browser) {
		case FIREFOX:
			portletDriver = new FirefoxDriver();
			break;
		case INTERNETEXPLORER:
			portletDriver = new InternetExplorerDriver();
			break;
		case CHROME:
			portletDriver = new ChromeDriver();
			break;
		case OPERA12:
			String driverLocation = commonProps.getBrowserDriver();
			if(driverLocation != null) {
				DesiredCapabilities settings = DesiredCapabilities.opera();
				settings.setCapability("opera.binary", driverLocation);
				settings.setCapability("opera.profile", "");
				settings.setCapability("opera.port", -1);
				portletDriver = new OperaDriver(settings);
			}
			break;
		case SAFARI:
			portletDriver = new SafariDriver();
			break;
		default:
			System.out.println("Unrecognized browser option in the global_properties.config file, reverting to Firefox");
			portletDriver = new FirefoxDriver();
			break;
		}
	}


}

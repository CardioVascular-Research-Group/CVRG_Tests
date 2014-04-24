package edu.jhu.cvrg.seleniummain;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.jhu.cvrg.authenticationtests.GlobusLogin;
import edu.jhu.cvrg.ceptests.CEPException;
import edu.jhu.cvrg.ceptests.CEPTestProperties;
import edu.jhu.cvrg.ceptests.search.CEPSearchTester;
import edu.jhu.cvrg.ceptests.upload.CEPUploadTester;
import edu.jhu.cvrg.waveformtests.WaveformTestProperties;
import edu.jhu.cvrg.waveformtests.analyze.AnalyzeTester;
import edu.jhu.cvrg.waveformtests.upload.UploadTester;
import edu.jhu.cvrg.waveformtests.visualize.VisualizeTester;

public class TestController {
	
	private Calendar todaysDate;
	private DateFormat dateFormat;
	private LogfileManager logger;
	private String hostname;
	private String logfilePath;
	private String username;
	private String password;
	private String initialWelcomePath = "web/guest/home";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length != 5 || args[0].equals("--help")) {
			System.out.println("Usage:  CVRG_Tests.jar <LOGON | WAVEFORM | CEP | ALL> <hostname> <username> <password> <logfile_location>\n");
			System.exit(0);
		}

		String testType = args[0];
		String hostname = args[1];
		String username = args[2];
		String password = args[3];
		String logfilePath = args[4];

		String commonPropsLocation = "./src/testconfig/global_properties.config";
		String waveformPropsLocation = "./src/testconfig/waveform_properties.config";
		String cepPropsLocation = "./src/testconfig/cep_properties.config";
		
		
		
		TestController mainControl = new TestController(hostname, logfilePath, username, password);
		
		TestControlTypeEnum testTypeEnum = TestControlTypeEnum.valueOf(testType);
		
		switch(testTypeEnum) {
			case LOGON:
				mainControl.testAuthentication(commonPropsLocation);
				break;
			case WAVEFORM:
				break;
			case CEP:
				mainControl.testCEPTools(cepPropsLocation);
				break;
			case ALL:
				break;
			default:
				// Exit
				System.out.println("Invalid test option entered.\n");
				System.out.println("Usage:  CVRG_Tests.jar <LOGON | WAVEFORM | CEP | ALL> <hostname> <username> <password> <logfile_location>\n");
				System.exit(0);
				break;
		}
		
	}
	
	public TestController(String newHostname, String newLogfilePath, String newUsername, String newPassword) {
		hostname = newHostname;
		logfilePath = newLogfilePath;
		username = newUsername;
		password = newPassword;
		
		dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	}
	
	public void testAuthentication(String propertiesFileLocation) {
		CommonProperties commonProps = CommonProperties.getInstance();
		
		try {
			setup();
			
			commonProps.loadConfiguration(propertiesFileLocation);
			
			String mainUser = commonProps.getMainUser();
			String mainPassword = commonProps.getMainPassword();
			String newUser = commonProps.getAltUser();
			String newPassword = commonProps.getAltPassword();
			
			GlobusLogin gLogin = new GlobusLogin(hostname, initialWelcomePath, mainUser, mainPassword, true);
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
	
	public void testWaveform(String propertiesFileLocation) {
		
		WaveformTestProperties testProps = WaveformTestProperties.getInstance();
		
		try {
			setup();
			
			testProps.loadConfiguration(propertiesFileLocation);
			
			LogfileManager logger = LogfileManager.getInstance();
			logger.addToLog("Waveform 3 Selenium Test Begin:  " + dateFormat.format(todaysDate.getTime()));
		
			String uploadPath = testProps.getUploadPath();
			String visualizePath = testProps.getVisualizePath();
			String welcomePath = testProps.getWelcomePath();
			String analyzePath = testProps.getAnalyzePath();
			
			UploadTester upload = new UploadTester(hostname, uploadPath, welcomePath, username, password, true);
			
			
			upload.login();
			upload.validateFolderTree();
			upload.uploadFile();
			upload.logout();
			
			AnalyzeTester analysis = new AnalyzeTester(hostname, analyzePath, welcomePath, username, password, true);
			
			analysis.login();
	
			analysis.analyzeOneECG();
	
			
			
			VisualizeTester visualize = new VisualizeTester(hostname, visualizePath, welcomePath, username, password, true);
			
			visualize.login();
			visualize.goToPage();
			visualize.testVisualizeViews();
			
	
			logger.addToLog("Waveform 3 Selenium Tests Completed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void testCEPTools(String propertiesFileLocation) {
		
		
		CEPTestProperties testProps = CEPTestProperties.getInstance();
		
		try {
			setup();
		
			testProps.loadConfiguration(propertiesFileLocation);
			LogfileManager logger = LogfileManager.getInstance();
			
			logger.addToLog("CEP Tools Selenium Test Begin:  " + dateFormat.format(todaysDate.getTime()));
			
			String uploadpath = testProps.getUploadpath();
			String searchpath = testProps.getSearchpath();
			
			CEPUploadTester upload = new CEPUploadTester(hostname, uploadpath, initialWelcomePath, username, password, true);
			
			upload.login(false);
			upload.goToPage();

			upload.runAllTests();
			upload.logout();
			upload.close();
			
			CEPSearchTester search = new CEPSearchTester(hostname, searchpath, initialWelcomePath, username, password, true);

			search.login(false);
			search.goToPage();

			search.runAllTests();
			search.logout();
			search.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	private void setup() throws IOException {
		LogfileManager logger = LogfileManager.getInstance();
		logger.setLogfileLocation(logfilePath);
		todaysDate = Calendar.getInstance();
		logger.addToLog("Main Selenium Test Begin:  " + dateFormat.format(todaysDate.getTime()));
	}

}

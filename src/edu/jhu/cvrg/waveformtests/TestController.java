package edu.jhu.cvrg.waveformtests;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.jhu.cvrg.waveformtests.analyze.AnalyzeTester;
import edu.jhu.cvrg.waveformtests.upload.UploadTester;
import edu.jhu.cvrg.waveformtests.visualize.VisualizeTester;

public class TestController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length != 5) {
			System.out.println("Usage:  WaveformTests.jar <hostname> <username> <password> <logfile_location> <properties_file> \n");
			System.exit(1);
		}

		String hostname = args[0];
		String logfilePath = args[3];
		String username = args[1];
		String password = args[2];
		String propertiesLocation = args[4];
		
		System.out.println(propertiesLocation);
		
		TestProperties testProps = TestProperties.getInstance();
		
		LogfileManager logger = LogfileManager.getInstance();
		logger.setLogfileLocation(logfilePath);
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar todaysDate = Calendar.getInstance();
		
		try {
			testProps.loadConfiguration(propertiesLocation);
			
			logger.addToLog("Waveform 3 Selenium Test Begin:  " + dateFormat.format(todaysDate.getTime()));
		
			String uploadPath = testProps.getUploadPath();
			String visualizePath = testProps.getVisualizePath();
			String welcomePath = testProps.getWelcomePath();
			String analyzePath = testProps.getAnalyzePath();
			
			UploadTester upload = new UploadTester(hostname, uploadPath, welcomePath, username, password);
			
			
			upload.login();
			upload.validateFolderTree();
			upload.uploadFile();
			upload.logout();
			
			AnalyzeTester analysis = new AnalyzeTester(hostname, analyzePath, welcomePath, username, password);
			
			analysis.login();
	
			analysis.analyzeOneECG();
	
			
			
			VisualizeTester visualize = new VisualizeTester(hostname, visualizePath, welcomePath, username, password);
			
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

}

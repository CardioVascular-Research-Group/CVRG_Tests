package edu.jhu.cvrg.seleniummain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import edu.jhu.cvrg.waveformtests.TestNameEnum;

public class LogfileManager {

	private static final LogfileManager logger = new LogfileManager();
	private FileWriter logWriter;
	private String logfileLocation;
	private int uploadFails = 0;
	private int uploadSuccesses = 0;
	private int analyzeFails = 0;
	private int analyzeSuccesses = 0;
	private int visualizeFails = 0;
	private int visualizeSuccesses = 0;
	private int downloadSuccesses = 0;
	private int downloadFails = 0;
	private int loginFails = 0;
	private int loginSuccesses = 0;
	
	
	private LogfileManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void setLogfileLocation(String logfile) {
		logfileLocation = logfile;
	}
	
	public void addToLog(String message) throws IOException {
		openFile();
		
		logWriter.write("\n" + message + "\n");
		
		logWriter.close();
	}
	
	public void addToLog(ArrayList<String> logMessages, TestNameEnum testName) throws IOException {

		openFile();
		
		switch(testName) {
			case SELENIUM:
				logWriter.write("\n***** ALERT!!! The following error messages were generated from Selenium itself *****\n");
				logWriter.write("***** This is most likely either a bug in a newly made test or *****\n");
				logWriter.write("***** an update in the application that invalidated a previous one *****\n");
				break;
			case LOGIN:
				logWriter.write("\n** Results from Authentication **\n");
				logWriter.write("Tests Passed:  " + loginSuccesses + "\n");
				logWriter.write("Tests Failed:  " + loginFails + "\n");
				break;
			case UPLOAD:
				logWriter.write("\n** Results from Upload **\n");
				logWriter.write("Tests Passed:  " + uploadSuccesses + "\n");
				logWriter.write("Tests Failed:  " + uploadFails + "\n");
				break;
			case VISUALIZE:
				logWriter.write("\n** Results from Visualize **\n");
				logWriter.write("Tests Passed:  " + visualizeSuccesses + "\n");
				logWriter.write("Tests Failed:  " + visualizeFails + "\n");
				break;
			case ANALYZE:
				logWriter.write("\n** Results from Analyze **\n");
				logWriter.write("Tests Passed:  " + analyzeSuccesses + "\n");
				logWriter.write("Tests Failed:  " + analyzeFails + "\n");
				break;
			case DOWNLOAD:
				logWriter.write("\n** Results from Download **\n");
				logWriter.write("Tests Passed:  " + downloadSuccesses + "\n");
				logWriter.write("Tests Failed:  " + downloadFails + "\n");
				break;
			default:
				logWriter.write("\n* Other Waveform Results *\n");
		}
		
		for(String logMessage : logMessages) {
			logWriter.write("\n" + logMessage + "\n");
		}
		
		logWriter.write("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		
		logWriter.close();
	}
	
	private void openFile() throws IOException {
		File logFileHandle = new File(logfileLocation);
		
		if(logFileHandle.createNewFile()) {
			logWriter = new FileWriter(logFileHandle, false); // do not append
		}
		else {
			logWriter = new FileWriter(logFileHandle, true); // append 
		}
	}
	
	public void incrementUploadSuccess() {
		uploadSuccesses++;
	}
	
	public void incrementUploadFails() {
		uploadFails++;
	}
	
	public void incrementAnalyzeSuccess() {
		analyzeSuccesses++;
	}
	
	public void incrementAnalyzeFails() {
		analyzeFails++;
	}
	
	public void incrementVisualizeSuccess() {
		visualizeSuccesses++;
	}
	
	public void incrementVisualizeFails() {
		visualizeFails++;
	}
	
	public void incrementLoginSuccess() {
		loginSuccesses++;
	}
	
	public void incrementLoginFails() {
		loginFails++;
	}
	
	public static String extractStackTrace(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		
		ex.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
	
	public static LogfileManager getInstance() {
		return logger;
	}

}

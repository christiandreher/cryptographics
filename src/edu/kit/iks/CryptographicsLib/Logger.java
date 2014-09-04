/**
 * Copyright (c) 2014 Matthias Jaenicke <matthias.jaenicke@student.kit.edu>,
 * Matthias Plappert <undkc@student.kit.edu>,
 * Julien Duman <uncyc@student.kit.edu>, 
 * Christian Dreher <uaeef@student.kit.edu>,
 * Wasilij Beskorovajnov <uajkm@student.kit.edu> and 
 * Aydin Tekin <aydin.tekin@student.kit.edu>
 * 
 * Released under the MIT license (refer to LICENSE.md)
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.kit.iks.CryptographicsLib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class Logger {
	
	/**
	 * Debug mode initially turned off (no debug outputs in console)
	 */
	private static boolean debugMode = false;
	
	/**
	 * Name of the log file 
	 */
	private static final String LOG_FILE_NAME = "statistics.csv";
	
	/**
	 * Name of the error log file
	 */
	private static final String ERROR_LOG_FILE = "error.log";
	
	/**
	 * Check whether debug mode is activated or not
	 * 
	 * @return {true}, if debug mode is activated, {false} if not
	 */
	public static boolean isDebugModeActive() {
		return Logger.debugMode;
	}
	
	/**
	 * Sets the debug mode 
	 */
	public static void setDebugMode() {
		Logger.debugMode = true;
	}
	
	/**
	 * Writes a new entry line in the logfile in this format:
	 * {logEntry};{UNIX timestamp}
	 * 
	 * @param logEntry Action which should be logged
	 */
	public static void log(String logEntry) {
		String unixTimestamp = Logger.getUnixTimestamp();
		
		if (Logger.isDebugModeActive()) {
			System.out.println("[Log]: (write '" + Logger.LOG_FILE_NAME + "') " + logEntry);
		}
		
    	Logger.writeLogFileLine(logEntry, unixTimestamp);
	}
	
	/**
	 * Writes a new debug output to the console
	 * 
	 * @param classID Class name of this debug output
	 * @param method Method name of this debug output
	 * @param debugText Debug text which should be printed
	 */
	public static void debug(String classID, String method, String debugText) {
		if (Logger.debugMode) {
			
			System.out.println("[Debug]: " + classID + "::" + method + "() - " + debugText);
		}
	}
	
	/**
	 * In production mode, this method writes exceptions into the error log file,
	 * in debug mode, this method prints the stacktrace into the console.
	 * 
	 * @param exception Exception to trace
	 */
	public static void error(Exception exception) {
		if (Logger.debugMode) {
			exception.printStackTrace();
		} else {
			String unixTimestamp = getUnixTimestamp();
			
			StringWriter stacktrace = new StringWriter();
			exception.printStackTrace(new PrintWriter(stacktrace));
			
			writeErrorLogFileLine(stacktrace.toString(), unixTimestamp);
		}
	}
	
	/**
	 * Helper method to write a new line into the logfile. Creates a logfile, if
	 * it doesn't exist already
	 * 
	 * @param logEntry Log entry to be written
	 * @param formattedDate To string formatted timestamp to be written
	 */
	private static void writeLogFileLine(String logEntry, String formattedDate) {
		
		String logEntryLine = logEntry + ";" + formattedDate + "\n";
		File file = new File(Logger.LOG_FILE_NAME);
 
		// If file doesn't exists, then create it and append header
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Logger.writeLogFileLine("Performed action", "Unix Timestamp (milliseconds)");
		}
 
		Logger.writeFile(file, logEntryLine);
	}
	
	private static void writeErrorLogFileLine(String error, String formattedDate) {
		
		File file = new File(Logger.ERROR_LOG_FILE);
		
		String lines = "Exception occured at " + formattedDate + "\n"
    				+ error;
 
		// If file doesn't exists, then create it and append header
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			lines = "\n\n==============================\n" + lines;
		}
		
		Logger.writeFile(file, lines);
	}
	
	/**
	 * Writes a line to a file. If more than one file needs to be written,
	 * don't run this method in a loop. Rather indicate new lines with \n
	 * and pass one string to this method instead.
	 * 
	 * @param file The file to write the line
	 * @param lines The line(s) to write
	 */
	private static void writeFile(File file, String lines) {
		try {
			// true = append file
			FileWriter fileWritter = new FileWriter(file.getName(), true);
		    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		    bufferWritter.write(lines);
		    bufferWritter.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Helper method to get unix timestamp
	 * 
	 * @return String of unix timestamp
	 */
	private static String getUnixTimestamp() {
		Date date = new java.util.Date();
		long unixTimestamp = date.getTime();
		
		return String.valueOf(unixTimestamp);
	}
}

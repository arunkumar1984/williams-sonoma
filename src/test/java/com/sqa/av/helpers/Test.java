package com.sqa.av.helpers;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Test {
	// ArayList to hold test data
	public static ArrayList<Object> tests = new ArrayList<Object>();

	// StringBuilder class in place of String class so that less objects are
	// created, more efficient when dealing with String concatenation
	public static StringBuilder testString = new StringBuilder();

	public static void main(String[] args) {
		// The name of the file to open.
		// String fileNameOld = "temp.txt";

		// File name should include directory path and relative location
		String fileNameRel = "src/main/resources/temp.json";

		// File name should include directory path and absolute location
		String fileNameAbs = "/Users/Arun/Documents/workspace/Basic-Project/src/main/resources/temp.json";

		// This will reference one line at a time. This is initially set to NULL
		// to state the start of file and has not commenced reading it
		String line = null;

		try {
			// FileReader reads text files in the default encoding.

			// Location of Absolute file path
			// FileReader fileReader = new FileReader(fileNameAbs);

			// If relative location, use the below line
			FileReader fileReader = new FileReader(fileNameRel);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// While there is a line to read or not NULL line (EOF) (and also,
			// setting line to current line)
			while ((line = bufferedReader.readLine()) != null) {
				// Pass current line to gatherDataString method
				gatherDataString(line);
			}
			evaluateDataString();
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileNameRel + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileNameRel + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		displayTests();
	}

	public static void mainNew(String args[]) {

		// String to be scanned to find the pattern.
		String line = "This order was placed for QT3000! OK?";
		String pattern = "(.*)(\\d+)(.*)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		if (m.find()) {
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
		} else {
			System.out.println("NO MATCH");
		}
	}

	private static void displayTests() {
		System.out.println(testString.toString());

	}

	private static void evaluateDataString() {
		// String[] tests = testString.toString().split("},{");
		// for(int i = 0; i < tests.length; i++) {
		// //String[] elements = "\d";
		// "\d+"
		// }
		// String regexString = "\\"\\d+\\"";

		// Gather all content from file into a String object
		String myString = testString.toString();

		// Regex Pattern to specify format of text document
		// String pattern = "(\")(\\w+\\d+)(\")";
		// String pattern = "(\")(num\\d+)(\")"; // Pattern staring with "num"
		String pattern = "(\")(\\d+)(\")"; // Pattern staring with "num"

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(myString);

		while (m.find()) {
			// Group 0 displays entire output --> "digit". Eg., "5"
			// Group 1 displays first component --> ". Eg., "
			// Group 2 displays first component --> digit. Eg., 5
			// Group 3 displays first component --> ". Eg., "

			System.out.println("Found value: " + m.group(0));
		}
	}

	private static void gatherDataString(String line) {
		// Add kind of code to ArrayList Collection
		testString.append(line);
		// System.out.println(line);
	}

	// private static void displayTests() {
	// System.out.println("Number of tests:" + tests.size());
	// for(int i = 0; i < tests.size(); i++) {
	// String[] parameters = (String[])tests.get(i);
	// System.out.print(parameters[0] + ",");
	// System.out.print(parameters[1] + ",");
	// System.out.println(parameters[2]);
	// }
	// }

	// private static void evaluateLine(String line) {
	// String[] parameters = new String[3];
	// String[] elements = line.split(",");
	// parameters[0] = elements[0].split("=")[1];
	// parameters[1] = elements[1].split("=")[1];
	// parameters[2] = elements[2].split("=")[1];
	// tests.add(parameters);
	// }
}
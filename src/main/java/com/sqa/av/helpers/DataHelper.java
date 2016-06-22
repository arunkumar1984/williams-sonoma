/**
 *   File Name: DataHelper.java<br>
 *
 *   LastName, FirstName<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Jun 13, 2016
 *
 */

package com.sqa.av.helpers;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import com.sqa.av.helpers.data.*;
import com.sqa.av.helpers.exceptions.*;

/**
 * DataHelper //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author LastName, FirstName
 * @version 1.0.0
 * @since 1.0
 *
 */
public class DataHelper {

	public static Object[][] evalDatabaseTable(String driverClassString, String databaseStringUrl, String username,
			String password, String tableName) throws ClassNotFoundException, SQLException {

		return evalDatabaseTable(driverClassString, databaseStringUrl, username, password, tableName, 0, 0);
	}

	public static Object[][] evalDatabaseTable(String driverClassString, String databaseStringUrl, String username,
			String password, String tableName, int rowOffset, int colOffset)
			throws ClassNotFoundException, SQLException {

		Object[][] myData;
		ArrayList<Object> myArrayData = new ArrayList<Object>();

		Class.forName(driverClassString);

		Connection dbconn = DriverManager.getConnection(databaseStringUrl, username, password);

		Statement stmt = dbconn.createStatement();

		ResultSet rs = stmt.executeQuery("select * from " + tableName);

		int numOfColumns = rs.getMetaData().getColumnCount();
		int curRow = 1;

		while (rs.next()) {
			if (curRow > rowOffset) {
				Object[] rowData = new Object[numOfColumns - colOffset];

				for (int i = 0, j = colOffset; i < rowData.length; i++) {
					rowData[i] = rs.getString(i + colOffset + 1);
				}

				myArrayData.add(rowData);

			}
			curRow++;

		}

		myData = new Object[myArrayData.size()][];

		for (int i = 0; i < myData.length; i++) {
			myData[i] = (Object[]) myArrayData.get(i);
		}

		// Step 5
		rs.close();
		stmt.close();
		dbconn.close();

		return myData;
	}

	public static Object[][] getExcelFileData(String fileLocation, String fileName, Boolean hasLabels) {
		// Object[][] data = { { "Square", 49, 7, 0 }, { "Circle", 113, 6, 0 },
		// { "Rectangle", 45, 9, 5 } };
		ArrayList<Object> results = new ArrayList<Object>();
		Object[][] resultsObject;

		// TODO Implement Helper Method

		try {

			// Get File based on class loader (Setup Needed)
			// ClassLoader classLoader = ApachePOITest.class.getClassLoader();
			//
			// Get InputStream via Class Loader (Setup Needed), meaning resource
			// folder:
			// InputStream file =
			// classLoader.getResourceAsStream("poi-example.xls");

			// Get the file using basic File and relative path to directory,
			// meaning root folder
			String fullFilePath = fileLocation + fileName;
			InputStream newExcelFormatFile = new FileInputStream(new File(fullFilePath));

			// Reads an XLS file
			// InputStream newExcelFormatFile = new FileInputStream(new
			// File("poi-example.xlsx"));

			// Reads an XLSX file
			// InputStream newExcelFormatFile = new FileInputStream(new
			// File("poi-example.xlsx"));

			// Get the workbook instance for XLS file.To support XLS file, use
			// HSSF instead of XSSF
			XSSFWorkbook workbook = new XSSFWorkbook(newExcelFormatFile);

			// Get first sheet from the workbook. The parameter (0) indicated
			// first sheet. To use sheet 'name', use getSheet(<NAME>)
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			// Move down one row if there is a label
			if (hasLabels) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				ArrayList<Object> rowData = new ArrayList<Object>();

				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					// Gather and print contents
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						// System.out.println("Calling a boolean value!!!!");
						System.out.print(cell.getBooleanCellValue() + "\t\t\t");
						rowData.add(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print((int) cell.getNumericCellValue() + "\t\t\t");
						rowData.add((int) cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						rowData.add(cell.getStringCellValue());
						break;
					}
				}
				Object[] rowDataObject = new Object[rowData.size()];
				rowData.toArray(rowDataObject);
				results.add(rowDataObject);
				System.out.println("");
			}
			// Close File Read Stream
			newExcelFormatFile.close();
			// Create an OutputStream to write
			FileOutputStream out = new FileOutputStream(new File("src/main/resources/excel-output.xlsx"));

			// Write the workbook
			workbook.write(out);
			// Close output Stream
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		resultsObject = new Object[results.size()][];
		results.toArray(resultsObject);
		return resultsObject;

	}

	// // TODO check for labels first line
	// if (hasLabels) {
	// // Remove any labels present
	// lines.remove(0);
	// }
	// System.out.println("My Labels:" + lines);

	public static Object[][] getTextFileData(String fileLocation, String fileName, TextFormat textFormat) {
		return getTextFileData(fileLocation, fileName, textFormat, false);
	}

	public static Object[][] getTextFileData(String fileLocation, String fileName, TextFormat textFormat,
			Boolean hasLabels, Object... dataTypes) {
		// Process data
		Object[][] data;
		// Collect data lines from text document supplied
		ArrayList<String> lines = openFileAndCollectData(fileLocation, fileName);
		switch (textFormat) {
		case CSV:
			data = parseCSVData(lines, hasLabels, dataTypes);
			break;
		case XML:
			data = parseXMLData(lines, hasLabels);
			break;
		case TAB:
			data = parseTabData(lines, hasLabels);
			break;
		case JSON:
			data = parseJSONData(lines, hasLabels);
			break;
		default:
			data = null;
			break;
		}
		return data;
	}

	public static Object[][] getTextFileData(String fileLocation, String fileName, TextFormat textFormat,
			Object... dataTypes) {
		return getTextFileData(fileLocation, fileName, textFormat, false, dataTypes);
	}

	/**
	 * @param group
	 * @param class1
	 * @return
	 */
	private static Object convertDataType(String parameter, Object dataType) {
		try {
			if (dataType.equals(Integer.TYPE)) {
				return Integer.parseInt(parameter);
			} else if (dataType.equals(Boolean.TYPE)) {
				if (parameter.equalsIgnoreCase("true") | parameter.equalsIgnoreCase("false")) {
					return Boolean.parseBoolean(parameter);
				} else {
					throw new BooleanFormatException();
				}

			} else {
				System.out
						.println("Data type is a String or not recognized, returning a String for (" + parameter + ")");
				return parameter;
			}
		} catch (NumberFormatException e) {
			System.err.println("Number is not in correct format (" + parameter + ")");
			return 0;
		} catch (BooleanFormatException e) {
			System.err.println("Boolean value is not a correct String (" + parameter + ")");
			return false;
		}
	}

	/**
	 * @param fileLocation
	 * @param fileName
	 * @return
	 */
	private static ArrayList<String> openFileAndCollectData(String fileLocation, String fileName) {
		// Create a full relative file path
		String fullFilePath = fileLocation + fileName;
		// Array to hold lines from file
		ArrayList<String> dataLines = new ArrayList<String>();
		try {

			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fullFilePath);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// Create String to hold current line
			String line = bufferedReader.readLine();
			// While there is a line to read or not null line
			// (also setting line to current line)
			while (line != null) {
				// Pass current line to the gatherDataString Method
				dataLines.add(line);
				line = bufferedReader.readLine();
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fullFilePath + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fullFilePath + "'");
			// Alternative we could just do this:
			// ex.printStackTrace();
		}
		return dataLines;
	}

	/**
	 * @param lines
	 * @param objects
	 * @return
	 */
	private static Object[][] parseCSVData(ArrayList<String> lines, boolean hasLabels, Object[] dataTypes) {

		ArrayList<Object> results = new ArrayList<Object>();
		// Check for labels on first line
		if (hasLabels) {
			// Remove any labels present
			lines.remove(0);
		}

		// String to be scanned to find the pattern.
		String pattern = "(,*)([a-zA-Z0-9\\s]+)(,*)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		for (int i = 0; i < lines.size(); i++) {
			int curDataType = 0;
			ArrayList<Object> curMatches = new ArrayList<Object>();
			Matcher m = r.matcher(lines.get(i));
			while (m.find()) {
				if (dataTypes.length > 0) {
					try {
						curMatches.add(convertDataType(m.group(2).trim(), dataTypes[curDataType]));
					} catch (Exception e) {
						System.out.println("DataTypes provided do not match parsed data results.");
					}
				} else {
					curMatches.add(m.group(2).trim());
				}
				curDataType++;
			}
			Object[] resultsObj = new Object[curMatches.size()];
			curMatches.toArray(resultsObj);
			results.add(resultsObj);
		}
		System.out.println("Results:" + results);
		Object[][] resultsObj = new Object[results.size()][];
		results.toArray(resultsObj);
		return resultsObj;
	}

	/**
	 * @param lines
	 * @param hasLabels
	 * @return
	 */
	private static Object[][] parseJSONData(ArrayList<String> lines, Boolean hasLabels) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param lines
	 * @param hasLabels
	 * @return
	 */
	private static Object[][] parseTabData(ArrayList<String> lines, Boolean hasLabels) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param lines
	 * @param hasLabels
	 * @return
	 */
	private static Object[][] parseXMLData(ArrayList<String> lines, Boolean hasLabels) {
		// TODO Auto-generated method stub
		return null;
	}
}
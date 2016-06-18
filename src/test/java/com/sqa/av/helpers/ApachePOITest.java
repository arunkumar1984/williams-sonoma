package com.sqa.av.helpers;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.testng.annotations.*;

public class ApachePOITest {
	@Test
	public void test() {
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
			InputStream oldExcelFormatFile = new FileInputStream(new File("poi-example.xls"));

			// Reads an XLS file
			InputStream newExcelFormatFile = new FileInputStream(new File("poi-example.xlsx"));

			// Reads an XLSX file
			// InputStream newExcelFormatFile = new FileInputStream(new
			// File("poi-example.xlsx"));

			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(newExcelFormatFile);

			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
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
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t\t\t");
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						break;
					}
				}
				System.out.println("");
			}
			// Close File Read Stream
			newExcelFormatFile.close();
			// Create an OutputStream to write
			FileOutputStream out = new FileOutputStream(new File("src/main/resources/excel-output.xls"));
			// Write the workbook
			workbook.write(out);
			// Close output Stream
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

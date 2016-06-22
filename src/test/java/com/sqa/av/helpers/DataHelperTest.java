package com.sqa.av.helpers;

import org.testng.*;
import org.testng.annotations.*;

import com.sqa.av.helpers.data.*;

public class DataHelperTest {
	// @DataProvider
	// public Object[][] dpMySQL() throws ClassNotFoundException, SQLException {
	// return DataHelper.evalDatabaseTable("com.mysql.jdbc.Driver",
	// "jdbc:mysql://localhost:8889/sqadb", "root",
	// "root", "amazon");
	// }
	//
	// @DataProvider
	// public Object[][] dpMySQLOS() throws ClassNotFoundException, SQLException
	// {
	// return DataHelper.evalDatabaseTable("com.mysql.jdbc.Driver",
	// "jdbc:mysql://localhost:8889/sqadb", "root",
	// "root", "amazon", 1, 1);
	// }
	//
	// @DataProvider
	// public Object[][] dpPostgres() throws ClassNotFoundException,
	// SQLException {
	// Object[][] data = DataHelper.evalDatabaseTable("org.postgresql.Driver",
	// "jdbc:postgresql://localhost:5432/autodb", "postgres", "postgres",
	// "monster");
	//
	// return data;
	// }
	//
	// @DataProvider
	// public Object[][] dpPostgresOSE() throws ClassNotFoundException,
	// SQLException {
	// Object[][] data = DataHelper.evalDatabaseTable("org.postgresql.Driver",
	// "jdbc:postgresql://localhost:5432/autodb", "postgres", "postgres",
	// "monster", 1, 1);
	//
	// return data;
	// }

	@DataProvider(name = "textData")
	public Object[][] getData() {
		Object[][] data;
		data = DataHelper.getTextFileData("src/main/resources/", "data.csv", TextFormat.CSV, true);
		DisplayHelper.multArray(data);
		return data;
	}

	@DataProvider(name = "textDataTyped")
	public Object[][] getDataTyped() {
		Object[][] data;
		data = DataHelper.getTextFileData("src/main/resources/", "data.csv", TextFormat.CSV, true, Integer.TYPE,
				Boolean.TYPE);
		DisplayHelper.multArray(data);
		return data;
	}

	@DataProvider(name = "textDataTypedWithString")
	public Object[][] getDataTypedWithString() {
		Object[][] data;
		data = DataHelper.getTextFileData("src/main/resources/", "data2.csv", TextFormat.CSV, true, String.class,
				Integer.TYPE, Boolean.TYPE);
		DisplayHelper.multArray(data);
		return data;
	}

	// @Test(dataProvider = "dpMySQL", priority = 1)
	// public void testAmazon(Object id, Object name, Object quantity) {
	// System.out.println("Test DP with mySQL: " + name);
	// }
	//
	// @Test(dataProvider = "dpMySQLOSE", priority = 3)
	// public void testAmazonOffset(Object name, Object quantity) throws
	// ClassNotFoundException, SQLException {
	// Display.display2DArray(dpMySQLOS());
	// System.out.println("Test DP with mySQL and Offset: " + name);
	// }
	//
	// @Test(dataProvider = "dpPostgres", priority = 2)
	// public void testMonster(Object id, Object keywords, Object results) {
	// System.out.println("Test DP with Postgres: " + keywords);
	// }
	//
	// @Test(dataProvider = "dpPostgres", priority = 4)
	// public void testMonsterOSE(Object id, Object keywords, Object results) {
	// System.out.println("Test DP with Postgres: " + keywords);
	// }

	@Test(dataProvider = "textData")
	public void testReadingFile(String number, String isPrime) {
		try {
			System.out.println("Number " + number + ", is Prime? (" + isPrime + ")");
			boolean actualResult = isPrime(Integer.parseInt(number));
			Assert.assertEquals(actualResult, Boolean.parseBoolean(isPrime), "Number is not prime based on data set.");

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "textDataTyped")
	public void testReadingFileTyped(int number, boolean isPrime) {
		System.out.println("Number " + number + ", is Prime? (" + isPrime + ")");
		boolean actualResult = isPrime(number);
		Assert.assertEquals(actualResult, isPrime, "Number is not prime based on data set.");
	}

	@Test(dataProvider = "textDataTypedWithString")
	public void testReadingFileTypedWithString(String title, int number, boolean isPrime) {
		System.out.println("Number " + number + ", is Prime? (" + isPrime + ")");
		boolean actualResult = isPrime(number);
		Assert.assertEquals(actualResult, isPrime, "Number is not prime based on data set.");
	}

	private boolean isPrime(int number) {
		boolean isPrime = true;
		for (int i = 2; i <= number / 2; i++) {
			if (number % i == 0) {
				isPrime = false;
			}
		}
		return isPrime;
	}
}

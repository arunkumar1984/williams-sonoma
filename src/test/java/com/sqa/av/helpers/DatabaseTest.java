/**
 *   File Name: DatabaseTest.java<br>
 *
 *   Venkatraman, Arunkumar<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Jun 18, 2016
 *   
 */

package com.sqa.av.helpers;

import java.sql.*;

import org.testng.annotations.*;

/**
 * DatabaseTest //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 * 
 * @author Venkatraman, Arunkumar
 * @version 1.0.0
 * @since 1.0
 *
 */
public class DatabaseTest {
	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void databaseTest() throws ClassNotFoundException, SQLException {
		System.out.println("Database Test: ");
		// Get specific Driver for the DB used
		Class.forName("com.mysql.jdbc.Driver");

		// Create connection to DB using DB string, with username and pwd
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://localhost:8889/sqdb", "root", "root");

		// Create Statement object to execute SQL queries
		Statement statement = dbconn.createStatement();

		// Capture the resultset of an executed SQL statement
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person");// WHERE
																				// name
																				// like
																				// \"A%\"");

		// Iterate through the results to capture to display all items
		while (resultSet.next()) {
			// TODO Display or Capture elements
			int ID = resultSet.getInt(1); // or int ID = resultSet.getInt("ID");
			String Name = resultSet.getString(2);
			String Address = resultSet.getString(3);
			String City = resultSet.getString(4);
			String Zip = resultSet.getString(5);
			System.out.println("ID: " + ID + " - Name: " + Name + " - Address: " + Address + " - City: " + City
					+ " - Zip: " + Zip + "\n");
		}
		resultSet.close();
		statement.close();
		dbconn.close();
	}

}

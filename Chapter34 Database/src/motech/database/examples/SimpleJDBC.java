package motech.database.examples;

import java.sql.*;

public class SimpleJDBC {
	public static void PrintResult() 
		throws SQLException, ClassNotFoundException {
		
		//load the JDBC driver into memory
		//alternate: DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");		
		System.out.println("Driver loaded");
		
		//create the connection to the database.
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost/sakila?noAccessToProcedureBodies=true", "application", "password");
		System.out.println("Database connected\n");
		
/*		//create a SQL statement
		Statement statement = connection.createStatement();
		//execute the SQL query
		ResultSet result = statement.executeQuery("SELECT * FROM city LIMIT 20");
		
		System.out.println("------------------------------------------------------------------------");
		System.out.format("|%-4s| %-25s| %-10s| %-25s| %n", "ID", "City Name", "Country", "Last Update");
		System.out.println("------------------------------------------------------------------------");
		//loop through results and print the results as a formatted list.
		while(result.next()){
			System.out.format("|%-4s| %-25s| %-10s| %-25s| %n",
					result.getString(1), result.getString(2),result.getString(3), result.getString(4));
		}
		System.out.println("------------------------------------------------------------------------");*/
		
	
				
		CallableStatement callStatement = connection.prepareCall("{CALL sakila.film_in_stock(?, ?, ?)}");
		
		callStatement.setInt(1, 1);
		callStatement.setInt(2, 1);
		callStatement.registerOutParameter(3, Types.INTEGER);
		
		callStatement.execute();
		int returnVal = callStatement.getInt(3);
		
		System.out.println(returnVal);
		
		connection.close();	
	}
}

package motech.database.main;

import java.sql.SQLException;

import motech.database.examples.*;

public class Program {
	public static void main(String[] args) 
		throws ClassNotFoundException, SQLException {
		
		//Example 1 - simple connection to Sakila database to print all data in city table.
		//SimpleJDBC.PrintResult();
		
		ShowFilms sf = new ShowFilms();
		sf.init();
	}
}

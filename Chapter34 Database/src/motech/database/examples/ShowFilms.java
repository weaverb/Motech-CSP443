package motech.database.examples;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.TableColumn;

public class ShowFilms extends JApplet {
	private JComboBox<Actor> actorList = new JComboBox<Actor>();
	private JTable resultTable = new JTable();
	private JPanel mainPanel = new JPanel();
	private JScrollPane scrollPane;
	private JButton executeBtn = new JButton("Show Films");
	private Connection connection;

	public void init() {
		//set the applet size
		this.setSize(640, 100);

		initializeDB();

		try {
			Statement getActors = connection.createStatement();
			ResultSet actorsResult = getActors
					.executeQuery("SELECT actor_id, first_name, last_name FROM actor");

			while (actorsResult.next()) {
				Actor actor = new Actor();
				actor.setActorId(actorsResult.getInt(1));
				actor.setActorName(actorsResult.getString(2) + " "
						+ actorsResult.getString(3));
				actorList.addItem(actor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		executeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeBtn_actionPerformed(e);
			}
		});

		mainPanel.add(new JLabel("Actors"));
		mainPanel.add(actorList);
		mainPanel.add(executeBtn);

		add(mainPanel, BorderLayout.NORTH);
	}

	private void initializeDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// create the connection to the database.
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sakila", "application", "password");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void executeBtn_actionPerformed(ActionEvent e) {
		Actor selectedActor = (Actor) actorList.getSelectedItem();
		Vector<String> columnNames = new Vector<String>();
		Vector<Object> data = new Vector<Object>();
		
		int rowCount = 0;
		
		try {
		/*	Statement getFilms = connection.createStatement();
			ResultSet filmsList = getFilms
					.executeQuery("SELECT f.title AS 'Film Title' FROM film AS f "
							+ "JOIN film_actor AS fa ON f.film_id = fa.film_id "
							+ "JOIN actor AS a ON fa.actor_id = a.actor_id "
							+ "WHERE a.actor_id = " + (actorList.getSelectedIndex() + 1) + ";");*/
			
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT f.title AS 'Film Title' FROM film AS f "
					+ "JOIN film_actor AS fa ON f.film_id = fa.film_id "
					+ "JOIN actor AS a ON fa.actor_id = a.actor_id "
					+ "WHERE a.actor_id = ?;");
			
			preparedStatement.setInt(1, actorList.getSelectedIndex() + 1);
			
			
			
			ResultSet filmsList = preparedStatement.executeQuery();
			
	
			

			ResultSetMetaData metaData = filmsList.getMetaData();

			int columns = metaData.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				columnNames.addElement(metaData.getColumnName(i));
			}

			while (filmsList.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i = 1; i <= columns; i++) {
					row.addElement(filmsList.getString(i));
				}
				data.addElement(row);
				rowCount++;
			}
			filmsList.close();
			preparedStatement.close();
			//getFilms.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		resultTable = new JTable(data, columnNames);
		TableColumn column;
		for (int i = 0; i < resultTable.getColumnCount(); i++) {
			column = resultTable.getColumnModel().getColumn(i);
			column.setMaxWidth(250);			
		}
		
		scrollPane = new JScrollPane(resultTable);
		JFrame frame = new JFrame();
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(250, rowCount * 20);
	    frame.setVisible(true);
	}
}

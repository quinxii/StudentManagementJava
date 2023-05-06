package StudentManagement;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentManagement {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.postgresql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/component";
	// Database credentials
	static final String USER = "postgres";
	static final String PASS = "123";
	
	private static JTable tableList;
	private static JTextField addID;
	private static JTextField addFirstName;
	private static JTextField addLastName;
	private static JTextField addDOB;
	private static JTextField addAddress;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StudentManagementGUI();
			}
		});
	}
	
	private static void StudentManagementGUI() {
//		Create and Display window
		JFrame jfrm = new JFrame("BTTL08_20127306");
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setResizable(false);
		jfrm.getContentPane().setEnabled(true);
		jfrm.setBounds(100, 100, 900, 580);
		
		jfrm.getContentPane().setLayout(null);
		jfrm.setVisible(true);
		
//		Set up button
		JLabel newLabel = new JLabel("Student Management");
		newLabel.setBounds(310, 10, 287, 37);
		newLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		newLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jfrm.getContentPane().add(newLabel);
		
		JButton showButton = new JButton("Show list");
		showButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showButton.setBounds(110, 55, 130, 40);
		jfrm.getContentPane().add(showButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(110, 180, 686, 310);
		jfrm.getContentPane().add(scrollPane);

		tableList = new JTable();
		scrollPane.setViewportView(tableList);
		tableList.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "First Name", "Last Name", "DOB", "Address" }));

		JButton addButton = new JButton("Add list");
		addButton.setBounds(250, 55, 130, 40);
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jfrm.getContentPane().add(addButton);
		
		JButton searchButton = new JButton("Search list");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchButton.setBounds(390, 55, 130, 40);
		jfrm.getContentPane().add(searchButton);
		
		JButton updateButton = new JButton("Update list");
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updateButton.setBounds(530, 55, 130, 40);
		jfrm.getContentPane().add(updateButton);
		
		JButton deleteButton = new JButton("Delete list");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deleteButton.setBounds(670, 55, 130, 40);
		jfrm.getContentPane().add(deleteButton);
		
//		Set up Input place
		JLabel id = new JLabel("ID");
		id.setHorizontalTextPosition(SwingConstants.CENTER);
		id.setFont(new Font("Tahoma", Font.PLAIN, 13));
		id.setBounds(110, 120, 78, 21);
		jfrm.getContentPane().add(id);
		
		addID = new JTextField();
		addID.setFont(new Font("Tahoma", Font.PLAIN, 13));
		addID.setBounds(110, 140, 120, 21);
		jfrm.getContentPane().add(addID);
		addID.setColumns(10);
		
		JLabel firstName = new JLabel("First Name");
		firstName.setHorizontalTextPosition(SwingConstants.CENTER);
		firstName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		firstName.setBounds(250, 120, 78, 21);
		jfrm.getContentPane().add(firstName);

		addFirstName = new JTextField();
		addFirstName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		addFirstName.setBounds(250, 140, 120, 21);
		jfrm.getContentPane().add(addFirstName);
		addFirstName.setColumns(10);
		
		JLabel lastName = new JLabel("Last Name");
		lastName.setHorizontalTextPosition(SwingConstants.CENTER);
		lastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lastName.setBounds(390, 120, 78, 21);
		jfrm.getContentPane().add(lastName);

		addLastName = new JTextField();
		addLastName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		addLastName.setBounds(390, 140, 120, 21);
		jfrm.getContentPane().add(addLastName);
		addLastName.setColumns(10);

		JLabel dob = new JLabel("DOB");
		dob.setHorizontalTextPosition(SwingConstants.CENTER);
		dob.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dob.setBounds(530, 120, 78, 21);
		jfrm.getContentPane().add(dob);

		addDOB = new JTextField();
		addDOB.setFont(new Font("Tahoma", Font.PLAIN, 13));
		addDOB.setBounds(530, 140, 120, 21);
		jfrm.getContentPane().add(addDOB);
		addDOB.setColumns(10);
		
		JLabel address = new JLabel("Address");
		address.setHorizontalTextPosition(SwingConstants.CENTER);
		address.setFont(new Font("Tahoma", Font.PLAIN, 13));
		address.setBounds(670, 120, 78, 21);
		jfrm.getContentPane().add(address);

		addAddress = new JTextField();
		addAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		addAddress.setBounds(670, 140, 120, 21);
		jfrm.getContentPane().add(addAddress);
		addAddress.setColumns(10);
		
//		Button Handle
		//1. Show students list.
		showButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String JDBC_DRIVER = "com.postgresql.cj.jdbc.Driver";
					String DB_URL = "jdbc:postgresql://localhost:5432/component";
					// Database credentials
					String USER = "postgres";
					String PASS = "123";
					Connection conn = null;
					Statement stmt = null;
					
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					
					System.out.println("Creating database...");
					stmt = conn.createStatement();
					String sql;
					sql = "SELECT * FROM STUDENT";
					ResultSet rs = stmt.executeQuery(sql);
					DefaultTableModel tableModel = (DefaultTableModel) tableList.getModel();
					tableModel.setRowCount(0);
					while (rs.next()) {
						String id = rs.getString("id");
						String first = rs.getString("firstName");
						String last = rs.getString("lastName");
						String address = rs.getString("address");
						Date dob = rs.getDate("dob");
						Format formatter = new SimpleDateFormat("yyyy-MM-dd");
						String strDob = formatter.format(dob);
						String tableData[] = { id, first, last, strDob, address };
						tableModel.addRow(tableData);
					}
					rs.close();
					conn.close();
					System.out.println("Connection closed.");
				} catch (Exception el) {
					System.out.println(el.getMessage());
				}
			}
			
		});

		//2. Add a new student (student id, first name, last name, date of birth, address).
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String JDBC_DRIVER = "com.postgresql.cj.jdbc.Driver";
					String DB_URL = "jdbc:postgresql://localhost:5432/component";
					// Database credentials
					String USER = "postgres";
					String PASS = "123";
					Connection conn = null;
					Statement stmt = null;
					
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					
					System.out.println("Creating database...");
					stmt = conn.createStatement();
					String sql;
					sql = "INSERT INTO STUDENT(id, firstName, lastName, dob, address)" + "VALUES (?, ?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, addID.getText());
					pstmt.setString(2, addFirstName.getText());
					pstmt.setString(3, addLastName.getText());
					pstmt.setString(4, addDOB.getText());
					pstmt.setString(5, addAddress.getText());
					pstmt.execute();
					pstmt.close();
					DefaultTableModel tableModel = (DefaultTableModel) tableList.getModel();
					tableModel.addRow(new Object[] { addID.getText(), addFirstName.getText(), addLastName.getText(),
							addDOB.getText(), addAddress.getText() });
					addID.setText("");
					addFirstName.setText("");
					addLastName.setText("");
					addDOB.setText("");
					addAddress.setText("");
					conn.close();
					System.out.println("Connection closed.");
				} catch (Exception el) {
					System.out.println(el.getMessage());
				}
			}
		});
	
		//3. Search a student by student id and by name.
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String JDBC_DRIVER = "com.postgresql.cj.jdbc.Driver";
					String DB_URL = "jdbc:postgresql://localhost:5432/component";
					// Database credentials
					String USER = "postgres";
					String PASS = "123";
					Connection conn = null;
					Statement stmt = null;
					
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					
					System.out.println("Creating database...");
					stmt = conn.createStatement();
					String sql;
					sql = "SELECT * FROM student WHERE studentID = '" + addID.getText() + "' AND firstName = '" + addFirstName.getText() + "' AND lastName = '" + addLastName.getText() + "';";
					ResultSet rs = stmt.executeQuery(sql);
					DefaultTableModel tableModel = (DefaultTableModel) tableList.getModel();
					tableModel.setRowCount(0);
					while (rs.next()) {
						String id = rs.getString("id");
						String first = rs.getString("firstName");
						String last = rs.getString("lastName");
						String address = rs.getString("address");
						Date dob = rs.getDate("dob");
						Format formatter = new SimpleDateFormat("yyyy-MM-dd");
						String strDob = formatter.format(dob);
						String tableData[] = { id, first, last, strDob, address };
						tableModel.addRow(tableData);
					}
					rs.close();
					addID.setText("");
					addFirstName.setText("");
					addLastName.setText("");
					addDOB.setText("");
					addAddress.setText("");
					conn.close();
					System.out.println("Connection closed.");
				} catch (Exception el) {
					System.out.println(el.getMessage());
				}
			}
			
		});
	
		//4. Update a student record.
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String JDBC_DRIVER = "com.postgresql.cj.jdbc.Driver";
					String DB_URL = "jdbc:postgresql://localhost:5432/component";
					// Database credentials
					String USER = "postgres";
					String PASS = "123";
					Connection conn = null;
					Statement stmt = null;
					
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					
					System.out.println("Creating database...");
					stmt = conn.createStatement();
					String sql;
					sql = "UPDATE STUDENT SET "
							+ "firstName = ?, "
							+ "lastName = ?, "
							+ "dob = ?, "
							+ "address = ? "
							+ "WHERE id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, addFirstName.getText());
					pstmt.setString(2, addLastName.getText());
					pstmt.setString(3, addDOB.getText());
					pstmt.setString(4, addAddress.getText());
					pstmt.setString(5, addID.getText());
					pstmt.execute();
					pstmt.close();
					DefaultTableModel tableModel = (DefaultTableModel) tableList.getModel();
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						if (((String) tableModel.getValueAt(i, 0)).equals(addID.getText())) {
							tableModel.setValueAt(addFirstName.getText(), i, 1);
							tableModel.setValueAt(addLastName.getText(), i, 2);
							tableModel.setValueAt(addDOB.getText(), i, 3);
							tableModel.setValueAt(addAddress.getText(), i, 4);
							break;
						}
					}
					addID.setText("");
					addFirstName.setText("");
					addLastName.setText("");
					addDOB.setText("");
					addAddress.setText("");
					conn.close();
					System.out.println("Connection closed.");
				} catch (Exception el) {
					System.out.println(el.getMessage());
				}
			}
			
		});
		
		//5. Delete a record.
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String JDBC_DRIVER = "com.postgresql.cj.jdbc.Driver";
					String DB_URL = "jdbc:postgresql://localhost:5432/component";
					// Database credentials
					String USER = "postgres";
					String PASS = "123";
					Connection conn = null;
					Statement stmt = null;
					
					Class.forName(JDBC_DRIVER);
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					
					System.out.println("Creating database...");
					stmt = conn.createStatement();
					String sql;
					sql = "DELETE FROM STUDENT WHERE id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, addID.getText());
					pstmt.execute();
					pstmt.close();
					DefaultTableModel tableModel = (DefaultTableModel) tableList.getModel();
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						if (((String) tableModel.getValueAt(i, 0)).equals(addID.getText())) {
							tableModel.removeRow(i);
							break;
						}
					}
					addID.setText("");
					addFirstName.setText("");
					addLastName.setText("");
					addDOB.setText("");
					addAddress.setText("");
					conn.close();
					System.out.println("Connection closed.");
				} catch (Exception el) {
					System.out.println(el.getMessage());
				}
			}
			
		});

//		Table Handle
		tableList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableList.getSelectedRow();
				DefaultTableModel tableModel = (DefaultTableModel) tableList.getModel();
				addID.setText(tableModel.getValueAt(i, 0).toString());
				addFirstName.setText(tableModel.getValueAt(i, 1).toString());
				addLastName.setText(tableModel.getValueAt(i, 2).toString());
				addDOB.setText(tableModel.getValueAt(i, 3).toString());
				addAddress.setText(tableModel.getValueAt(i, 4).toString());
			}
		});
	}
}
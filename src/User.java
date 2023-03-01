import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class User extends UserLog{

private JFrame frmUser;
private JTable table;
private JTextField textname;
DefaultTableModel model;
private JTextField textsurname;
private JTextField textcomunity;
private int id = 32;

/**
 * @wbp.parser.entryPoint
 */
	public void logIn() {
		initialize();
		Connect();
		loadtable();
		setText();

	}

	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	String url, name, pass;

	public void Connect() {
	try {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		url = "jdbc:mysql://localhost:3306/projektas";
		name = "root";
		pass="Slaptas111!";
		
		con = DriverManager.getConnection(url, name, pass);
		System.out.println("connected to database");
		//con.close();
		
	}
	catch (ClassNotFoundException ex) {
		
	}
	catch (SQLException ex) {
		
	}
}

public void loadtable() {

	model.setRowCount(0);
	try {
		ps = con.prepareStatement("SELECT services_name, Group_Items.price FROM Services JOIN User ON userID='"+id+"' JOIN Group_Items ON g_comunityID = User.comunityID and g_servicesID=servicesID");
		rs =ps.executeQuery("SELECT services_name, Group_Items.price FROM Services JOIN User ON userID='"+id+"' JOIN Group_Items ON g_comunityID = User.comunityID and g_servicesID=servicesID");
		
		while (rs.next()) {
			String name = rs.getString("services_name");
			Float price = rs.getFloat("price");
			model.addRow(new Object[] {name, price});
		}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
}
public void getID(int id) {
	this.id=id;
}

public void setText() {
	try {
		ps = con.prepareStatement("SELECT name, surname, comunity_name FROM User JOIN Comunity ON userID='"+id+"' and User.comunityID = Comunity.comunityID");
		rs =ps.executeQuery("SELECT name, surname, comunity_name FROM User JOIN Comunity ON userID='"+id+"' and User.comunityID = Comunity.comunityID");
		
		while (rs.next()) {
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String comname = rs.getString("comunity_name");
			
			textname.setText(name);
			textsurname.setText(surname);
			textcomunity.setText(comname);
		}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
}

private void initialize() {

		frmUser = new JFrame();
		frmUser.setTitle("User");
		frmUser.setBounds(100, 100, 600, 350);
		frmUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUser.getContentPane().setLayout(null);
		frmUser.setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(263, 52, 300, 247);
		frmUser.getContentPane().add(scrollPane);
		
		table = new JTable();
		model = new DefaultTableModel();
		scrollPane.setViewportView(table);
		Object[] column = {"Service", "Price"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		
		textname = new JTextField();
		textname.setBounds(90, 53, 167, 26);
		frmUser.getContentPane().add(textname);
		textname.setColumns(10);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(17, 58, 61, 16);
		frmUser.getContentPane().add(lblNewLabel);
		
		textsurname = new JTextField();
		textsurname.setColumns(10);
		textsurname.setBounds(90, 88, 167, 26);
		frmUser.getContentPane().add(textsurname);
		
		JLabel lblNewLabel_1 = new JLabel("Surname");
		lblNewLabel_1.setBounds(17, 93, 61, 16);
		frmUser.getContentPane().add(lblNewLabel_1);
		
		textcomunity = new JTextField();
		textcomunity.setColumns(10);
		textcomunity.setBounds(90, 126, 167, 26);
		frmUser.getContentPane().add(textcomunity);
		
		JLabel lblNewLabel_1_1 = new JLabel("Comunity");
		lblNewLabel_1_1.setBounds(17, 131, 61, 16);
		frmUser.getContentPane().add(lblNewLabel_1_1);
		
		frmUser.setVisible(true);
		
	}
}


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class signing {

	private JFrame frame;
	private JTextField textusername;
	private JPasswordField textpassword;


	public void signings() {
		initialize();
		Connect();
	}

	Connection con;
	PreparedStatement ps;
	Statement st;
	ResultSet rs;
	
	String url, name, pass;
	
	public void Connect() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			url = "jdbc:mysql://localhost:3306/projektas";
			name = "root";
			pass="Slaptas111!";
			
			con = DriverManager.getConnection(url, name, pass);
			System.out.println("connected");			
		}
		catch (ClassNotFoundException ex) {
			
		}
		catch (SQLException ex) {
			
		}
	}
	public void logIn(UserLog user) {
		user.logIn();
	}
	

	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	    frame.setLocationRelativeTo(null);
	    
		
		JLabel labellogin = new JLabel("Log In");
		labellogin.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		labellogin.setBounds(122, 6, 49, 16);
		frame.getContentPane().add(labellogin);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(27, 91, 71, 16);
		frame.getContentPane().add(lblNewLabel);
		
		textusername = new JTextField();
		textusername.setBounds(108, 86, 154, 26);
		frame.getContentPane().add(textusername);
		textusername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(27, 124, 71, 16);
		frame.getContentPane().add(lblPassword);
		
		textpassword = new JPasswordField();
		textpassword.setBounds(108, 119, 154, 26);
		frame.getContentPane().add(textpassword);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(108, 45, 154, 40);
		comboBox.addItem("Admin");
		comboBox.addItem("Manager");
		comboBox.addItem("User");
		
		frame.getContentPane().add(comboBox);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = textusername.getText();
				String password = textpassword.getText();
				
				if (comboBox.getSelectedItem() == "Admin") {
				
					try {
					st= con.createStatement();
					
					String qr = "select * from Admin where username='"+username+"' and password='"+password+"'";
					
					rs = st.executeQuery(qr);
					
						if (rs.next()) {
							frame.dispose();
							System.out.println("prisijungta");

							logIn(new Admin());

							
						}
						else {
							JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
						}
					}
					catch (SQLException e1){
						e1.printStackTrace();				
					}
				
				}
				else if (comboBox.getSelectedItem()=="User") {
					try {
						String qr = "select * from User where username='"+username+"' and password='"+password+"'";
						ps = con.prepareStatement(qr);
						rs = ps.executeQuery(qr);
						
							if (rs.next()) {
								int id =rs.getInt("userID");
								User user = new User();
								user.getID(id);
								frame.dispose();
								logIn(new User());

							}
							else {
								JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
							}
						}
						catch (SQLException e1){
							e1.printStackTrace();				
						}
					
				}
				else if (comboBox.getSelectedItem()=="Manager") {
					try {
						st= con.createStatement();
						
						String qr = "select * from Manager where username='"+username+"' and password='"+password+"'";
						
						rs = st.executeQuery(qr);
						
							if (rs.next()) {
								frame.dispose();
								System.out.println("prisijungta");

								logIn(new ServiceManager());
								
							}
							else {
								JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
							}
						}
						catch (SQLException e1){
							e1.printStackTrace();				
						}
				}
				else {
					
				}
			}
		});
		btnNewButton.setBounds(145, 163, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Login in as");
		lblNewLabel_1.setBounds(27, 56, 71, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
	    frame.setVisible(true);

	}

}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UsrRecords extends Records{

	private JFrame frame;
	private JTable table;
	private JTextField textname;
	private JTextField textsurname;
	private JTextField textusername;
	private JTextField textpassword;
	private JTextField textid;
	private JComboBox <Integer> comboBox;
	DefaultTableModel model;

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void UsrManager() {
		Connect();
		initialize();
		loadBox();
		loadtable();
		
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
	public void loadBox() {
		String loadQr = "SELECT comunityID FROM Comunity";
		
		try{
			ps = con.prepareStatement(loadQr);
			rs = ps.executeQuery(loadQr);
			
			while(rs.next()) {
				int cid = rs.getInt("comunityID");
				comboBox.addItem(cid);
			}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
		
	}
	
	public ArrayList<UserList> getUserList(){
		ArrayList<UserList> userList = new ArrayList<UserList>();
		String getQr = "SELECT * FROM User";
		
		try {
		ps = con.prepareStatement(getQr);
		rs = ps.executeQuery(getQr);
		UserList user;
		
		while(rs.next()) {
			user = new UserList(rs.getInt("userID"),rs.getString("name"),rs.getString("surname"), rs.getString("username"), 
					rs.getString("password"),rs.getInt("comunityID"));
			userList.add(user);
		}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
		return userList;
	}
	
	public void loadtable() {
		model.setRowCount(0);
		ArrayList<UserList> list = getUserList();
		model = (DefaultTableModel) table.getModel();
		
		Object[] row = new Object[6];
		for (int i=0; i<list.size(); i++) {
			row[0] = list.get(i).getID();
			row[1] = list.get(i).getName();
			row[2] = list.get(i).getSurname();
			row[3] = list.get(i).getUsername();
			row[4] = list.get(i).getPassword();
			row[5] = list.get(i).getcomID();
			
			model.addRow(row);
			
		}
	}
	public void showItem(int index) {
		textid.setText(Integer.toString(getUserList().get(index).getID()));
		textname.setText(getUserList().get(index).getName());
		textsurname.setText(getUserList().get(index).getSurname());
		textusername.setText(getUserList().get(index).getUsername());
		textpassword.setText(getUserList().get(index).getPassword());
		comboBox.setSelectedItem(getUserList().get(index).getcomID());
	}
	
	public void initialize() {
		
		frame = new JFrame();
		frame.setTitle("User management");
		frame.setBounds(100, 100, 700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				showItem(index);
				System.out.println("pressed");
			}
		});
		scrollPane.setBounds(263, 52, 413, 289);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		model = new DefaultTableModel();
		scrollPane.setViewportView(table);
		Object[] column = {"id", "name", "surname", "username", "password", "community"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		
		textsurname = new JTextField();
		textsurname.setColumns(10);
		textsurname.setBounds(112, 91, 145, 26);
		frame.getContentPane().add(textsurname);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(17, 96, 61, 16);
		frame.getContentPane().add(lblSurname);
		
		textusername = new JTextField();
		textusername.setColumns(10);
		textusername.setBounds(112, 129, 145, 26);
		frame.getContentPane().add(textusername);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(17, 134, 75, 16);
		frame.getContentPane().add(lblUsername);
		
		textpassword = new JTextField();
		textpassword.setColumns(10);
		textpassword.setBounds(112, 167, 145, 26);
		frame.getContentPane().add(textpassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(17, 172, 61, 16);
		frame.getContentPane().add(lblPassword);
		
		JButton btnNewButton = new JButton("Add new");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, bsurname, busername, bpassword;
				int bid;
				
				if(textname.getText().isEmpty() && textsurname.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
				}else {
				bname = textname.getText();
				bsurname = textsurname.getText();
				busername = textusername.getText();
				bpassword = textpassword.getText();
				bid = (int) comboBox.getSelectedItem();
				
				try {
				ps= con.prepareStatement("insert into User(name, surname, username, password, comunityID)values(?,?,?,?,?)");
				ps.setString(1, bname);
				ps.setString(2, bsurname);
				ps.setString(3, bsurname);
				ps.setString(4, bname);
				ps.setInt(5, bid);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record added");
				loadtable();
				}
			
				catch (SQLException e1) {
				    if (e1 instanceof SQLIntegrityConstraintViolationException) {
				    	e1.printStackTrace();
				    }else {
					e1.printStackTrace();
				    }
				}
				}
			}
		});
		btnNewButton.setBounds(163, 237, 88, 26);
		frame.getContentPane().add(btnNewButton);
				
		textname = new JTextField();
		textname.setBounds(112, 53, 145, 26);
		frame.getContentPane().add(textname);
		textname.setColumns(10);
			
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(17, 58, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textname.getText().isEmpty() && textsurname.getText().isEmpty() && textusername.getText().isEmpty()	&& textpassword.getText().isEmpty()){
				
					JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
				}else {
					String pname, psurname, pusername,ppassword;
					int pid, comID;
				
					pname = textname.getText();
					psurname = textsurname.getText();
					pusername = textusername.getText();
					ppassword = textpassword.getText();
					comID = (int) comboBox.getSelectedItem();
					pid = Integer.parseInt(textid.getText());
					
				
					String updateQr= "UPDATE User SET name=?, surname=?, username=?, password=?, comunityID=? WHERE userID =?";
					try {
						ps = con.prepareStatement(updateQr);
						
						ps.setString(1, pname);
						ps.setString(2, psurname);
						ps.setString(3, pusername);
						ps.setString(4, ppassword);
						ps.setInt(5, comID);
						ps.setInt(6, pid);
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "record updated");
						loadtable();
						
					}
					catch (SQLException e1) {
					    if (e1 instanceof SQLIntegrityConstraintViolationException) {
						
					}else {
						e1.printStackTrace();
					}
					
				}
				
			}
			}
		});
		btnNewButton_1.setBounds(67, 313, 88, 26);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Delete");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textid.getText() != null){
					
					int did = Integer.parseInt(textid.getText());
					String deleteQr = "DELETE FROM User WHERE userID=?";
					try {
						ps = con.prepareStatement(deleteQr);
						ps.setInt(1, did);
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "record deleted");
						loadtable();

					}
					catch (SQLException e2) {
						if (e2 instanceof SQLIntegrityConstraintViolationException) {
							
						}else {
							e2.printStackTrace();
						}	
					}
				}else {
					JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
				}
			}
		});
		btnNewButton_1_1.setBounds(163, 312, 88, 29);
		frame.getContentPane().add(btnNewButton_1_1);
		
		textid = new JTextField();
		textid.setColumns(10);
		textid.setBounds(112, 275, 145, 26);
		frame.getContentPane().add(textid);
		
		JLabel lblId = new JLabel("User ID");
		lblId.setBounds(15, 280, 75, 16);
		frame.getContentPane().add(lblId);
		
		comboBox = new JComboBox<Integer>();	
		comboBox.setBounds(112, 204, 139, 27);
		frame.getContentPane().add(comboBox);
		
		JLabel lblComunityId = new JLabel("Community");
		lblComunityId.setBounds(16, 208, 97, 16);
		frame.getContentPane().add(lblComunityId);
		
		JButton btnNewButton_2 = new JButton("<-  Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Admin back = new Admin();
				back.logIn();
			}
		});
		btnNewButton_2.setBounds(6, 6, 107, 26);
		frame.getContentPane().add(btnNewButton_2);
		
		
	}
}

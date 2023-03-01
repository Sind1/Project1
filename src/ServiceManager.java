
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

public class ServiceManager extends UserLog{

	private JFrame frame;
	private JTable table;
	private JComboBox<Integer> comboBox_comunity, comboBox_services;
	DefaultTableModel model, model1, model2;
	
	int comname, sername;
	Float pprice;
	int comunityname;
	int servicesname, gid;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void logIn() {
		initialize();
		Connect();
		loadtable();
		loadBox();
		loadBox1();
		loadtable1();
		loadtable2();
	}
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	String url, name, pass;
	private JTextField textprice;
	private JTable table_1;
	private JTable table_2;
	
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

		public ArrayList<Group> getGroupList(){
		ArrayList<Group> groupList = new ArrayList<Group>();
		String getQr = "SELECT comunityID, comunity_name, servicesID, services_name, Group_Items.groupID, Group_Items.price FROM Comunity JOIN Group_Items ON comunityID = g_comunityID JOIN Services ON servicesID = g_servicesID ORDER BY comunityID ASC";
		
		
		try {
		ps = con.prepareStatement(getQr);
		rs = ps.executeQuery(getQr);
		Group group;
		
		while(rs.next()) {
			group = new Group(rs.getInt("comunityID"), rs.getInt("servicesID"), rs.getString("comunity_name"),rs.getString("services_name"),rs.getInt("groupID"), rs.getFloat("price"));
			groupList.add(group);
		}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
		return groupList;
	}
	
	public void loadtable() {
		model.setRowCount(0);
		ArrayList<Group> list = getGroupList();
		model = (DefaultTableModel) table.getModel();
		
		Object[] row = new Object[6];
		for (int i=0; i<list.size(); i++) {
			row[0] = list.get(i).getGid();
			row[1] = list.get(i).getName();
			row[2] = list.get(i).getSname();
			row[3] = list.get(i).getPrice();
			
			model.addRow(row);
			
		}
	}

	public void loadtable1() {
		model1.setRowCount(0);
		try {
		ps = con.prepareStatement("select comunityID, comunity_name from Comunity");
		rs =ps.executeQuery("select comunityID, comunity_name from Comunity");
		
		while (rs.next()) {
			int id = rs.getInt("comunityID");
			String name = rs.getString("comunity_name");
			model1.addRow(new Object[] {id, name});
		}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
	}
	public void loadtable2() {
		model2.setRowCount(0);
		try {
		ps = con.prepareStatement("select servicesID, services_name from Services");
		rs =ps.executeQuery("select servicesID, services_name from Services");
		
		while (rs.next()) {
			int id = rs.getInt("servicesID");
			String name = rs.getString("services_name");
			model2.addRow(new Object[] {id, name});
		}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
	}
	public void loadBox() {
		String loadQr = "SELECT comunityID FROM Comunity";
		
		try{
			ps = con.prepareStatement(loadQr);
			rs = ps.executeQuery(loadQr);
			
			while(rs.next()) {
				int cid = rs.getInt("comunityID");

				comboBox_comunity.addItem(cid);
	
			}
			
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
		
	}
	public void loadBox1() {
		String loadQr = "SELECT servicesID FROM Services";
		
		try{
			ps = con.prepareStatement(loadQr);
			rs = ps.executeQuery(loadQr);
			
			while(rs.next()) {

				int sid = rs.getInt("servicesID");

				comboBox_services.addItem(sid);	
			}
			
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
		
	}
	public void showItem(int index) {
		textprice.setText(Float.toString(getGroupList().get(index).getPrice()));
		comboBox_comunity.setSelectedItem(getGroupList().get(index).getID());
		comboBox_services.setSelectedItem(getGroupList().get(index).getSid());
	}

	private void initialize() {
			
			frame = new JFrame();
			frame.setTitle("Service management");
			frame.setBounds(100, 100, 900, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.setLocationRelativeTo(null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = table.getSelectedRow();
					showItem(index);
					System.out.println("pressed");
					gid = getGroupList().get(index).getGid();
				}
			});scrollPane.setBounds(263, 52, 413, 289);
			frame.getContentPane().add(scrollPane);
			
			table = new JTable();
			model = new DefaultTableModel();
			scrollPane.setViewportView(table);
			Object[] column = {"group", "comunity", "service", "price"};
			model.setColumnIdentifiers(column);
			table.setModel(model);

			JLabel lblNewLabel = new JLabel("Service price");
			lblNewLabel.setBounds(15, 135, 94, 16);
			frame.getContentPane().add(lblNewLabel);
			
			comboBox_comunity = new JComboBox<Integer>();
			comboBox_comunity.setBounds(112, 49, 139, 27);
			frame.getContentPane().add(comboBox_comunity);
			
			JButton btnNewButton = new JButton("Add new");
			btnNewButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
				
					comname = (Integer)comboBox_comunity.getSelectedItem();
					sername = (Integer)comboBox_services.getSelectedItem();
					pprice = Float.parseFloat(textprice.getText());
					
					if(textprice.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
					}else {
	
					try {
						
					ps= con.prepareStatement("insert into Group_Items(g_comunityID, g_servicesID, price)values(?,?,?)");
					ps.setInt(1, comname);
					ps.setInt(2, sername);
					ps.setFloat(3, pprice);
					ps.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record added");
					loadtable();
					loadtable1();
					loadtable2();
					
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
			
			btnNewButton.setBounds(163, 198, 88, 26);
			frame.getContentPane().add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("Update");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (textprice.getText().isEmpty()){
					
						JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
					}else {
						Float uprice;
						int pid, sid;
					
						uprice = Float.parseFloat(textprice.getText());
						pid = (Integer) comboBox_comunity.getSelectedItem();
						sid = (Integer) comboBox_services.getSelectedItem();
		
						String updateQr= "UPDATE Group_Items SET g_comunityID=?, g_servicesID=?, price=? WHERE groupID=?";
						try {
							ps = con.prepareStatement(updateQr);
							
							ps.setInt(1, pid);
							ps.setInt(2, sid);
							ps.setFloat(3, uprice);
							ps.setInt(4, gid);
							
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
					if (comboBox_comunity.getSelectedItem() != null){
						
						int did = (Integer)comboBox_comunity.getSelectedItem();
						int dsid = (Integer)comboBox_services.getSelectedItem();
						
						String deleteQr = "DELETE FROM Group_Items WHERE groupID=?";
						try {
							ps = con.prepareStatement(deleteQr);
							ps.setInt(1, gid);

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
			
			JLabel lblNewLabel_1 = new JLabel("Service ID");
			lblNewLabel_1.setBounds(15, 96, 83, 16);
			frame.getContentPane().add(lblNewLabel_1);
			
			textprice = new JTextField();
			textprice.setColumns(10);
			textprice.setBounds(112, 130, 145, 26);
			frame.getContentPane().add(textprice);
			
			JLabel lblNewLabel_1_1 = new JLabel("Comunity ID");
			lblNewLabel_1_1.setBounds(15, 53, 94, 16);
			frame.getContentPane().add(lblNewLabel_1_1);
			
			comboBox_services = new JComboBox();
			comboBox_services.setBounds(112, 92, 139, 27);
			frame.getContentPane().add(comboBox_services);
			
			JScrollPane scrollPane_comunity = new JScrollPane();
			scrollPane_comunity.setBounds(704, 54, 174, 133);
			frame.getContentPane().add(scrollPane_comunity);
			
			table_1 = new JTable();
			model1 = new DefaultTableModel();
			scrollPane_comunity.setViewportView(table_1);
			Object[] column1 = {"comunity id", "comunity"};
			model1.setColumnIdentifiers(column1);
			table_1.setModel(model1);
			
			JScrollPane scrollPane_services = new JScrollPane();
			scrollPane_services.setBounds(704, 208, 174, 133);
			frame.getContentPane().add(scrollPane_services);
			
			table_2 = new JTable();
			model2 = new DefaultTableModel();
			scrollPane_services.setViewportView(table_2);
			Object[] column2 = {"services id", "services"};
			model2.setColumnIdentifiers(column2);
			table_2.setModel(model2);
			
			JLabel lblNewLabel_2 = new JLabel("Comunity list");
			lblNewLabel_2.setBounds(704, 39, 121, 16);
			frame.getContentPane().add(lblNewLabel_2);
			
			JLabel lblNewLabel_2_1 = new JLabel("Services list");
			lblNewLabel_2_1.setBounds(704, 191, 121, 16);
			frame.getContentPane().add(lblNewLabel_2_1);
			
			frame.setVisible(true);
			
		}
}

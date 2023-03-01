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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CmnRecords extends Records {

	private JFrame frame;
	private JTable table;
	private JTextField textname;
	private JTextField textid;
	DefaultTableModel model;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void CmnRecords() {
		initialize();
		Connect();
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
	// nera comunity listo pls 
	public ArrayList<ComunityList> getComunityList(){
		ArrayList<ComunityList> comunityList = new ArrayList<ComunityList>();
		String getQr = "SELECT * FROM Comunity";
		
		try {
		ps = con.prepareStatement(getQr);
		rs = ps.executeQuery(getQr);
		ComunityList comunity;
		
		while(rs.next()) {
			comunity = new ComunityList(rs.getInt("comunityID"),rs.getString("comunity_name"));
			comunityList.add(comunity);
		}
		}
		catch(SQLException exx) {
			exx.printStackTrace();
		}
		return comunityList;
	}
	
	public void loadtable() {
		model.setRowCount(0);
		ArrayList<ComunityList> list = getComunityList();
		model = (DefaultTableModel) table.getModel();
		
		Object[] row = new Object[6];
		for (int i=0; i<list.size(); i++) {
			row[0] = list.get(i).getID();
			row[1] = list.get(i).getName();
			
			model.addRow(row);
			
		}
	}
	public void showItem(int index) {
		textid.setText(Integer.toString(getComunityList().get(index).getID()));
		textname.setText(getComunityList().get(index).getName());
	}

	public void initialize() {
			
			frame = new JFrame();
			frame.setTitle("Comunity management");
			frame.setBounds(100, 100, 700, 400);
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
				}
			});scrollPane.setBounds(263, 52, 413, 289);
			frame.getContentPane().add(scrollPane);
			
			table = new JTable();
			model = new DefaultTableModel();
			scrollPane.setViewportView(table);
			Object[] column = {"id", "name"};
			model.setColumnIdentifiers(column);
			table.setModel(model);
			
			textname = new JTextField();
			textname.setBounds(112, 53, 145, 26);
			frame.getContentPane().add(textname);
			textname.setColumns(10);

			JLabel lblNewLabel = new JLabel("Name");
			lblNewLabel.setBounds(17, 58, 61, 16);
			frame.getContentPane().add(lblNewLabel);
			
			JButton btnNewButton = new JButton("Add new");
			btnNewButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					String bname;
					
					if(textname.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
					}else {
					bname = textname.getText();
	
					try {
					ps= con.prepareStatement("insert into Comunity(comunity_name)values(?)");
					ps.setString(1, bname);
		
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
			
			JButton btnNewButton_1 = new JButton("Update");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (textname.getText().isEmpty()){
					
						JOptionPane.showMessageDialog(btnNewButton, "Incorrect input", "Error", 0);
					}else {
						String pname;
						int pid;
					
						pname = textname.getText();
						pid = Integer.parseInt(textid.getText());
		
						String updateQr= "UPDATE Comunity SET comunity_name=? WHERE comunityID=?";
						try {
							ps = con.prepareStatement(updateQr);
							
							ps.setString(1, pname);
							ps.setInt(2, pid);
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
						
						String deleteQr = "DELETE FROM Comunity WHERE comunityID=?";
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
			
			JLabel lblId = new JLabel("Comunity ID");
			lblId.setBounds(15, 280, 85, 16);
			frame.getContentPane().add(lblId);
			
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
			
			frame.setVisible(true);
			
		}

	

}


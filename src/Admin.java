
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Admin extends UserLog {

	private JFrame frame;
	private JPanel contentPane;
	private JButton btnNewButton;

//polimorphism
	public void logIn() {
		frame = new JFrame();
		frame.setTitle("Loged in as Admin");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 300, 250);
		frame.setLocationRelativeTo(null);
	 

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Users");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsrRecords users = new UsrRecords();
				users.UsrManager();
			}
		});
		btnNewButton.setBounds(91, 49, 117, 29);
		contentPane.add(btnNewButton);
		
		JButton btnManagers = new JButton("Managers");
		btnManagers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manager manage = new Manager();
				manage.open();
				
			}
		});
		btnManagers.setBounds(91, 90, 117, 29);
		contentPane.add(btnManagers);
		
		JButton btnComunity = new JButton("Comunities");
		btnComunity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CmnRecords cmn = new CmnRecords();
			cmn.CmnRecords();
			}
		});
		btnComunity.setBounds(91, 131, 117, 29);
		contentPane.add(btnComunity);
		
		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.setBounds(133, 6, 34, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Services");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceRecords service = new ServiceRecords();
				service.ServiceRecords();
			}
		});
		btnNewButton_1.setBounds(91, 172, 117, 29);
		contentPane.add(btnNewButton_1);
		frame.setVisible(true);
	}
}

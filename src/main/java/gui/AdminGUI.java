package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Admin;
import domain.Driver;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AdminGUI extends JFrame {
	
    private Admin admin;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	
	protected JLabel jLabelSelectOption;
	
	/**
	 * This is the default constructor
	 */
	public AdminGUI(Admin a) {
		super();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		admin=a;
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(8, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(admin);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonQueryQueries);
		
		JButton jButtonAddMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.AddMoney"));
		jButtonAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AddMoneyGUI(admin);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonAddMoney);
		
		JButton jButtonWithdrawMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.WithdrawMoney"));
		jButtonWithdrawMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new RemoveMoneyGUI(admin);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonWithdrawMoney);
		
		JButton jButtonMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Movements"));
		jButtonMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MovementGUI(admin);
				a.setVisible(true);
			}
		});
		
		jContentPane.add(jButtonMovements);
		
		
		setContentPane(jContentPane);
		
		JButton JButtonDelete = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.DeleteAccount")); 
		JButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFrame a = new ConfirmDeleteGUI(admin);
				a.setVisible(true);
				
				closeWindow(e);	
			}
		});JButton JButtonManageReclamations = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.ManageReclamations"));
		JButtonManageReclamations.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JFrame a = new AdminReclamationGUI(admin);
			a.setVisible(true);
		}
		});
		jContentPane.add(JButtonManageReclamations);
		jContentPane.add(JButtonDelete);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle") + " - "+ResourceBundle.getBundle("Etiquetas").getString("Admin")+": "+admin.getName());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				dispose();
			}
		});
	}
	private void closeWindow(ActionEvent e) {
		this.setVisible(false);
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"


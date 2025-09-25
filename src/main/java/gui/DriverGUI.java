package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

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


public class DriverGUI extends JFrame {
	
    private Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	
	protected JLabel jLabelSelectOption;
	
	/**
	 * This is the default constructor
	 */
	public DriverGUI(Driver d) {
		super();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		driver=d;
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.CreateRide"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateRideGUI(driver);
				a.setVisible(true);
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(11, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonCreateQuery);
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonQueryQueries);
		
		JButton jButtonAcceptRejectReservation = new JButton();
		jButtonAcceptRejectReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ManageReservationsGUI(driver);
				a.setVisible(true);
			}
		});
		jButtonAcceptRejectReservation.setText((String) null);
		jButtonAcceptRejectReservation.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.AcceptReject"));
		jContentPane.add(jButtonAcceptRejectReservation);
		
		JButton jButtonAddMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.AddMoney"));
		jButtonAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AddMoneyGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonAddMoney);
		
		JButton jButtonWithdrawMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.WithdrawMoney"));
		jButtonWithdrawMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new RemoveMoneyGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonWithdrawMoney);
		
		JButton jButtonAddCar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.AddCar"));
		jButtonAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AddCarGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonAddCar);
		
		JButton jButtonMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Movements"));
		jButtonMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MovementGUI(driver);
				a.setVisible(true);
			}
		});
		
		jContentPane.add(jButtonMovements);
		
		JButton jButtonCancelRide = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CancelRideGUI.CancelRide"));
		jButtonCancelRide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CancelRideGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonCancelRide);
		
		
		setContentPane(jContentPane);
		
		JButton JButtonDelete = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.DeleteAccount")); 
		JButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFrame a = new ConfirmDeleteGUI(driver);
				a.setVisible(true);
				
				closeWindow(e);	
			}
		});jContentPane.add(JButtonDelete);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle") + " - "+ResourceBundle.getBundle("Etiquetas").getString("Driver")+": "+driver.getName());
		
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


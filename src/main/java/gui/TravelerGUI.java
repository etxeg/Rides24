/**
 * @author Software Engineering teachers
 */
package gui;



import javax.swing.*;

import domain.Driver;
import domain.Ride;
import domain.Traveler;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TravelerGUI extends JFrame {
	
    private Traveler traveler;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	
	protected JLabel jLabelSelectOption;
	private JButton jButtonReservationState;
	private JButton jButtonConfirmReservation;
	private JButton jButtonAssessment;
	private JButton JButtonDelete;
	private JButton jButtonReclamation;
	private JButton btnReclamationState;
	private JButton jButtonDelete;
	
	/**
	 * This is the default constructor
	 */
	public TravelerGUI(Traveler t) {
		super();
		BLFacade facade = MainGUI.getBusinessLogic();
		traveler=t;
		traveler.printAlerts();
		System.out.println("Start\n");
		List<Ride> alertRides = facade.checkAlerts(t);
		if (alertRides.isEmpty() == false) {
			JFrame a = new AlertFoundGUI(alertRides);
			a.setVisible(true);
		}
		// this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(traveler);
				a.setVisible(true);
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(13, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonQueryQueries);
		
		JButton jButtonAddMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.AddMoney"));
		jButtonAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AddMoneyGUI(traveler);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonAddMoney);
		
		JButton jButtonWithdrawMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserGUI.WithdrawMoney"));
		jButtonWithdrawMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new RemoveMoneyGUI(traveler);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonWithdrawMoney);
		
		jButtonReservationState = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.ReservationState"));
		jButtonReservationState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ReservationStateGUI(traveler);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonReservationState);
		
		jButtonConfirmReservation = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.ConfirmReservation"));
		jButtonConfirmReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ConfirmReservationGUI(traveler);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonConfirmReservation);
		
		JButton jButtonMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Movements"));
		jButtonMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MovementGUI(traveler);
				a.setVisible(true);
			}
		});
		
		jContentPane.add(jButtonMovements);
		
		jButtonAssessment = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Assessment"));
		jButtonAssessment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AssessmentGUI(traveler);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonAssessment);
		
		JButtonDelete = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.DeleteAccount")); 
		JButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFrame a = new ConfirmDeleteGUI(traveler);
				a.setVisible(true);
				
				closeWindow(e);
			}
		}); 
		
		jButtonReclamation = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Reclamation"));
		jButtonReclamation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame b=new ReclamationGUI(traveler);
				b.setVisible(true);
			}
		});
		jContentPane.add(jButtonReclamation);
		
		btnReclamationState = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.ReclamationState"));
		btnReclamationState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ReclamationStateGUI(t);
				a.setVisible(true);
			}
		});
		jContentPane.add(btnReclamationState);
		jContentPane.add(JButtonDelete);
		
		
		setContentPane(jContentPane);
		
		jButtonDelete = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.CreateAlert"));
		jButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AlertGUI(t);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonDelete);
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle") + " - "+ResourceBundle.getBundle("Etiquetas").getString("Traveler")+": "+traveler.getName());
		
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


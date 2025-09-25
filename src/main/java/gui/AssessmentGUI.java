package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Assessment;
import domain.Driver;
import domain.Reservation;
import domain.Ride;
import domain.Traveler;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;

public class AssessmentGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultTableModel tableModelRides;
	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Traveler"),
			ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.nSeats")
	};
	private JComboBox<Reservation> rideComboBox = new JComboBox<Reservation>();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public AssessmentGUI(Traveler traveler) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton jButtonSend = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send"));
		
		jButtonSend.setBounds(168, 232, 100, 21);
		contentPane.add(jButtonSend);
		jButtonSend.setEnabled(false);
		
		JLabel reservationText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationStateGUI.SelectReservation"));
		reservationText.setHorizontalAlignment(SwingConstants.CENTER);
		reservationText.setBounds(83, 28, 260, 20);
		contentPane.add(reservationText);
		
        for (Reservation actualReservation : traveler.getReservations()){
        	if (actualReservation.isBaieztatua()) {
        		jButtonSend.setEnabled(true);
        		rideComboBox.addItem(actualReservation);
        	}
        }
        
        rideComboBox.setBounds(63, 58, 300, 20);
        contentPane.add(rideComboBox);
		
		rideComboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		jButtonSend.setEnabled(true);
        	}
        });
		
		JRadioButton rdbtnBal1 = new JRadioButton();
		buttonGroup_1.add(rdbtnBal1);
		rdbtnBal1.setText("1");
		rdbtnBal1.setBounds(120, 132, 41, 21);
		contentPane.add(rdbtnBal1);
		
		JRadioButton rdbtnBal2 = new JRadioButton();
		buttonGroup_1.add(rdbtnBal2);
		rdbtnBal2.setText("2");
		rdbtnBal2.setBounds(162, 132, 41, 21);
		contentPane.add(rdbtnBal2);
		
		JRadioButton rdbtnBal3 = new JRadioButton();
		rdbtnBal3.setSelected(true);
		buttonGroup_1.add(rdbtnBal3);
		rdbtnBal3.setText("3");
		rdbtnBal3.setBounds(204, 132, 41, 21);
		contentPane.add(rdbtnBal3);
		
		JRadioButton rdbtnBal4 = new JRadioButton();
		buttonGroup_1.add(rdbtnBal4);
		rdbtnBal4.setText("4");
		rdbtnBal4.setBounds(246, 132, 41, 21);
		contentPane.add(rdbtnBal4);
		
		JRadioButton rdbtnBal5 = new JRadioButton();
		buttonGroup_1.add(rdbtnBal5);
		rdbtnBal5.setText("5");
		rdbtnBal5.setBounds(289, 132, 41, 21);
		contentPane.add(rdbtnBal5);
		
		JLabel BalText = new JLabel("Balorazioa");
		BalText.setHorizontalAlignment(SwingConstants.CENTER);
		BalText.setBounds(83, 99, 260, 13);
		contentPane.add(BalText);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(63, 159, 300, 63);
		contentPane.add(textPane);
		
		jButtonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonSend.setEnabled(false);
				Reservation reservation = (Reservation) rideComboBox.getSelectedItem();
				int star = 0;
				if (rdbtnBal1.isSelected()) {
					star = 1;
				} else if (rdbtnBal2.isSelected()) {
					star = 2;
				} else if (rdbtnBal3.isSelected()) {
					star = 3;
				} else if (rdbtnBal4.isSelected()) {
					star = 4;
				} else if (rdbtnBal5.isSelected()) {
					star = 5;
				}
				String text = textPane.getText();
				if (star > 0) {
					Assessment assessment = facade.createAssessment(reservation.getRide(), traveler, star, text);
					if (assessment != null) {
						jButton2_actionPerformed(e);
					}
				}
			}
		});
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

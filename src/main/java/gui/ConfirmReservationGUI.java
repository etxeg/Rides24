package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
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

public class ConfirmReservationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Reservation> rideComboBox = new JComboBox<Reservation>();

	/**
	 * Create the frame.
	 */
	public ConfirmReservationGUI(Traveler traveler) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel reservationText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationStateGUI.SelectReservation"));
		reservationText.setHorizontalAlignment(SwingConstants.CENTER);
		reservationText.setBounds(83, 28, 260, 20);
		contentPane.add(reservationText);
		
		JButton jButtonConfirmReservation = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReservationStateGUI.Confirm"));
		jButtonConfirmReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reservation selectedReservation = (Reservation) rideComboBox.getSelectedItem();
				facade.confirmReservation(selectedReservation);
				jButtonConfirmReservation.setEnabled(false);
			}
		});
		jButtonConfirmReservation.setBounds(231, 232, 100, 21);
		jButtonConfirmReservation.setEnabled(false);
		contentPane.add(jButtonConfirmReservation);
		
		rideComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reservation selectedReservation = (Reservation) rideComboBox.getSelectedItem();
				Date currentDate = Calendar.getInstance().getTime();
			    if (selectedReservation.getDate().after(currentDate)) { //Bidaia ez da oraindik gertatu
			    	jButtonConfirmReservation.setEnabled(false);
			    } else { //Bidaiaren data pasa da
			    	jButtonConfirmReservation.setEnabled(true);
			    }
			}
		});
        for (Reservation actualRide : traveler.getReservations()){
        	if (actualRide.getState().equals("Onartua")) {
        		rideComboBox.addItem(actualRide);
        	}
        }
        rideComboBox.setBounds(63, 73, 300, 20);
        contentPane.add(rideComboBox);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(104, 232, 100, 21);
		contentPane.add(jButtonClose);
		
		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

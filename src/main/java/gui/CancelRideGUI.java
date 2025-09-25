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

public class CancelRideGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JComboBox<Ride> rideComboBox = new JComboBox<Ride>();

	/**
	 * Create the frame.
	 */
	public CancelRideGUI(Driver driver) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel reservationText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.SelectRide"));
		reservationText.setHorizontalAlignment(SwingConstants.CENTER);
		reservationText.setBounds(83, 28, 260, 20);
		contentPane.add(reservationText);
		
        rideComboBox.setBounds(63, 58, 300, 20);
        contentPane.add(rideComboBox);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(168, 232, 100, 21);
		contentPane.add(jButtonClose);
		
		JButton jButtonCancelRide = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CancelRideGUI.CancelRide"));
		jButtonCancelRide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ride selectedRide = (Ride) rideComboBox.getSelectedItem();
				rideComboBox.removeItem(selectedRide);
				facade.cancelRide(selectedRide);
			}
		});
		jButtonCancelRide.setBounds(148, 190, 140, 21);
		jButtonCancelRide.setEnabled(false);
		contentPane.add(jButtonCancelRide);
		
		for (Ride actualRide : driver.getRides()){
        	jButtonCancelRide.setEnabled(true);
            rideComboBox.addItem(actualRide);
        }
		
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

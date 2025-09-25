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

public class ManageReservationsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JTable tableRides= new JTable();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultTableModel tableModelRides;
	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Traveler"),
			ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.nSeats")
	};
	private JComboBox<Ride> rideComboBox = new JComboBox<Ride>();

	/**
	 * Create the frame.
	 */
	public ManageReservationsGUI(Driver driver) {
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
		
		tableModelRides = new DefaultTableModel(null, columnNamesRides);
		tableRides.setModel(tableModelRides);
		
		tableModelRides.setDataVector(null, columnNamesRides);
		tableModelRides.setColumnCount(3);
		
		JRadioButton rdbtnAccept = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.Accept"));
		buttonGroup.add(rdbtnAccept);
		rdbtnAccept.setBounds(123, 171, 103, 21);
		contentPane.add(rdbtnAccept);
		
		JRadioButton rdbtnReject = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.Reject"));
		rdbtnReject.setSelected(true);
		buttonGroup.add(rdbtnReject);
		rdbtnReject.setBounds(228, 171, 103, 21);
		contentPane.add(rdbtnReject);
		
		JButton jButtonConfirm = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.Confirm"));
		jButtonConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int errenkadaZbk = tableRides.getSelectedRow();
		        if (errenkadaZbk != -1) { //Errenkada bat aukeratuta dago
		        	Reservation erreserba = (Reservation) tableModelRides.getValueAt(errenkadaZbk, 2);
					if (rdbtnAccept.isSelected()) {
						facade.acceptReservation(erreserba);
					} else { //Baztertu
						facade.rejectReservation(erreserba);
					}
					jButtonConfirm.setEnabled(false);
		        }
			}
		});
		jButtonConfirm.setBounds(231, 232, 100, 21);
		jButtonConfirm.setEnabled(false);
		contentPane.add(jButtonConfirm);
		
		tableRides.getSelectionModel().addListSelectionListener(event -> {
	        if (!event.getValueIsAdjusting() && tableRides.getSelectedRow() != -1) {
	        	jButtonConfirm.setEnabled(true);
	        }
	    });
		
		rideComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModelRides.setDataVector(null, columnNamesRides);
				tableModelRides.setColumnCount(3);
				
				Ride selectedRide = (Ride) rideComboBox.getSelectedItem();
				if (selectedRide!=null) {
					List<Reservation> pendingReservations = selectedRide.getPendingReservations();
					for (Reservation actualReservation:pendingReservations) {
						if (actualReservation.isOnartua() == false) {
							Vector<Object> row = new Vector<Object>();
							row.add(actualReservation.getTraveler().getName());
							row.add(actualReservation.getnSeats());
							row.add(actualReservation); //Erreserba geroago lortzeko
							tableModelRides.addRow(row);
						}
					}
					tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(2));
				}
				jButtonConfirm.setEnabled(false);
			}
		});
        for (Ride actualRide : driver.getRides()){
        	if (actualRide.getPendingReservations().isEmpty() == false) {
        		rideComboBox.addItem(actualRide);
        	}
        }
        rideComboBox.setBounds(63, 58, 300, 20);
        contentPane.add(rideComboBox);
        
        scrollPaneEvents.setBounds(new Rectangle(123, 88, 180, 77));
		scrollPaneEvents.setViewportView(tableRides);
		this.getContentPane().add(scrollPaneEvents, null);
		
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

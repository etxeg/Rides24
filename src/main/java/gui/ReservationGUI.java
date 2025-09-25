package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Reservation;
import domain.Ride;
import domain.Traveler;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReservationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ReservationGUI(Traveler traveler,Ride ride) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel fromText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationGUI.From"));
		fromText.setBounds(88, 28, 80, 20);
		contentPane.add(fromText);
		
		JLabel toText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationGUI.To"));
		toText.setBounds(88, 58, 80, 20);
		contentPane.add(toText);
		
		JLabel dateText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationGUI.Date"));
		dateText.setBounds(88, 88, 80, 20);
		contentPane.add(dateText);
		
		JLabel nPlacesText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationGUI.NPlaces"));
		nPlacesText.setBounds(88, 118, 112, 20);
		contentPane.add(nPlacesText);
		
		JLabel priceText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationGUI.Price"));
		priceText.setBounds(88, 148, 80, 20);
		contentPane.add(priceText);
		
		JLabel priceBox = new JLabel();
		priceBox.setBounds(210, 148, 80, 20);
		priceBox.setText(""+ride.getPrice());
		contentPane.add(priceBox);
		
		JComboBox<Integer> nPlacesBox = new JComboBox<>();
		nPlacesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nPlaces = (int) nPlacesBox.getSelectedItem();
				float newPrice = nPlaces*ride.getPrice();
				System.out.println(newPrice+" and nplaces:"+nPlaces);
				priceBox.setText(""+newPrice);
			}
		});
        for (int i = 1; i <= ride.getnPlaces(); i++) {
            nPlacesBox.addItem(i);
        }
        nPlacesBox.setBounds(210, 118, 80, 20);
        contentPane.add(nPlacesBox);
        
		JLabel dateBox = new JLabel();
		dateBox.setBounds(210, 88, 216, 20);
		dateBox.setText(""+ride.getDate());
		contentPane.add(dateBox);
		
		JLabel toBox = new JLabel();
		toBox.setBounds(210, 58, 80, 20);
		toBox.setText(""+ride.getTo());
		contentPane.add(toBox);
		
		JLabel fromBox = new JLabel();
		fromBox.setBounds(210, 28, 80, 20);
		fromBox.setText(""+ride.getFrom());
		contentPane.add(fromBox);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(104, 232, 100, 21);
		contentPane.add(jButtonClose);
		
		JButton jButtonErreserbatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Book"));
		jButtonErreserbatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nPlaces = (int) nPlacesBox.getSelectedItem();
				boolean correct = facade.createReservation(traveler, ride, nPlaces);
				if (correct == true) {
					jButtonErreserbatu.setEnabled(false);
					jButton2_actionPerformed(e);
				} else {
					System.out.println("Error");
				}
			}
		});
		jButtonErreserbatu.setBounds(231, 232, 100, 21);
		contentPane.add(jButtonErreserbatu);
		
		JButton jButtonAssessments = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReservationGUI.Assessments"));
		jButtonAssessments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AssessmentCheckGUI(ride.getDriver());
				a.setVisible(true);
			}
		});
		jButtonAssessments.setBounds(88, 188, 265, 34);
		contentPane.add(jButtonAssessments);

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

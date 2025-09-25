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
import domain.Reclamation;
import domain.Reservation;
import domain.Ride;
import domain.Traveler;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

public class ReclamationStateGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultComboBoxModel<Reclamation> reclam = new DefaultComboBoxModel<Reclamation>();
	private JComboBox<Reclamation> rideComboBox = new JComboBox<Reclamation>();

	/**
	 * Create the frame.
	 */
	public ReclamationStateGUI(Traveler traveler) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel reservationText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReclamationStateGUI.SelectReclamation"));
		reservationText.setHorizontalAlignment(SwingConstants.CENTER);
		reservationText.setBounds(83, 28, 260, 20);
		contentPane.add(reservationText);
		
		JLabel stateText = new JLabel("Loading...");
		stateText.setBounds(220, 133, 206, 13);
		contentPane.add(stateText);
		
		boolean erreklamazioaDu = false;
		for(Reclamation actualReclamation:traveler.getReclamations()) {
			if (erreklamazioaDu==false) {
				erreklamazioaDu = true;
				stateText.setText(actualReclamation.getState());
			}
			reclam.addElement(actualReclamation);
		}
		
		rideComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reclamation selectedReclamation = (Reclamation) rideComboBox.getSelectedItem();
				String actualState = selectedReclamation.getState();
				stateText.setText(actualState);
			}
		});
        
        rideComboBox.setBounds(63, 58, 300, 20);
        rideComboBox.setModel(reclam);
        contentPane.add(rideComboBox);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(168, 232, 100, 21);
		contentPane.add(jButtonClose);
		
		JLabel stateLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReservationStateGUI.State"));
		stateLabel.setBounds(83, 126, 100, 27);
		contentPane.add(stateLabel);
		
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


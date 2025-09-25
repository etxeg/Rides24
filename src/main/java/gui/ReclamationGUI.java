package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Reservation;
import domain.Traveler;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JComboBox;

public class ReclamationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JComboBox<Reservation> comboBox = new JComboBox<Reservation>();
	private DefaultComboBoxModel<Reservation> erreserbas = new DefaultComboBoxModel<Reservation>();

	/**
	 * Launch the application.
	 */
	/**public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReclamationGUI frame = new ReclamationGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public ReclamationGUI(Traveler traveler) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel bidaiaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Reclamation.Reservation"));
		bidaiaLabel.setBounds(60, 65, 86, 13);
		contentPane.add(bidaiaLabel);
		
		JLabel arrazoiaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Reclamation.Reason"));
		arrazoiaLabel.setBounds(60, 127, 86, 13);
		contentPane.add(arrazoiaLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(193, 124, 222, 41);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton sendButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send"));
		sendButton.setEnabled(false);
		
		List<Reservation> origins=facade.getErreserbaBefore(traveler);
		System.out.println(origins);
		for(Reservation erreklamazioa:origins) {
			erreserbas.addElement(erreklamazioa);
			sendButton.setEnabled(true);
		}
		System.out.println(erreserbas);
		
		
		comboBox.setBounds(193, 61, 233, 21);
		contentPane.add(comboBox);
		comboBox.setModel(erreserbas);
		contentPane.add(comboBox);
		
		
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//int zenb=(int) spinner.getValue();
				Reservation res= (Reservation) comboBox.getSelectedItem();
				String why=textField_1.getText();
				boolean ondo= facade.reclamation(traveler, res, why);
				if (ondo) {
					jButton2_actionPerformed(e);
				} else {
					System.out.println("Errore bat egon da");
				}
				
			}
		});
		sendButton.setBounds(185, 202, 85, 21);
		contentPane.add(sendButton);
		
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

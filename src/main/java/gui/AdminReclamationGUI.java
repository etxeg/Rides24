package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Reclamation;
import domain.Ride;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextPane;

public class AdminReclamationGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JTextPane textPane = new JTextPane();
	JComboBox<Reclamation> comboBox= new JComboBox<Reclamation>();
	DefaultComboBoxModel<Reclamation> reclam = new DefaultComboBoxModel<Reclamation>();



	/**
	 * Create the frame.
	 * @param user 
	 */
	public AdminReclamationGUI(Admin user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.Accept"));
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(135, 160, 82, 21);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ManageReservationsGUI.Reject"));
		rdbtnNewRadioButton_1.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(219, 160, 103, 21);
		contentPane.add(rdbtnNewRadioButton_1);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Reclamation> origins=facade.getReclamationNotProcesed();
		
		
		
		comboBox.setBounds(96, 57, 237, 21);
		comboBox.setModel(reclam);
		contentPane.add(comboBox);
		
		JButton sendButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send"));
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButton.setEnabled(false);
				Reclamation erreklamazioa = (Reclamation) comboBox.getSelectedItem();
				if (rdbtnNewRadioButton.isSelected()) {
					facade.erreklamazioaKudeatu(erreklamazioa, 1);
				} else {
					facade.erreklamazioaKudeatu(erreklamazioa, 0);
				}
				
				List<Reclamation> origins=facade.getReclamationNotProcesed();
				for(Reclamation actualReclamation:origins) reclam.addElement(actualReclamation);
				
			}
		});
		sendButton.setEnabled(false);
		sendButton.setBounds(172, 206, 85, 21);
		contentPane.add(sendButton);
		
		boolean lehenengoa = false;
		
		for(Reclamation actualReclamation:origins) {
			if (lehenengoa == false) {
				lehenengoa = true;
				textPane.setText(actualReclamation.getWhy());
				sendButton.setEnabled(true);
			}
			reclam.addElement(actualReclamation);
		}
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.removeAll();
				Reclamation rec = (Reclamation) comboBox.getSelectedItem();
				textPane.setText(rec.getWhy());
				sendButton.setEnabled(true);
			}
		});
		textPane.setEditable(false);
		
		textPane.setBounds(96, 98, 237, 49);
		contentPane.add(textPane);
	}
}

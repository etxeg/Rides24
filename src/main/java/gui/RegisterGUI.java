package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Driver;
import domain.Traveler;
import domain.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textErabiltzailea;
	private JPasswordField passwordField;
	private DefaultComboBoxModel erabiltzaileak = new DefaultComboBoxModel();
	private JTextField textEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				dispose();
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textErabiltzailea = new JTextField();
		textErabiltzailea.setBounds(251, 32, 96, 19);
		contentPane.add(textErabiltzailea);
		textErabiltzailea.setColumns(10);
		
		JLabel erabiltzaileaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.User"));
		erabiltzaileaLabel.setBounds(95, 36, 86, 13);
		contentPane.add(erabiltzaileaLabel);
		
		JLabel pasahitzaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));
		pasahitzaLabel.setBounds(95, 89, 86, 13);
		contentPane.add(pasahitzaLabel);
		
		JTextPane sarreraText = new JTextPane();
		sarreraText.setEditable(false);
		sarreraText.setBounds(95, 137, 252, 57);
		contentPane.add(sarreraText);
		
		JLabel motaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Type"));
		motaLabel.setBounds(95, 113, 86, 13);
		contentPane.add(motaLabel);
		
		JLabel emailLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Email"));
		emailLabel.setBounds(95, 60, 46, 14);
		contentPane.add(emailLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(251, 85, 96, 19);
		contentPane.add(passwordField);
		
		JComboBox textMota = new JComboBox();
		textMota.setBounds(251, 108, 96, 22);
		contentPane.add(textMota);
		textMota.setModel(erabiltzaileak);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(251, 57, 96, 19);
		contentPane.add(textEmail);
		erabiltzaileak.addElement(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));
		erabiltzaileak.addElement(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler"));
		//erabiltzaileak.addElement(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Admin"));
		
		JButton sartuBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Enter"));
		sartuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String izena = textErabiltzailea.getText();
				String pasahitza = new String(passwordField.getPassword());
				String email = textEmail.getText();
				String mota = erabiltzaileak.getSelectedItem().toString();
				if (!izena.isEmpty() && !pasahitza.isEmpty() && !email.isEmpty() && !mota.isEmpty()) {
					BLFacade facade = MainGUI.getBusinessLogic();
					User user = facade.register(izena, email, pasahitza, mota);
					System.out.println("Iritsi da");
					if (user!=null) {
						sarreraText.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Success"));
						if (user instanceof Driver) { //Driver-bat da
							DriverGUI a=new DriverGUI((Driver)user);
							a.setVisible(true);
							closeWindow(e);
						} else {
							if (user instanceof Admin){
								AdminGUI a=new AdminGUI((Admin)user);
								a.setVisible(true);
								closeWindow(e);
							}else {
								TravelerGUI a=new TravelerGUI((Traveler)user);
								a.setVisible(true);
								closeWindow(e);
							}
						}
					} else {
						sarreraText.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.AccountAlreadyExistErrorMsg"));
					}
					
				} else {
					sarreraText.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.InvalidDataErrorMsg"));
				}
			}
		});
		sartuBotoia.setBounds(168, 205, 113, 21);
		contentPane.add(sartuBotoia);
		
		
	}
	private void closeWindow(ActionEvent e) {
		this.setVisible(false);
	}
}

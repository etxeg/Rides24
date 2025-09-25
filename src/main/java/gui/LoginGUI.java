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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textErabiltzailea;
	private JPasswordField textPasahitza;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
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
		textErabiltzailea.setBounds(251, 33, 96, 19);
		contentPane.add(textErabiltzailea);
		textErabiltzailea.setColumns(10);
		
		JLabel usernameText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.User")); //$NON-NLS-1$ //$NON-NLS-2$
		usernameText.setBounds(95, 36, 86, 13);
		contentPane.add(usernameText);
		
		JLabel passwordText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password")); //$NON-NLS-1$ //$NON-NLS-2$
		passwordText.setBounds(95, 71, 86, 13);
		contentPane.add(passwordText);
		
		textPasahitza = new JPasswordField();
		textPasahitza.setBounds(251, 68, 96, 19);
		contentPane.add(textPasahitza);
		
		JTextPane sarreraText = new JTextPane();
		sarreraText.setEditable(false);
		sarreraText.setBounds(95, 116, 252, 57);
		contentPane.add(sarreraText);
		
		JButton sartuBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Enter"));
		sartuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String izena = textErabiltzailea.getText();
				String pasahitza = new String(textPasahitza.getPassword());
				BLFacade facade = MainGUI.getBusinessLogic();
				User user = facade.checkLogin(izena, pasahitza);
				if (user != null) {
					//System.out.println(user.getName());
					sarreraText.setEnabled(false);
					sarreraText.setText("Pasahitza zuzena da.");
					if (user instanceof Driver) { //Driver-bat da
						DriverGUI a=new DriverGUI((Driver)user);
						a.setVisible(true);
						closeWindow(e);
					} else {
						if(user instanceof Admin) {
							AdminGUI a=new AdminGUI((Admin)user);
							a.setVisible(true);
							closeWindow(e);
						}else {
						TravelerGUI a=new TravelerGUI((Traveler)user);
						a.setVisible(true);
						closeWindow(e);
					}
				}} else {
					sarreraText.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.InvalidUserErrorMsg"));
				}
			}
		});
		sartuBotoia.setBounds(176, 183, 85, 21);
		contentPane.add(sartuBotoia);
	}
	private void closeWindow(ActionEvent e) {
		this.setVisible(false);
	}
}

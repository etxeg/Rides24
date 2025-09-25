package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

import domain.Driver;
import domain.Traveler;
import domain.User;
import javax.swing.SwingConstants;

public class ConfirmDeleteGUI extends JFrame {

	private User user;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ConfirmDeleteGUI(User user) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		BLFacade facade = MainGUI.getBusinessLogic();

		JButton btnBai = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteGUI.Yes"));
		btnBai.setBounds(67, 155, 120, 30);
		btnBai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.deleteUser(user);
				JFrame a = new MainGUI();
				a.setVisible(true);
				closeWindow(e);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnBai);

		JButton btnEz = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteGUI.No"));
		btnEz.setBounds(262, 155, 120, 30);
		btnEz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user instanceof Traveler) {
					Traveler traveler = (Traveler) user;
					JFrame a = new TravelerGUI(traveler);
					a.setVisible(true);
					
				} else if (user instanceof Driver) {
					Driver driver = (Driver) user;
					JFrame a = new DriverGUI(driver);
					a.setVisible(true);
				}
				closeWindow(e);
			}
		});
		contentPane.add(btnEz);
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ConfirmDeleteGUI"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(67, 68, 315, 30);
		contentPane.add(lblNewLabel);
	}
	private void closeWindow(ActionEvent e) {
		this.setVisible(false);
	}
}

package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Car;
import domain.Driver;
import domain.Traveler;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class AddCarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField matrikulaBox;
	private JTextField plazakBox;
	private JTextField markaBox;
 
	public AddCarGUI(Driver driver) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel matrikulaText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCar.Plate"));
		matrikulaText.setBounds(90, 63, 91, 13);
		contentPane.add(matrikulaText);
		
		matrikulaBox = new JTextField();
		matrikulaBox.setBounds(223, 60, 96, 19);
		contentPane.add(matrikulaBox);
		matrikulaBox.setColumns(10);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(129, 232, 85, 21);
		contentPane.add(jButtonClose);
		
		plazakBox = new JTextField();
		plazakBox.setColumns(10);
		plazakBox.setBounds(223, 89, 96, 19);
		contentPane.add(plazakBox);
		
		JLabel plazakText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCar.Places"));
		plazakText.setBounds(90, 92, 91, 13);
		contentPane.add(plazakText);
		
		markaBox = new JTextField();
		markaBox.setColumns(10);
		markaBox.setBounds(223, 118, 96, 19);
		contentPane.add(markaBox);
		
		JLabel markaText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCar.Brand"));
		markaText.setBounds(90, 121, 91, 13);
		contentPane.add(markaText);
		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		
		JLabel errorText = new JLabel();
		errorText.setVisible(false);
		errorText.setHorizontalAlignment(SwingConstants.CENTER);
		errorText.setBounds(46, 161, 342, 19);
		contentPane.add(errorText);
		
		JButton btnAdd = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddMoneyGUI.Add"));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pMatrikula = matrikulaBox.getText();
				String pMarka = markaBox.getText();
				if (pMatrikula == null || pMatrikula.isEmpty() || pMarka == null || pMarka.isEmpty()) {
					return;
				}
				try {
					int nPlazak = Integer.parseInt(plazakBox.getText());
					
					Car createdCar =facade.createCar(driver, pMatrikula, nPlazak, pMarka);
					if (createdCar != null) {
						jButton2_actionPerformed(e);//Itxi leihoa
					} else {
						errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("AddCar.CarExisted"));
						errorText.setVisible(true);
					}
					
				} catch (NumberFormatException e1) {
					errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("AddCar.MustBeNumber"));
					errorText.setVisible(true);
				}
			}
		});
		btnAdd.setBounds(223, 232, 85, 21);
		contentPane.add(btnAdd);
		
		setTitle(driver.getName());
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

package gui;

import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Traveler;
import domain.User;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class AlertGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNondik;
	private JTextField textFieldNora;
	private JTextField textFieldData;
	private BLFacade facade = MainGUI.getBusinessLogic();
 
	public AlertGUI(Traveler user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null); 
		
		JLabel lblNondik = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.Nondik"));
		lblNondik.setBounds(60, 43, 102, 14);
		contentPane.add(lblNondik);
		
		JLabel lblNora = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.Nora"));
		lblNora.setBounds(60, 77, 102, 14);
		contentPane.add(lblNora);
		
		textFieldNondik = new JTextField();
		textFieldNondik.setBounds(188, 40, 86, 20);
		contentPane.add(textFieldNondik);
		textFieldNondik.setColumns(10);
		
		textFieldNora = new JTextField();
		textFieldNora.setBounds(188, 74, 86, 20);
		contentPane.add(textFieldNora);
		textFieldNora.setColumns(10);
		
		JLabel lblData = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.Data"));
		lblData.setBounds(60, 129, 102, 14);
		contentPane.add(lblData);
		
		textFieldData = new JTextField();
		textFieldData.setBounds(188, 126, 86, 20);
		contentPane.add(textFieldData);
		textFieldData.setColumns(10);
		
		JLabel errorTextLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.DateError"));
		errorTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorTextLabel.setVisible(false);
		errorTextLabel.setBounds(54, 171, 342, 13);
		contentPane.add(errorTextLabel);
		
		JButton jButtonCreateAlert = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertGUI.jButtonCreateAlert")); 
		jButtonCreateAlert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldNondik.getText() == null || textFieldNondik.getText().isEmpty() || textFieldNora.getText() == null || textFieldNora.getText().isEmpty()) {
					return;
				}
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				Date fecha;
				try {
					fecha = format.parse(textFieldData.getText());
					facade.createAlert(user, textFieldNondik.getText(), textFieldNora.getText(), fecha);
					jButton2_actionPerformed(e);
				} catch (ParseException e1) {
					errorTextLabel.setVisible(true);
				}
			}
		});
		jButtonCreateAlert.setBounds(185, 203, 89, 23);
		contentPane.add(jButtonCreateAlert);
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}


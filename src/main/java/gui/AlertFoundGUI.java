package gui;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Ride;
import domain.Traveler;
import domain.User;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class AlertFoundGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public AlertFoundGUI(List<Ride> rides) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblData = new JLabel("Loading...");
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setBounds(10, 40, 416, 26);
		
		lblData.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertFoundGUI.Message"));
		
		contentPane.add(lblData);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(180, 232, 85, 21);
		contentPane.add(jButtonClose);
		
		
		DefaultListModel<Ride> listModel = new DefaultListModel<>();
		for (Ride ride : rides) {
		    listModel.addElement(ride);
		}
		JList<Ride> rideJList = new JList<>(listModel);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(80, 93, 285, 108);
		scrollPane.setViewportView(rideJList);
		contentPane.add(scrollPane);
		
		
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


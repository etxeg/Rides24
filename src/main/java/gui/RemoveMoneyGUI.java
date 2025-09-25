package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Traveler;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class RemoveMoneyGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField addMoney;
 
	public RemoveMoneyGUI(User user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel moneyQuantityText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RemoveMoneyGUI.MoneyQuantity"));
		moneyQuantityText.setBounds(90, 63, 91, 13);
		contentPane.add(moneyQuantityText);
		
		addMoney = new JTextField();
		addMoney.setBounds(223, 60, 96, 19);
		contentPane.add(addMoney);
		addMoney.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(101, 153, 252, 57);
		contentPane.add(textPane);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton btnAdd = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RemoveMoneyGUI.Remove"));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dirua = addMoney.getText();
				if (dirua == null || dirua.isEmpty()) {
					return;
				}
				float diruFloat = Float.parseFloat(dirua);
				boolean a = facade.withdrawMoney(user, diruFloat);
				if (a==false) {
					textPane.setText("Ezin da dirua atera.");
				}else {
					jButton2_actionPerformed(e);//Itxi leihoa
				}
			}
		});
		btnAdd.setBounds(180, 107, 85, 21);
		contentPane.add(btnAdd);
		
		JLabel lblNewLabel_1 = new JLabel("€");
		lblNewLabel_1.setBounds(326, 63, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(180, 232, 85, 21);
		contentPane.add(jButtonClose);
		
		
		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		
		setTitle(user.getName()+": "+user.getMoney()+"€");
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

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

public class AddMoneyGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField addMoney;
 
	public AddMoneyGUI(User user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel moneyQuantityText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddMoneyGUI.MoneyQuantity"));
		moneyQuantityText.setBounds(90, 63, 91, 13);
		contentPane.add(moneyQuantityText);
		
		addMoney = new JTextField();
		addMoney.setBounds(223, 60, 96, 19);
		contentPane.add(addMoney);
		addMoney.setColumns(10);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton btnAdd = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddMoneyGUI.Add"));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dirua = addMoney.getText();
				if (dirua == null || dirua.isEmpty()) {
					return;
				}
				float diruFloat = Float.parseFloat(dirua);
				facade.addMoney(user, diruFloat);
				
				jButton2_actionPerformed(e);//Itxi leihoa
			}
		});
		btnAdd.setBounds(180, 146, 85, 21);
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

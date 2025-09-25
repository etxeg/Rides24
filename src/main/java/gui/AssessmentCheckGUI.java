package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Assessment;
import domain.Driver;
import domain.Reservation;
import domain.Ride;
import domain.Traveler;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;

public class AssessmentCheckGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Assessment> assessmentComboBox = new JComboBox<Assessment>();

	/**
	 * Create the frame.
	 */
	public AssessmentCheckGUI(Driver driver) {
		boolean balorazioaDu = false;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel assessmentText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AssessmentCheckGUI.SelectReview"));
		assessmentText.setHorizontalAlignment(SwingConstants.CENTER);
		assessmentText.setBounds(83, 28, 260, 20);
		contentPane.add(assessmentText);
		
		JLabel starsText = new JLabel("Loading...");
		starsText.setBounds(193, 133, 85, 13);
		contentPane.add(starsText);
		
        
        assessmentComboBox.setBounds(63, 58, 300, 20);
        contentPane.add(assessmentComboBox);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(168, 236, 100, 21);
		contentPane.add(jButtonClose);
		
		JLabel starsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AssessmentCheckGUI.Stars"));
		starsLabel.setBounds(83, 126, 100, 27);
		contentPane.add(starsLabel);
		
		JLabel reviewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AssessmentCheckGUI.Review"));
		reviewLabel.setBounds(83, 156, 100, 27);
		contentPane.add(reviewLabel);
		
		JLabel noAssessmentLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AssessmentCheckGUI.noAssessment"));
		noAssessmentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noAssessmentLabel.setBounds(63, 110, 300, 13);
		contentPane.add(noAssessmentLabel);
		
		JTextPane reviewText = new JTextPane();
		reviewText.setEditable(false);
		reviewText.setBounds(193, 156, 218, 70);
		contentPane.add(reviewText);
		
		starsLabel.setVisible(false);
		reviewLabel.setVisible(false);
		starsText.setVisible(false);
		reviewText.setVisible(false);
		
		for (Assessment actualAssessment : driver.getAssessments()){
        	if (balorazioaDu == false) {
        		balorazioaDu = true;
        		String actualStars = actualAssessment.getStars()+"/5";
				String actualReview = actualAssessment.getText();
				starsText.setText(actualStars);
				reviewText.setText(actualReview);
				
				starsLabel.setVisible(true);
				reviewLabel.setVisible(true);
				starsText.setVisible(true);
				reviewText.setVisible(true);
				noAssessmentLabel.setVisible(false);
				
        	}
            assessmentComboBox.addItem(actualAssessment);
        }
		
		assessmentComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Assessment selectedAssessment = (Assessment) assessmentComboBox.getSelectedItem();
				String actualStars = selectedAssessment.getStars()+"/5";
				String actualReview = selectedAssessment.getText();
				starsText.setText(actualStars);
				reviewText.setText(actualReview);
			}
		});
		
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

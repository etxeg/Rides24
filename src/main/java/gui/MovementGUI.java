package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Movement;
import domain.User;
import javax.swing.JList;

public class MovementGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private String[] columnNamesMovement = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("MovementGUI.ID"),
			ResourceBundle.getBundle("Etiquetas").getString("MovementGUI.Sarrera"),
			ResourceBundle.getBundle("Etiquetas").getString("MovementGUI.Zenbat"), 
			ResourceBundle.getBundle("Etiquetas").getString("MovementGUI.Data")
	};


    public MovementGUI(User user) {
        setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Movements"));
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        
        
        tableModel = new DefaultTableModel(columnNamesMovement, 0);
        table = new JTable(tableModel);
        for (Movement m : user.getMovements()) {
            Object[] row = {m.getMugimenduZenbakia(), m.getSarreraDa() ? "+" : "-", m.getDirua(), m.getData()};
            tableModel.addRow(row);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        setVisible(true);
        
    }
	
}

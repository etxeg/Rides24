package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Driver;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import businessLogic.BLFactory;

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();
	
		System.out.println(c.getLocale());
		
		Locale.setDefault(new Locale(c.getLocale()));
		
		System.out.println("Locale: "+Locale.getDefault());
		
	    Driver driver=new Driver("driver3@gmail.com","Test Driver","123");

		
		//a.setVisible(true);


		try {
			
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			boolean isLocal = c.isBusinessLogicLocal();
			BLFacade appFacadeInterface = new BLFactory().getBusinessLogicFactory(isLocal);
			
			MainGUI a=new MainGUI();
			MainGUI.setBussinessLogic(appFacadeInterface);
			
			a.setVisible(true);
			//MainGUI a=new MainGUI(driver);
		

			
		}catch (Exception e) {
			/*a.jLabelSelectOption.setText("Error: "+e.toString());
			a.jLabelSelectOption.setForeground(Color.RED);	*/
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		//a.pack();


	}

}

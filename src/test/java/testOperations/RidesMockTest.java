package testOperations;

import javax.swing.UIManager;

import org.mockito.Mockito;

import businessLogic.BLFacade;
import gui.MainGUI;

public class RidesMockTest {
	static BLFacade appFacadeInterface = Mockito.mock(BLFacade.class);
	
	public static void main(String args[]) {
			MainGUI sut = new MainGUI();
			MainGUI.setBussinessLogic(appFacadeInterface);
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sut.setVisible(true);
	}
}

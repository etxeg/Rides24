package adapter;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Driver;

public class DriverTableMain {

	public static void main(String[] args) {
//		the	BL	is	local
		boolean isLocal =	true;
		BLFacade blFacade =	 new BLFacadeImplementation();//new BLFactory().getBusinessLogicFactory(isLocal);
		Driver	d= blFacade.getDriverByName("Urtzi");
		DriverTable	dt=new	DriverTable(d);
		dt.setVisible(true);
	}

}

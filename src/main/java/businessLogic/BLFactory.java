package businessLogic;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class BLFactory {
	 public BLFacade getBusinessLogicFactory(boolean isLocal) {
	        ConfigXML c = ConfigXML.getInstance();

	        try {
	            if (isLocal) {
	                DataAccess da = new DataAccess();
	                return new BLFacadeImplementation(da);
	            } else {
					String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
					URL url = new URL(serviceName);
			        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
			        Service service = Service.create(url, qname);
	                return service.getPort(BLFacade.class);
	            }
	        } catch (Exception e) {
	            System.out.println("Error in BLFactory: " + e.toString());
	            return null;
	        }
	    }
}

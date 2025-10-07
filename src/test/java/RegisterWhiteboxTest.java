import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Traveler;
import domain.User;
import testOperations.TestDataAccess;

public class RegisterWhiteboxTest {
	
	static DataAccess sut = new DataAccess();
	
	static TestDataAccess testDA = new TestDataAccess();
	
	

	@Test
	// sut.register -> user already exists
	public void testRegisterUserExists() {
		
		testDA.open();
		testDA.createDriver("a@a.com", "A");
		testDA.close();
		
		sut.open();
		User resultUser = sut.register("A", "a@a.com", "123456", "Driver");
		sut.close();
		
		assertNull(resultUser);
	}
	
	
	@Test
	//sut.register -> new Driver
	public void testRegisterNewDriver() {
		
		
		sut.open();
		User resultUser = sut.register("Jon", "jon@jon.com", "1234", "Driver");
		sut.close();
		
		
		
		assertNotNull(resultUser);
		assertTrue(resultUser instanceof Driver);
		testDA.open();
		assertTrue(testDA.existDriver("jon@jon.com"));
		testDA.close();
	}
	
	
	
	@Test
	//sut.register -> new Traveler
	public void testRegisterNewTraveler() {
		
		
		sut.open();
		User resultUser = sut.register("Xabi", "xabi@xabi.com", "1234", "Traveler");
		sut.close();
		
		
		
		assertNotNull(resultUser);
		assertTrue(resultUser instanceof Traveler);
		assertTrue(testDA.existTraveler("xabi@xabi.com"));
	}
	
	@Test
	public void testRegisterInvalidMota() {
		
		sut.open();
		User resultUser = sut.register("Mikel", "mikel@mikel.com", "1234", "AAA");
		sut.close();
		
		assertNull(resultUser);
	}

}

import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.User;
import testOperations.TestDataAccess;

class InvalidInputException extends RuntimeException {
}

class UserAlreadyExistsException extends RuntimeException {
}

public class RegisterBlackboxTest {

	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	@Test
	public void testRegisterNewDriver() {
		sut.open();
		User result = sut.register("Xabi", "a@a.com", "Aa123456!", "Driver");
		sut.close();
		assertNotNull(result);
		assertTrue(result instanceof Driver);
		assertTrue(testDA.existDriver("a@a.com"));
	}

	@Test
	public void testRegisterUserAlreadyExists() {
		testDA.open();
		testDA.createDriver("a@a.com", "Xabi");
		testDA.close();
		try {
			sut.open();
			User result = sut.register("Xabi", "a@a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected UserAlreadyExistsException");
		} catch (UserAlreadyExistsException e) {
			// Pass
		}

	}

	@Test
	public void testRegisterNameNull() {
		try {
			sut.open();
			sut.register(null, "a@a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

	@Test
	public void testRegisterEmailNull() {
		try {
			sut.open();
			sut.register("Xabi", null, "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

	@Test
	public void testRegisterPasswordNull() {
		try {
			sut.open();
			sut.register("Xabi", "a@a.com", null, "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

	@Test
	public void testRegisterMotaNull() {
		try {
			sut.open();
			sut.register("Xabi", "a@a.com", "Aa123456!", null);
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

	@Test
	public void testRegisterInvalidName() {
		try {
			sut.open();
			sut.register("Xabi123", "a@a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

	@Test
	public void testRegisterInvalidEmail() {
		try {
			sut.open();
			sut.register("Xabi", "a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

	@Test
	public void testRegisterInvalidPassword() {
		try {
			sut.open();
			sut.register("Xabi", "a@a.com", "123456", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

	@Test
	public void testRegisterInvalidMota() {
		try {
			sut.open();
			sut.register("Xabi", "a@a.com", "Aa123456!", "User");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
	}

}

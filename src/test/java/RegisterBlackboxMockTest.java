import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.junit.*;
import org.mockito.*;

import dataAccess.DataAccess;
import domain.Driver;
import domain.User;

public class RegisterBlackboxMockTest {

	static DataAccess sut;

	@Mock
	EntityManager db;
	@Mock
	EntityTransaction et;
	@Mock
	TypedQuery<User> query;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);
	}

	// Helper to mock query returning some users
	private void mockQueryResult(List<User> resultList) {
		when(db.createQuery(anyString(), eq(User.class))).thenReturn(query);
		when(query.setParameter(anyInt(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(resultList);
	}

	@Test
	public void testRegisterNewDriver() {
		mockQueryResult(Collections.emptyList());

		sut.open();
		User result = sut.register("Xabi", "a@a.com", "Aa123456!", "Driver");
		sut.close();
		assertNotNull(result);
		assertTrue(result instanceof Driver);
		verify(db).persist(any(Driver.class));
	}

	@Test
	public void testRegisterUserAlreadyExists() {
		mockQueryResult(List.of(new Driver("a@a.com", "Xabi", "Aa123456!")));

		try {
			sut.open();
			sut.register("Xabi", "a@a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected UserAlreadyExistsException");
		} catch (UserAlreadyExistsException e) {
			// Passes
		}
		verify(db, never()).persist(any());
	}

	@Test
	public void testRegisterNameNull() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register(null, "a@a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}

	// 4️⃣ Email is null
	@Test
	public void testRegisterEmailNull() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register("Xabi", null, "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}

	// 5️⃣ Password is null
	@Test
	public void testRegisterPasswordNull() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register("Xabi", "a@a.com", null, "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}

	@Test
	public void testRegisterMotaNull() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register("Xabi", "a@a.com", "Aa123456!", null);
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}

	@Test
	public void testRegisterInvalidName() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register("Xabi123", "a@a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}

	@Test
	public void testRegisterInvalidEmail() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register("Xabi", "a.com", "Aa123456!", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}

	@Test
	public void testRegisterInvalidPassword() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register("Xabi", "a@a.com", "123456", "Driver");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}

	@Test
	public void testRegisterInvalidMota() {
		mockQueryResult(Collections.emptyList());

		try {
			sut.open();
			sut.register("Xabi", "a@a.com", "Aa123456!", "User");
			sut.close();
			fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			// Pass
		}
		verify(db, never()).persist(any());
	}
}

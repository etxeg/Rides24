import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.*;
import org.mockito.*;

import dataAccess.DataAccess;
import domain.*;

public class RegisterWhiteboxMockTest {
	//change 1
// change2
	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	@Mock
	protected TypedQuery<User> query;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);

		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

	@Test
	public void testRegisterUserExists() {
		// Simulate existing user in DB
		List<User> existingUsers = List.of(new Driver("a@a.com", "A", "123456"));
		when(db.createQuery(anyString(), eq(User.class))).thenReturn(query);
		when(query.setParameter(anyInt(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(existingUsers);

		sut.open();
		User result = sut.register("A", "a@a.com", "123456", "Driver");
		sut.close();

		assertNull(result);
		verify(db, never()).persist(any());
	}

	@Test
	public void testRegisterNewDriver() {
		when(db.createQuery(anyString(), eq(User.class))).thenReturn(query);
		when(query.setParameter(anyInt(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(Collections.emptyList());

		sut.open();
		User result = sut.register("Jon", "jon@jon.com", "123456", "Driver");
		sut.close();

		assertNotNull(result);
		assertTrue(result instanceof Driver);
		verify(db).persist(any(Driver.class));
	}

	@Test
	public void testRegisterNewTraveler() {
		when(db.createQuery(anyString(), eq(User.class))).thenReturn(query);
		when(query.setParameter(anyInt(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(Collections.emptyList());

		sut.open();
		User result = sut.register("Xabi", "xabi@xabi.com", "123456", "Traveler");
		sut.close();

		assertNotNull(result);
		assertTrue(result instanceof Traveler);
		verify(db).persist(any(Traveler.class));
	}

	@Test
	public void testRegisterInvalidMota() {
		when(db.createQuery(anyString(), eq(User.class))).thenReturn(query);
		when(query.setParameter(anyInt(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(Collections.emptyList());

		sut.open();
		User result = sut.register("Mikel", "mikel@mikel.com", "123456", "User");
		sut.close();

		assertNull(result);
		verify(db, never()).persist(any());
	}

}

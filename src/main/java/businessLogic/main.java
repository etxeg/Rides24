package businessLogic;

public class main {

	public static void main(String[] args) {
		boolean isLocal = true;
		BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);
		ExtendedIterator<String> i = ((BLFacadeImplementation)blFacade).getDepartingCitiesIterator();
		String c;
		System.out.println("_____________________");
		System.out.println("FROM	LAST	TO	FIRST");
		i.goLast(); // Go to last element
		while (i.hasPrevious()) {
			c = i.previous();
			System.out.println(c);
		}
		System.out.println();
		System.out.println("_____________________");
		System.out.println("FROM	FIRST	TO	LAST");
		i.goFirst(); // Go to first element
		while (i.hasNext()) {
			c = i.next();
			System.out.println(c);
		}
	}

}

package businessLogic;

import java.util.List;
import java.util.NoSuchElementException;

public class ExtendedIteratorCities implements ExtendedIterator<String> {

	private List<String> cities;
	private int index = -1;

	public ExtendedIteratorCities(List<String> cities) {
		this.cities = cities;
	}

	@Override
	public boolean hasNext() {
		return index + 1 < cities.size();
	}

	@Override
	public String next() {
		if (!this.hasNext()) throw new NoSuchElementException();
		index++;
		return cities.get(index);
	}

	@Override
	public String previous() {
		if(!this.hasPrevious()) throw new NoSuchElementException();
		index--;
		return cities.get(index);
	}

	@Override
	public boolean hasPrevious() {
		return (index - 1) >= 0;
	}

	@Override
	public void goFirst() {
		index= -1;

	}

	@Override
	public void goLast() {
		index=cities.size();

	}

}

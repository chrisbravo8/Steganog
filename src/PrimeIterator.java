import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimeIterator implements Iterator<Integer> {

	List<Integer> list = new ArrayList();
	int it = -1;
	public PrimeIterator(int max) {
		boolean[] primeCandidates = new boolean[max]; 
		for (int i = 2; i < max; i++) {
			primeCandidates[i] = true;
		}

		for (int i = 2; i < Math.sqrt(max); i++) {
			if (primeCandidates[i] == true) {
				for (int j = i + i; j < max; j = j + i) {
					primeCandidates[j] = false;
				}
			}

		}

		for (int i = 0; i < primeCandidates.length; i++) {
			if (primeCandidates[i] == true) {
				list.add(i);
			}
		}

	}

	/**
	 * Returns whether this iterator has any more prime numbers less than max
	 **/
	@Override
	public boolean hasNext() {
		int temp = it+1;
		if(list.size()>temp){
		return true;
		
		}
		else{
			return false;
		}
	}

	/**
	 * Gets the next prime number
	 **/
	@Override
	public Integer next() {
		it++;
		int num = list.get(it);
		return num;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}
}

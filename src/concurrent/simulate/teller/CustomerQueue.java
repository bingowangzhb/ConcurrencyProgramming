package concurrent.simulate.teller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Customer queue
 * 
 * @author Charles Chen
 * @date 2014Äê9ÔÂ3ÈÕ
 */
public class CustomerQueue extends ArrayBlockingQueue<Customer> {
	/**
	 * Constructor
	 * 
	 * @param capacity
	 */
	public CustomerQueue(int capacity) {
		super(capacity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public String toString() {
		Iterator<Customer> it = this.iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}

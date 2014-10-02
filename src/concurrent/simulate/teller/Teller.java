package concurrent.simulate.teller;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Teller task, take customers from queue and serve
 * 
 * @author Charles Chen
 * @date 2014Äê9ÔÂ1ÈÕ
 */
public class Teller implements Runnable {
	private int id;
	private CustomerQueue customers;
	private boolean serving = true;

	public Teller(int id, CustomerQueue customers) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.customers = customers;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (!Thread.interrupted()) {
				// If teller is not in serving status, wait
				if (!serving) {
					synchronized (this) {
						this.wait();
					}
				}
				// Take a customer from queue and serve
				Customer customer = customers.take();
				TimeUnit.MILLISECONDS.sleep(customer.getDuration());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Teller stops serving for customers, doing other things
	 */
	public void doOtherThing() {
		if (serving) {
			serving = false;
		}
	}

	/**
	 * Teller resumes serving for customers
	 */
	public synchronized void resumeServe() {
		if (!serving) {
			serving = true;
			this.notifyAll();
		}
	}

	public String toString() {
		return "Teller" + id;
	}
}

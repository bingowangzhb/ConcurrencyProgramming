package concurrent.simulate.teller;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Customer generator, add customer into queue in random time gap
 * 
 * @author Charles Chen
 * @date 2014Äê9ÔÂ1ÈÕ
 */
public class CustomerGenerator implements Runnable {
	private Random random = new Random();
	private BlockingQueue<Customer> customers;

	public CustomerGenerator(BlockingQueue<Customer> customers) {
		// TODO Auto-generated constructor stub
		this.customers = customers;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int id = 0;
		while (!Thread.interrupted()) {
			try {
				customers.put(new Customer(id++, 1000 + random.nextInt(1000)));
				TimeUnit.MILLISECONDS.sleep(500 + random.nextInt(1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
	}
}

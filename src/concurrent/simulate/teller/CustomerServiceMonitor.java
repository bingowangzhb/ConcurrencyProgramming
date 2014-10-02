package concurrent.simulate.teller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Adjust tellers in specific period
 * 
 * @author Charles Chen
 * @date 2014Äê9ÔÂ4ÈÕ
 */
public class CustomerServiceMonitor implements Runnable {
	private CustomerQueue customers; // Customer queue
	private int period; // Period in millisecond
	private ExecutorService exec;
	private Queue<Teller> workingTellers; // Teller queue in working
	private Queue<Teller> freeTellers; // Teller queue in free
	private int id = 0; // Teller id

	public CustomerServiceMonitor(CustomerQueue customers, int period,
			ExecutorService exec) {
		// TODO Auto-generated constructor stub
		this.customers = customers;
		this.period = period;
		this.exec = exec;

		workingTellers = new LinkedList<Teller>();
		freeTellers = new LinkedList<Teller>();
		// Add a teller to serve the customers
		Teller teller = new Teller(id++, customers);
		exec.execute(teller);
		workingTellers.offer(teller);
	}

	private void adjust() {
		// If tellers are not enough, resume one from free tellers queue, or add
		// a new one
		if (customers.size() > 2 * workingTellers.size()) {
			// If free tellers queue has tellers, resume one and add into
			// working tellers queue
			if (freeTellers.size() > 0) {
				Teller teller = freeTellers.poll();
				if (teller != null) {
					teller.resumeServe();
					workingTellers.offer(teller);
				}
			} else {
				// Else, add a new teller
				Teller teller = new Teller(id++, customers);
				exec.execute(teller);
				workingTellers.offer(teller);
			}
		}
		// If tellers are more than customers, remove one and add into free
		// tellers queue
		if (customers.size() < workingTellers.size()) {
			Teller teller = workingTellers.poll();
			if (teller != null) {
				teller.doOtherThing();
				freeTellers.offer(teller);
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			this.adjust();
			System.out.println(status());
			try {
				TimeUnit.MILLISECONDS.sleep(period);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
	}

	public String status() {
		StringBuilder sb = new StringBuilder();
		sb.append(customers);
		sb.append(" {");
		Iterator<Teller> it = workingTellers.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}
}

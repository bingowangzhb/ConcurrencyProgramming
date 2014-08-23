package concurrent.deadlock;

import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {
	private int id;
	private Chopstick left;
	private Chopstick right;

	public Philosopher(int id, Chopstick left, Chopstick right) {
		this.id = id;
		this.left = left;
		this.right = right;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Philosopher#" + this.id
				+ " is trying to hold his left chopstick#" + left.getId());
		left.hold();
		System.out.println("Cool, Philosopher#" + this.id
				+ " held his left chopstick#" + left.getId());
		try {
			TimeUnit.MICROSECONDS.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		System.out.println("Philosopher#" + this.id
				+ " is trying to hold his right chopstick#" + right.getId());
		right.hold();
		System.out.println("Cool, Philosopher#" + this.id
				+ " held his right chopstick#" + right.getId());
		System.out.println("Philosopher#" + this.id + " is eating");

		try {
			TimeUnit.MICROSECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		left.release();
		right.release();
	}

}

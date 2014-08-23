package concurrent.deadlock;

public class Chopstick {
	private int id;
	private volatile boolean isHeld = false;

	public Chopstick(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public synchronized void hold() {
		if (isHeld) {
			System.out.println("The chopstick#" + this.id
					+ " is held, waiting for being released");
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.isHeld = true;
	}

	public synchronized void release() {
		this.isHeld = false;
		this.notifyAll();
	}
}

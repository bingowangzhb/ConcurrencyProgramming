package concurrent.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserUseCase2 {
	public static int maxDist = 1000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int walker_count = 5;
		int init_distance = 0;
		Phaser phaser = new Phaser() {
			public boolean onAdvance(int phase, int registeredParties) {
				System.out.println("arrived");
				return true;
			}
		};
		ExecutorService exec = Executors.newCachedThreadPool();
		while (--walker_count >= 0) {
			exec.execute(new Walker(walker_count, phaser, init_distance));
		}
		exec.shutdown();
	}

}

class Walker implements Runnable {
	private int id;
	private Phaser phaser;
	private int distance;

	public Walker(int id, Phaser phaser, int distance) {
		this.id = id;
		this.phaser = phaser;
		this.distance = distance;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		phaser.register();
		while (!Thread.interrupted()) {
			System.out.println("Walker#" + id + " is walking...");
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			distance += 100;
			System.out.println("Walker#" + id + " has arrived " + distance);
			phaser.arriveAndAwaitAdvance();
			if (distance >= PhaserUseCase2.maxDist) {
				break;
			}
		}
	}

}
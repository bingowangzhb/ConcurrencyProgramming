package concurrent.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Using phaser to simulate CountDownLatch
 * 
 * Use Case:
 * 
 * Start 5 worker tasks, each worker will register in the phaser, and after
 * working, will arrive the phaser.
 * 
 * Start 1 warper tasks, the wraper will await in the beginnig, so after all
 * workers arrive the phaser, the phaser will notify all waiting tasks
 * 
 * @author Charles Chen
 * @date 2014年8月19日
 */
public class PhaserUseCase1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int worker_count = 5;
		Phaser phaser = new Phaser();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Wraper(phaser));
		while (--worker_count >= 0) {
			exec.execute(new Worker(worker_count, phaser));
		}

	}

}

/**
 * Worker task, produce the pieces of a product
 * 
 * @author Charles Chen
 * @date 2014年8月19日
 */
class Worker implements Runnable {
	private int id;
	private Phaser phaser;

	public Worker(int id, Phaser phaser) {
		this.id = id;
		this.phaser = phaser;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		phaser.register();
		System.out.println("Worker#" + id + " is working...");
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Worker#" + id + " is done.");
		phaser.arriveAndDeregister();
	}

}

/**
 * Wraper task, start working after all workers is done
 * 
 * @author Charles Chen
 * @date 2014年8月19日
 */
class Wraper implements Runnable {
	private Phaser phaser;

	public Wraper(Phaser phaser) {
		this.phaser = phaser;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		phaser.awaitAdvance(phaser.getPhase());
		System.out.println("The wraper is working...");
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("The wraper is done.");
	}

}
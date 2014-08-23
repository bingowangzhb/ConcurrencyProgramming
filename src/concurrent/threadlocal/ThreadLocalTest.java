package concurrent.threadlocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
	private ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
		private Random rand = new Random();

		public Integer initialValue() {
			return rand.nextInt(100);
		}
	};

	public int getValue() {
		return value.get();
	}

	public void increase() {
		this.value.set(value.get() + 1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadLocalTest tlt = new ThreadLocalTest();
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			exec.execute(new VisitValue(tlt, i));
		}
		
		try {
			TimeUnit.MICROSECONDS.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class VisitValue implements Runnable {
	private ThreadLocalTest tlt;
	private int id;

	public VisitValue(ThreadLocalTest tlt, int id) {
		this.tlt = tlt;
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			System.out.println("#" + this.id + ": Value is " + tlt.getValue());
			tlt.increase();
		}
	}

}

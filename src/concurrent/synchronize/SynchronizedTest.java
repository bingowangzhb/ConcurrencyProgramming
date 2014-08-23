package concurrent.synchronize;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest {
	public final static DualSync ds = new DualSync();
	public static Thread t1 = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ds.method1();
		}

	});
	public static Thread t2 = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ds.method2();
		}

	});

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		t1.start();
		t2.start();
	}

}

class DualSync {
	private Object obj = new Object();

	public synchronized void method1() {
		for (int i = 0; i < 5; i++) {
			System.out.println("method1 is running");
			try {
				SynchronizedTest.t2.join(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void method2() {
		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				System.out.println("method2 is running");
				try {
					SynchronizedTest.t1.join(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
package concurrent.deadlock;

import java.util.concurrent.TimeUnit;

public class SimpleDeadlock implements Runnable {
	private String a;
	private String b;

	public SimpleDeadlock(String a, String b) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (a.intern()) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (b.intern()) {
				System.out.println(a + b);
			}
		}
	}

	public static void main(String[] args) {
		String a = "a";
		String b = "b";
		new Thread(new SimpleDeadlock(a, b), "Thread-01").start();
		new Thread(new SimpleDeadlock(b, a), "Thread-02").start();
	}
}

package concurrent.daemon;

import java.util.concurrent.TimeUnit;

public class MyTask implements Runnable {
	private int times;

	public MyTask(int times) {
		this.times = times;
	}

	public void run() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= times; i++) {
			System.out
					.println((Thread.currentThread().isDaemon() ? "Daemon thread is executing: "
							: "User thread is executing")
							+ i);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out
				.println((Thread.currentThread().isDaemon() ? "Daemon thread is end: "
						: "User thread is end"));
	}
}

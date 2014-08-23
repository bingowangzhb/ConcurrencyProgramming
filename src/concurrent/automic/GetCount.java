package concurrent.automic;

import java.util.concurrent.TimeUnit;

public class GetCount {
	private volatile int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static void main(String[] args) {
		final GetCount count = new GetCount();

		Thread t1 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				if (count.getCount() == 0) {
					System.out.println(Thread.currentThread().getName()
							+ ": Current count is 0");
					try {
						TimeUnit.MICROSECONDS.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()
							+ ": Current Count is " + count.getCount());
				}
			}

		}, "Thread1");
		Thread t2 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				count.setCount(count.getCount() + 1);
			}

		}, "Thread2");

		t1.start();
		t2.start();
	}
}

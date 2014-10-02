package concurrent.keeporder.method1;

import java.util.concurrent.TimeUnit;

public class PrintTask implements Runnable {
	private String printStr;
	private int seq;

	public PrintTask(int seq, String printStr) {
		// TODO Auto-generated constructor stub
		this.seq = seq;
		this.printStr = printStr;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			Thread.yield();
			if (ClientTest.shared.intValue() % ClientTest.count == seq) {
				System.out.println(this.printStr);
				Thread.yield();
				ClientTest.shared.incrementAndGet();
			}
		}
	}

}

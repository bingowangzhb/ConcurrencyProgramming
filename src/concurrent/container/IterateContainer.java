package concurrent.container;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class IterateContainer implements Runnable {
	private Collection<Object> container;

	public IterateContainer(Collection<Object> container) {
		this.container = container;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			for (Object object : container) {
				System.out.println(object);
			}
		}
	}

}

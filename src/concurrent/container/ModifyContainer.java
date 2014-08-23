package concurrent.container;

import java.util.Collection;

public class ModifyContainer implements Runnable {
	private Collection<Object> container;

	public ModifyContainer(Collection<Object> container) {
		this.container = container;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			for (Object object : container) {
				container.remove(object);
				System.out.println("Removed " + object);
			}
		}
	}

}

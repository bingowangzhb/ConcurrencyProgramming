package concurrent.exchanger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
	private Exchanger<List<Product>> exchanger;

	public Consumer(Exchanger<List<Product>> exchanger) {
		this.exchanger = exchanger;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			List<Product> products = new CopyOnWriteArrayList<Product>();
			while (!Thread.interrupted()) {
				products = exchanger.exchange(products);
				if (!ClientTest.isEmpty) {
					System.out
							.println("Product pool is full, starts to consume...");
					for (Product product : products) {
						products.remove(product);
						System.out.println(product + " is consumed");
						TimeUnit.MILLISECONDS.sleep(500);
					}
					System.out.println("Product pool is empty now!");
					ClientTest.isEmpty = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

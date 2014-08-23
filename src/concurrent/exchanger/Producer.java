package concurrent.exchanger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
	private Exchanger<List<Product>> exchanger;
	private int size;

	public Producer(Exchanger<List<Product>> exchanger, int size) {
		this.exchanger = exchanger;
		this.size = size;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			List<Product> products = new CopyOnWriteArrayList<Product>();
			while (!Thread.interrupted()) {
				if (ClientTest.isEmpty) {
					System.out
							.println("Product pool is empty, starts to produce products...");
					for (int i = 0; i < size; i++) {
						Product product = new Product();
						System.out.println("Produced " + product);
						products.add(product);
						TimeUnit.MILLISECONDS.sleep(500);
					}
					System.out.println("Product pool is full now!");
					ClientTest.isEmpty = false;
					products = exchanger.exchange(products);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}

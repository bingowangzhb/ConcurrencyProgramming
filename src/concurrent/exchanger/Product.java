package concurrent.exchanger;

import java.util.UUID;

public class Product {
	private String sequence;

	public Product() {
		this.sequence = UUID.randomUUID().toString();
	}

	public String toString() {
		return "Product[" + this.sequence + "]";
	}
}

package concurrent.simulate.teller;

/**
 * Customer POJO
 * 
 * @author Charles Chen
 * @date 2014Äê9ÔÂ1ÈÕ
 */
public class Customer {
	private int id;
	private int duration;

	public Customer(int id, int duration) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String toString() {
		return "Customer#" + id + "(" + duration + ")";
	}
}

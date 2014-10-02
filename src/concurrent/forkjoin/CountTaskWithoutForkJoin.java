package concurrent.forkjoin;

import java.util.concurrent.Callable;

public class CountTaskWithoutForkJoin implements Callable<Integer> {
	private int id;
	private int start;
	private int end;

	public CountTaskWithoutForkJoin(int id, int start, int end) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.start = start;
		this.end = end;
	}

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		int sum = 0;
		for (int i = start; i <= end; i++) {
			sum += i;
		}
		return sum;
	}

}

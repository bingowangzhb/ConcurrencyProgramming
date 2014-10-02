package concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	private final static int THRESHOLD = 2;

	private int start;
	private int end;
	private int id;

	public CountTask(int start, int end, int id) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
		this.id = id;
	}

	@Override
	protected Integer compute() {
		// TODO Auto-generated method stub
		// System.out.println("CountTask#" + id + ": Start(" + start + "), End("
		// + end + ")");
		int sum = 0;
		if (end - start + 1 <= THRESHOLD) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		}

		int mid = (start + end) / 2;
		CountTask leftTask = new CountTask(start, mid,
				CountTaskClientTest.taskId++);
		CountTask rightTask = new CountTask(mid + 1, end,
				CountTaskClientTest.taskId++);

		leftTask.fork();
		rightTask.fork();
		return leftTask.join() + rightTask.join();
	}
}

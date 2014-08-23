package concurrent.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Resource objects shared pool
 * 
 * @author Charles Chen
 * 
 */
public class ResourcePool {
	// Resource list for storage
	private List<Resource> resources = new ArrayList<Resource>();
	private Semaphore semp;

	public ResourcePool(List<Resource> resources) {
		this.resources.addAll(resources);
		// Initialise the semaphore object with the size of resources list
		// Specific fair as true, the semaphore will guarantee first-in
		// first-out permits service
		semp = new Semaphore(resources.size(), true);
	}

	/**
	 * Get a resource from pool
	 * 
	 * @return
	 */
	public synchronized Resource getResource() {
		try {
			// Try acquire semaphore permit
			// If current semaphore value is 0, block the thread
			semp.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resources.remove(0);
	}

	/**
	 * Release a resource to pool
	 * 
	 * @param resource
	 */
	public synchronized void release(Resource resource) {
		resources.add(resource);
		// Release semaphore permit
		semp.release();
	}
}

/**
 * Resource POJO
 * 
 * @author Charles Chen
 * 
 */
class Resource {
	private String name;

	public Resource(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
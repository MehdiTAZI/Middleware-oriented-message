package fr.esiag.mezzodijava.mezzo.servercommons;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPool {

    private static int corePoolSize = 51;
    private int maxPoolSize;
    private static long keepAliveTime = 5000;
    // private static int maxQueue = 5;

    private ThreadPoolExecutor threadPool = null;
    final SynchronousQueue<Runnable> queue;

    private static Logger log = LoggerFactory.getLogger(ThreadPool.class);

    public ThreadPool(int maxPoolSize) {
	this.maxPoolSize = maxPoolSize;
	queue = new SynchronousQueue<Runnable>();
	threadPool = new ThreadPoolExecutor(corePoolSize,
		(corePoolSize > this.maxPoolSize) ? corePoolSize
			: this.maxPoolSize, keepAliveTime,
		TimeUnit.MILLISECONDS, queue);
    }

    public int getActiveCount() {
	return threadPool.getActiveCount();
    }

    public void runTask(Runnable task) {
	threadPool.execute(task);
	log.trace("Task count.." + queue.size());

    }

    public void shutDown() {
	threadPool.shutdown();
    }

    public void setMaxSize(int capacity) {
	if (capacity > threadPool.getCorePoolSize()) {
	    threadPool.setMaximumPoolSize(capacity);
	} else {
	    threadPool.setMaximumPoolSize(threadPool.getCorePoolSize());
	}

    }
}

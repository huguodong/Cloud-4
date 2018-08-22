package com.ssitcloud.common.netty.server.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;

/**
 * RPCFuture for async RPC call
 * Created by luxiaoxun on 2016-03-15.
 */
public class CloudSyncFuture implements Future<Object> {

    private Sync sync;
    private CloudSyncRequest request;
    private CloudSyncResponse response;
    private long startTime;
    
    private static ThreadPoolExecutor threadPoolExecutor;

    private long responseTimeThreshold = 5000;

    private List<AsyncCloudCallback> pendingCallbacks = new ArrayList<AsyncCloudCallback>();
    private ReentrantLock lock = new ReentrantLock();

    public CloudSyncFuture(CloudSyncRequest request) {
        this.sync = new Sync();
        this.request = request;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public boolean isDone() {
        return sync.isDone();
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        sync.acquire(-1);
        if (this.response != null) {
            return this.response.getResult();
        } else {
            return null;
        }
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        boolean success = sync.tryAcquireNanos(-1, unit.toNanos(timeout));
        if (success) {
            if (this.response != null) {
                return this.response.getResult();
            } else {
                return null;
            }
        } else {
            throw new RuntimeException("Timeout exception. Request id: " + this.request.getRequestId());
        }
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    public void done(CloudSyncResponse reponse) {
        this.response = reponse;
        sync.release(1);
        invokeCallbacks();
        // Threshold
        long responseTime = System.currentTimeMillis() - startTime;
        if (responseTime > this.responseTimeThreshold) {
            System.out.println("Service response time is too slow. Request id = " + reponse.getRequestId() + ". Response Time = " + responseTime + "ms");
        }
    }

    private void invokeCallbacks() {
        lock.lock();
        try {
            for (final AsyncCloudCallback callback : pendingCallbacks) {
                runCallback(callback);
            }
        } finally {
            lock.unlock();
        }
    }

    public CloudSyncFuture addCallback(AsyncCloudCallback callback) {
        lock.lock();
        try {
            if (isDone()) {
                runCallback(callback);
            } else {
                this.pendingCallbacks.add(callback);
            }
        } finally {
            lock.unlock();
        }
        return this;
    }
    private  void submit(Runnable task) {
		if (threadPoolExecutor == null) {
			synchronized (this) {
				if (threadPoolExecutor == null) {
					threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
							TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
									65536));
				}
			}
		}
		threadPoolExecutor.submit(task);
	}

    private void runCallback(final AsyncCloudCallback callback) {
        final CloudSyncResponse res = this.response;
        submit(new Runnable() {
            @Override
            public void run() {
                if ("0".equals(res.getStatus())) {
                    callback.success(res.getResult());
                } else {
                    callback.fail(new RuntimeException("Response error", new Throwable()));
                }
            }
        });
    }

    static class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 1L;

        //future status
        private final int done = 1;
        private final int pending = 0;

        protected boolean tryAcquire(int acquires) {
            return getState() == done ? true : false;
        }

        protected boolean tryRelease(int releases) {
            if (getState() == pending) {
                if (compareAndSetState(pending, done)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isDone() {
            getState();
            return getState() == done;
        }
    }
}

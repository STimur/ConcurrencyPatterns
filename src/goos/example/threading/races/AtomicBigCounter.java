package goos.example.threading.races;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is not properly synchronised, to demonstrate how the {@link MultithreadedStressTester} works.
 */
public class AtomicBigCounter {
    private AtomicInteger count = new AtomicInteger(0);

    public int count() {
        return count.get();
    }

    public void inc() {
        count.incrementAndGet();
    }
}

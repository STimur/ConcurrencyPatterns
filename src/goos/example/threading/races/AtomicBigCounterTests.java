package goos.example.threading.races;

import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AtomicBigCounterTests {
    AtomicBigCounter counter = new AtomicBigCounter();

    @Test
    public void
    isInitiallyZero() {
        assertThat(counter.count(), equalTo(0));
    }

    @Test
    public void
    canIncreaseCounter() {
        counter.inc();
        assertThat(counter.count(), equalTo(1));

        counter.inc();
        assertThat(counter.count(), equalTo(2));

        counter.inc();
        assertThat(counter.count(), equalTo(3));
    }

    @Test
    public void
    canIncrementCounterFromMultipleThreadsSimultaneously() throws InterruptedException {
        MultithreadedStressTester stressTester = new MultithreadedStressTester(25000);

        stressTester.stress(new Runnable() {
            public void run() {
                counter.inc();
            }
        });

        stressTester.shutdown();

        assertThat("final count", counter.count(), equalTo(stressTester.totalActionCount()));
    }
}
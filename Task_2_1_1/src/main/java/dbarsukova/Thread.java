package dbarsukova;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class Thread extends Sequence {
    private Deque<Integer> array;
    private final int threads;

    public Thread(int threads) {
        this.threads = threads;
    }

    @Override
    public boolean search(int[] arr) {
        if (arr == null) {
            throw new NullPointerException();
        }
        this.array = Arrays.stream(arr).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        boolean primeFound = false;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            List<Callable<Boolean>> tasks = new ArrayList<>();
            for (int i = 0; i < threads; i++) {
                tasks.add(() -> {
                    Integer a;
                    while ((a = this.array.poll()) != null) {
                        if (!isPrime(a)) {
                            return true;
                        }
                    }
                    return false;
                });
            }
            for (Future<Boolean> result : pool.invokeAll(tasks)) {
                if (result.get()) {
                    primeFound = true;
                    break;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            pool.shutdownNow();
        }
        return primeFound;
    }
}

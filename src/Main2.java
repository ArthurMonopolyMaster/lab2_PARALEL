import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Main2 {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void countElements(int[] arr, int start, int end, Result result) {
        int localCount = 0;
        int localMax = 0;

        for (int i = start; i < end; i++) {
            if (arr[i] > 10) {
                localCount++;
                if (arr[i] > localMax) {
                    localMax = arr[i];
                }
            }
        }

        lock.lock();
        try {
            result.count += localCount;
            if (localMax > result.max) {
                result.max = localMax;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int size = 10_000_000;
        int numThreads = 20;
        int[] arr = new int[size];
        Random rand = new Random(System.nanoTime());

        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(101);
        }

        Result result = new Result();
        Thread[] threads = new Thread[numThreads];
        int chunkSize = size / numThreads;

        long startTime = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == numThreads - 1) ? size : (i + 1) * chunkSize;
            threads[i] = new Thread(() -> countElements(arr, startIndex, endIndex, result));
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000;

        System.out.println("Number of elements greater than 10: " + result.count);
        System.out.println("The largest number greater than 10 (max 100): " + result.max);
        System.out.println("Time: " + duration + " ms");
    }
}

//class Result2 {
//    int count = 0;
//    int max = 0;
//}

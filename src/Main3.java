import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main3 {
    private static final AtomicInteger countAtomic = new AtomicInteger(0);
    private static final AtomicInteger maxAtomic = new AtomicInteger(0);

    public static void countElements(int[] arr, int start, int end) {
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

        countAtomic.addAndGet(localCount);
        maxAtomic.accumulateAndGet(localMax, Math::max);
    }

    public static void main(String[] args) {
        int size = 10_000_000;
        int numThreads = 20;
        int[] arr = new int[size];
        Random rand = new Random(System.nanoTime());

        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(101);
        }

        Thread[] threads = new Thread[numThreads];
        int chunkSize = size / numThreads;

        long startTime = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == numThreads - 1) ? size : (i + 1) * chunkSize;
            threads[i] = new Thread(() -> countElements(arr, startIndex, endIndex));
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

        System.out.println("Number of elements greater than 10: " + countAtomic.get());
        System.out.println("The largest number greater than 10 (max 100): " + maxAtomic.get());
        System.out.println("Time: " + duration + " ms");
    }
}

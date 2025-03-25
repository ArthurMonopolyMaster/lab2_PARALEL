import java.util.Random;

public class Main1_1 {
    public static void main(String[] args) {
        int size = 10_000_000;  // Розмір масиву
        int[] arr = new int[size];
        Random rand = new Random(System.nanoTime());

        // Заповнюємо масив випадковими числами від 0 до 100
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(101);
        }

        int count = 0;
        int max = 0;

        long start = System.nanoTime();  // Фіксуємо початок часу виконання

        // Підрахунок чисел > 10 і пошук максимального числа
        for (int i = 0; i < size; i++) {
            if (arr[i] > 10) {
                count++;
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
        }

        long end = System.nanoTime();  // Фіксуємо кінець часу виконання
        long duration = (end - start) / 1000;  // Конвертуємо в мікросекунди

        // Вивід результатів
        System.out.println("Number of elements greater than 10: " + count);
        System.out.println("The largest number greater than 10 (max 100): " + max);
        System.out.println("Time: " + duration + " ms");
    }
}


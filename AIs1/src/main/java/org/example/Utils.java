package org.example;
import java.util.Random;

public class Utils {
    public static int[][] shuffleMatrix(int shiftsCount) {
        int[][] matrix = new int[4][4];
        Random random = new Random();
        // Инициализация матрицы
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = j;
            }
        }
        // Выполняем указанное количество случайных сдвигов
        for (int s = 0; s < shiftsCount; s++) {
            boolean shiftRow = random.nextBoolean(); // true → строка, false → столбец

            if (shiftRow) {
                int row = random.nextInt(4);
                shiftRowRight(matrix, row);
            } else {
                int col = random.nextInt(4);
                shiftColumnDown(matrix, col);
            }
        }

        return matrix;
    }

    private static void shiftRowRight(int[][] matrix, int row) {
        int last = matrix[row][3];
        for (int j = 3; j > 0; j--) {
            matrix[row][j] = matrix[row][j - 1];
        }
        matrix[row][0] = last;
    }
    // Сдвиг столбца вниз
    private static void shiftColumnDown(int[][] matrix, int col) {
        int last = matrix[3][col];
        for (int i = 3; i > 0; i--) {
            matrix[i][col] = matrix[i - 1][col];
        }
        matrix[0][col] = last;
    }

    public static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    //Возвращает используемую память в мегабайтах
    public static double getUsedMemoryMB() {
        return getUsedMemory() / (1024.0 * 1024.0);
    }
    //Печатает информацию о памяти JVM.
    public static void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long used = total - free;
        long max = runtime.maxMemory();

        System.out.printf("""
                ==== Memory usage ====
                Used:  %.2f MB
                Free:  %.2f MB
                Total: %.2f MB
                Max:   %.2f MB
                =======================
                """,
                used / (1024.0 * 1024.0),
                free / (1024.0 * 1024.0),
                total / (1024.0 * 1024.0),
                max / (1024.0 * 1024.0)
        );
    }
}

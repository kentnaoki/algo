package astar;

import java.util.Arrays;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

class RandomPuzzle {
    private final int[][] basePuzzle;
    private final int size;

    public RandomPuzzle(int size) {
        this.basePuzzle = generatePuzzle(size);
        this.size = size;
    }

    private int[][] generatePuzzle(int size) {
        int[][] puz = new int[size][size];
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puz[i][j] = count;
                count++;
            }
        }
        int row = size - 1;
        int col = size - 1;
        puz[row][col] = 0;
        return puz;
    }

    public int[][] getBasePuzzle() {
        return basePuzzle;
    }

    public int[][] getRandomisedPuzzle() {
        int[][] puzzle = new int[size][size];
        for (int i = 0; i < size; i++) {
            puzzle[i] = Arrays.copyOf(basePuzzle[i], size);
        }

        int row = size - 1;
        int col = size - 1;
        Random rand = new Random();
        int iter = rand.nextInt(100);
        Map<Integer, int[]> dirMap = new HashMap<>();
        dirMap.put(0, new int[] { -1, 0 });
        dirMap.put(1, new int[] { 1, 0 });
        dirMap.put(2, new int[] { 0, -1 });
        dirMap.put(3, new int[] { 0, 1 });

        int i = 0;
        while (i < iter) {
            int move = rand.nextInt(4);
            int[] dir = dirMap.get(move);

            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                puzzle[row][col] = puzzle[newRow][newCol];
                puzzle[newRow][newCol] = 0;

                row = newRow;
                col = newCol;
                i++;
            }
        }

        return puzzle;
    }

}

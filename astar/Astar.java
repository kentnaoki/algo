package astar;

import java.util.Arrays;

public class Astar {
    public static void main(String[] args) {
        RandomPuzzle random = new RandomPuzzle(3);
        int[][] puz = random.getRandomisedPuzzle();
        System.out.println(puzToString(puz));
    }

    private static String puzToString(int[][] puzzle) {
        return String.join("\n", Arrays.stream(puzzle).map(Arrays::toString).toList());
    }
}

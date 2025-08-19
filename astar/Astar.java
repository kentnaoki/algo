package astar;

import java.util.Arrays;

public class Astar {
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        RandomPuzzle random = new RandomPuzzle(size);
        int[][] puz = random.getRandomisedPuzzle();
        System.out.println(puzToString(puz));
    }

    private static String puzToString(int[][] puzzle) {
        return String.join("\n", Arrays.stream(puzzle).map(Arrays::toString).toList());
    }
}

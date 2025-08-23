package astar;

import java.util.Arrays;

public class Astar {
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        RandomPuzzle random = new RandomPuzzle(size);
        int[][] goal = random.getBasePuzzle();
        int[][] puz = random.getRandomisedPuzzle();
        Solver solver = new Solver();
        int count = solver.solvePuzzle(puz, goal);
        System.out.println("----------------Total Move-----------------");
        System.out.println(count);
    }

    private static String puzToString(int[][] puzzle) {
        return String.join("\n", Arrays.stream(puzzle).map(Arrays::toString).toList());
    }
}

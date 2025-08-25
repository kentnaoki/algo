package astar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import astar.encoder.LongEncoder;
import astar.heuristic.Heuristic;
import astar.heuristic.HeuristicFactory;
import astar.heuristic.ManhattanHeuristic;
import astar.heuristic.NoHeuristic;

public class Astar {
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int sampleSize = Integer.parseInt(args[1]);
        String heuristicType = args[2];

        RandomPuzzle random = new RandomPuzzle(size);
        int[][] goal = random.getBasePuzzle();
        Heuristic heuristic = HeuristicFactory.getHeuristic(heuristicType, size);

        long totalNodes = 0;
        long totalTimeMs = 0;

        for (int i = 0; i < sampleSize; i++) {
            int[][] puz = random.getRandomisedPuzzle();
            Solver solver = new Solver(heuristic, new LongEncoder(size));
            long[] result = solver.solvePuzzle(puz, goal);

            totalNodes += result[0];
            totalTimeMs += result[1];
        }

        double totalTimeSeconds = totalTimeMs / 1000.0;
        double average = totalNodes / totalTimeSeconds;

        System.out.println("-------------------------------");
        System.out.println((int) average + " Nodes/second");

    }

    private static String puzToString(int[][] puzzle) {
        return String.join("\n", Arrays.stream(puzzle).map(Arrays::toString).toList());
    }
}

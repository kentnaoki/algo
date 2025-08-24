package astar.heuristic;

import astar.heuristic.HeuristicType;
import astar.heuristic.ManhattanHeuristic;
import astar.heuristic.MisplacedTileHeuristic;
import astar.heuristic.NoHeuristic;

public class HeuristicFactory {

    public static Heuristic getHeuristic(String type) {
        HeuristicType heuristicType = HeuristicType.fromString(type);

        switch (heuristicType) {
            case NO_HEURISTIC:
                return new NoHeuristic();
            case MISPLACED_TILE:
                return new MisplacedTileHeuristic();
            case MANHATTAN:
                return new ManhattanHeuristic();
            default:
                throw new RuntimeException("Invalid heuristic type.");
        }
    }
}

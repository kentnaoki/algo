package astar.heuristic;

public class NoHeuristic implements Heuristic {

    public NoHeuristic() {
    }

    @Override
    public int getHeuristic(int[][] puz) {
        return 0;
    }
}

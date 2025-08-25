package astar.heuristic;

public class NoHeuristic implements Heuristic {

    public NoHeuristic() {
    }

    @Override
    public int getHeuristic(int[] state) {
        return 0;
    }

    @Override
    public int updateHeuristic(int oldH, int val, int oldIdx, int newIdx) {
        return 0;
    }
}

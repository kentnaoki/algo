package astar.heuristic;

public interface Heuristic {
    public int getHeuristic(int[] state);

    public int updateHeuristic(int oldH, int val, int oldIdx, int newIdx);
}

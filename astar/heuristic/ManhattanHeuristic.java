package astar.heuristic;

public class ManhattanHeuristic implements Heuristic {

    private final int size;

    public ManhattanHeuristic(int size) {
        this.size = size;
    }

    @Override
    public int getHeuristic(int[] state) {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            int val = state[i];
            if (val != 0) {
                h += dist(i, val);
            }
        }
        return h;
    }

    @Override
    public int updateHeuristic(int oldH, int val, int oldIdx, int newIdx) {
        int oldDist = dist(oldIdx, val);
        int newDist = dist(newIdx, val);
        return oldH - oldDist + newDist;
    }

    private int dist(int idx, int val) {
        if (val == 0)
            return 0;
        int row = idx / size;
        int col = idx % size;
        int goalRow = (val - 1) / size;
        int goalCol = (val - 1) % size;
        return Math.abs(row - goalRow) + Math.abs(col - goalCol);
    }

}

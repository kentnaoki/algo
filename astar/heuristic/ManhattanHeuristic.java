package astar.heuristic;

public class ManhattanHeuristic implements Heuristic {

    public ManhattanHeuristic() {
    }

    @Override
    public int getHeuristic(int[][] puz) {
        int cost = 0;
        int puzSize = puz.length;
        for (int row = 0; row < puzSize; row++) {
            for (int col = 0; col < puzSize; col++) {
                cost += getDistToGoal(row, col, puz[row][col], puzSize);
            }
        }
        return cost;
    }

    private int getDistToGoal(int row, int col, int val, int puzSize) {
        if (val == 0) {
            return 0;
        }
        int rowGoal = (val - 1) / puzSize;
        int colGoal = (val - 1) % puzSize;

        return Math.abs(rowGoal - row) + Math.abs(colGoal - col);
    }
}

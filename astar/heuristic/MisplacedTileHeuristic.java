package astar.heuristic;

public class MisplacedTileHeuristic implements Heuristic {

    public MisplacedTileHeuristic() {
    }

    @Override
    public int getHeuristic(int[][] puz) {
        int cost = 0;
        int puzSize = puz.length;
        for (int row = 0; row < puzSize; row++) {
            for (int col = 0; col < puzSize; col++) {
                if (isTileMisplaced(row, col, puz[row][col], puzSize)) {
                    count++;
                }
            }
        }
        return cost;
    }

    private boolean isTileMisplaced(int row, int col, int val, int puzSize) {
        if (val == 0) {
            return row == puzSize - 1 && col == puzSize - 1;
        }
        int rowGoal = (val - 1) / puzSize;
        int colGoal = (val - 1) % puzSize;

        return row == rowGoal && col == colGoal;
    }
}

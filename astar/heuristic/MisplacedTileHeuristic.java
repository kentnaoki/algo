package astar.heuristic;

public class MisplacedTileHeuristic implements Heuristic {

    private final int size;

    public MisplacedTileHeuristic(int size) {
        this.size = size;
    }

    @Override
    public int getHeuristic(int[] puz) {
        int cost = 0;
        int puzSize = puz.length;
        for (int i = 0; i < puzSize; i++) {
            int val = puz[i];
            int row = i / size;
            int col = i % size;
            int rowGoal = (val - 1) / puzSize;
            int colGoal = (val - 1) % puzSize;

            if (isTileMisplaced(row, col, rowGoal, colGoal, val)) {
                cost++;
            }
        }
        return cost;

    }

    private boolean isTileMisplaced(int row, int col, int rowGoal, int colGoal, int val) {
        if (val == 0) {
            return row == size - 1 && col == size - 1;
        }

        return row == rowGoal && col == colGoal;
    }
}

package astar.heuristic;

public class MisplacedTileHeuristic implements Heuristic {

    private final int size;

    public MisplacedTileHeuristic(int size) {
        this.size = size;
    }

    @Override
    public int getHeuristic(int[] state) {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            int val = state[i];
            if (val == 0) {
                continue;
            }
            int row = idx / size;
            int col = idx % size;
            int rowGoal = (val - 1) / size;
            int colGoal = (val - 1) % size;

            if (row == rowGoal || col == colGoal) {
                h++;
            }
        }
        return h;
    }

    @Override
    public int updateHeuristic(int oldH, int val, int oldIdx, int newIdx) {
        if (val == 0)
            return oldH;

        int oldRow = oldIdx / size;
        int oldCol = oldIdx % size;
        int newRow = newIdx / size;
        int newCol = newIdx % size;

        int goalRow = (val - 1) / size;
        int goalCol = (val - 1) % size;

        boolean wasMisplaced = (oldRow != goalRow || oldCol != goalCol);
        boolean nowMisplaced = (newRow != goalRow || newCol != goalCol);

        if (wasMisplaced && !nowMisplaced) {
            return oldH - 1;
        } else if (!wasMisplaced && nowMisplaced) {
            return oldH + 1;
        } else {
            return oldH;
        }
    }
}

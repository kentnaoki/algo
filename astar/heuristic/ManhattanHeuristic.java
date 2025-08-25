package astar.heuristic;

public class ManhattanHeuristic implements Heuristic {

    private final int size;

    public ManhattanHeuristic(int size) {
        this.size = size;
    }

    @Override
    public int getHeuristic(int[] puz) {
        int cost = 0;

        for (int i = 0; i < puz.length; i++) {
            int val = puz[i];
            if (val != 0) {
                int row = i / size;
                int col = i % size;
                int rowGoal = (val - 1) / size;
                int colGoal = (val - 1) % size;
                cost += Math.abs(rowGoal - row) + Math.abs(colGoal - col);
            }
        }
        return cost;
    }

}

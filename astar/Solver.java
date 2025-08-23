package astar;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Solver {

    public Solver() {
    }

    public int solvePuzzle(int[][] puz, int[][] goal) {
        Map<Integer, int[]> dirMap = new HashMap<>();
        dirMap.put(0, new int[] { -1, 0 });
        dirMap.put(1, new int[] { 1, 0 });
        dirMap.put(2, new int[] { 0, -1 });
        dirMap.put(3, new int[] { 0, 1 });

        int count = 0;
        int cost = Integer.MAX_VALUE;
        int[] zeroCor = findZero(puz);
        int puzSize = puz.length;
        Set<String> visited = new HashSet<>();
        Node node = new Node(puz, zeroCor, calculateCost(puz), calculateCost(puz));
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(node);
        visited.add(Arrays.deepToString(node.puzzle()));

        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            System.out.println("------------------------");
            System.out.println(String.join("\n", Arrays.stream(curNode.puzzle()).map(Arrays::toString).toList()));
            count++;

            int zeroRow = curNode.zeroCor()[0];
            int zeroCol = curNode.zeroCor()[1];

            // if (visited.contains(Arrays.deepToString(curNode.puzzle()))) {
            // continue;
            // }

            for (Map.Entry<Integer, int[]> entry : dirMap.entrySet()) {
                int[] dir = entry.getValue();
                int index = entry.getKey();
                int newRow = zeroRow + dir[0];
                int newCol = zeroCol + dir[1];

                int[][] curPuz = getDeepCopy(curNode.puzzle());

                if (newRow >= 0 && newRow < puzSize && newCol >= 0 && newCol < puzSize) {
                    curPuz[zeroRow][zeroCol] = curPuz[newRow][newCol];
                    curPuz[newRow][newCol] = 0;
                }

                int curCost = calculateCost(curPuz);

                if (Arrays.deepToString(curPuz).equals(Arrays.deepToString(goal))) {
                    System.out.println("------------------------");
                    System.out.println(String.join("\n", Arrays.stream(curPuz).map(Arrays::toString).toList()));
                    return count;
                }

                if (!visited.contains(Arrays.deepToString(curPuz))) {
                    Node nextNode = new Node(getDeepCopy(curPuz), new int[] { newRow, newCol },
                            curNode.currentCost() + 1, curCost);
                    queue.offer(nextNode);
                    visited.add(Arrays.deepToString(nextNode.puzzle()));
                }
            }
        }

        return count;
    }

    private int calculateCost(int[][] puz) {
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

    private int[] findZero(int[][] puz) {
        for (int row = 0; row < puz.length; row++) {
            for (int col = 0; col < puz.length; col++) {
                if (puz[row][col] == 0) {
                    return new int[] { row, col };
                }
            }
        }
        return new int[] { 0, 0 };
    }

    private int[][] getDeepCopy(int[][] arr) {
        int[][] copy = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i].clone();
        }
        return copy;
    }
}

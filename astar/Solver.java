package astar;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Solver {
    private final Map<Integer, int[]> directionMap;
    private final Set<String> visited;
    private final Queue<Node> queue;

    public Solver() {
        this.directionMap = new HashMap<>();
        directionMap.put(0, new int[] { -1, 0 });
        directionMap.put(1, new int[] { 1, 0 });
        directionMap.put(2, new int[] { 0, -1 });
        directionMap.put(3, new int[] { 0, 1 });
        this.visited = new HashSet<>();
        this.queue = new ArrayDeque<>();
    }

    public int solvePuzzle(int[][] puz, int[][] goal) {
        String goalStr = Arrays.deepToString(goal);
        int count = 0;
        int[] zeroCor = findZero(puz);
        int puzSize = puz.length;
        Node node = new Node(puz, zeroCor, null);
        queue.offer(node);
        visited.add(Arrays.deepToString(node.puzzle()));

        while (!queue.isEmpty()) {
            Node curNode = queue.poll();

            int zeroRow = curNode.zeroCor()[0];
            int zeroCol = curNode.zeroCor()[1];

            for (Map.Entry<Integer, int[]> entry : directionMap.entrySet()) {
                int[] dir = entry.getValue();
                int index = entry.getKey();
                int newRow = zeroRow + dir[0];
                int newCol = zeroCol + dir[1];

                int[][] curPuz = getDeepCopy(curNode.puzzle());

                if (newRow >= 0 && newRow < puzSize && newCol >= 0 && newCol < puzSize) {
                    curPuz[zeroRow][zeroCol] = curPuz[newRow][newCol];
                    curPuz[newRow][newCol] = 0;
                }

                String curPuzStr = Arrays.deepToString(curPuz);

                if (curPuzStr.equals(goalStr)) {
                    System.out.println("-----------GOAL-------------");
                    System.out.println(String.join("\n", Arrays.stream(curPuz).map(Arrays::toString).toList()));
                    return getPathLength(curNode);
                }

                if (!visited.contains(curPuzStr)) {
                    Node nextNode = new Node(getDeepCopy(curPuz), new int[] { newRow, newCol }, curNode);
                    queue.offer(nextNode);
                    visited.add(curPuzStr);
                }
            }

        }

        return -1;
    }

    private int getPathLength(Node node) {
        int count = 0;
        while (node.parent() != null) {
            System.out.println("------------------Move (Reverse order)-------------------------");
            System.out.println(String.join("\n", Arrays.stream(node.puzzle()).map(Arrays::toString).toList()));
            node = node.parent();
            count++;
        }

        return count;
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

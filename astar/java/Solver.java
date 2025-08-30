package astar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import astar.encoder.Encoder;
import astar.heuristic.Heuristic;

class Solver {
    private final List<int[]> direction;
    private final PriorityQueue<Node> openList;
    private final Map<Long, Integer> closedList;
    private final Heuristic heuristic;
    private final Encoder encoder;

    public Solver(Heuristic heuristic, Encoder encoder) {
        this.direction = new ArrayList<>();
        direction.add(new int[] { -1, 0 });
        direction.add(new int[] { 1, 0 });
        direction.add(new int[] { 0, -1 });
        direction.add(new int[] { 0, 1 });
        this.openList = new PriorityQueue<>((a, b) -> a.score() - b.score());
        this.closedList = new HashMap<>();
        this.heuristic = heuristic;
        this.encoder = encoder;
    }

    public long[] solvePuzzle(int[][] puz, int[][] goal) {
        long start = System.currentTimeMillis();
        int[] goal1d = arrayTo1d(goal);
        Long encodedGoal = encoder.encode(goal1d);
        int[] zeroCor = findZero(puz);
        int puzSize = puz.length;
        int[] puz1d = arrayTo1d(puz);
        Node node = new Node(puz1d, zeroCor, null, 0, 0, heuristic.getHeuristic(puz1d));
        openList.offer(node);
        int expanded = 0;

        while (!openList.isEmpty()) {
            expanded++;
            Node curNode = openList.poll();
            Long key = encoder.encode(curNode.puzzle());

            if (closedList.containsKey(key) && curNode.startToN() > closedList.get(key)) {
                continue;
            }

            closedList.put(key, curNode.startToN());

            int zeroRow = curNode.zeroCor()[0];
            int zeroCol = curNode.zeroCor()[1];

            for (int[] dir : direction) {
                int newRow = zeroRow + dir[0];
                int newCol = zeroCol + dir[1];

                int[] curPuz = curNode.puzzle();

                if (newRow < 0 || newRow >= puzSize || newCol < 0 || newCol >= puzSize) {
                    continue;
                }

                int zeroIdx = zeroRow * puzSize + zeroCol;
                int newIdx = newRow * puzSize + newCol;
                int val = curPuz[newIdx];
                int tmp = curPuz[zeroIdx];
                curPuz[zeroIdx] = curPuz[newIdx];
                curPuz[newIdx] = tmp;

                Long encodedCurPuz = encoder.encode(curPuz);

                if (expanded == 1000000 || encodedCurPuz.equals(encodedGoal)) {
                    long end = System.currentTimeMillis();
                    long timeElapsed = end - start;
                    return new long[] { expanded, timeElapsed };
                }

                int startToN = curNode.startToN() + 1;
                int heuristicCost = heuristic.updateHeuristic(curNode.heuristic(), val, newIdx, zeroIdx);
                int score = startToN + heuristicCost;

                Node nextNode = new Node(getDeepCopy(curPuz), new int[] { newRow, newCol }, curNode, score, startToN,
                        heuristicCost);

                if (!closedList.containsKey(encodedCurPuz) || startToN < closedList.get(encodedCurPuz)) {
                    closedList.put(encodedCurPuz, startToN);
                    openList.offer(nextNode);
                }

                tmp = curPuz[zeroIdx];
                curPuz[zeroIdx] = curPuz[newIdx];
                curPuz[newIdx] = tmp;
            }

        }

        return new long[] { -1, -1 };
    }

    private int[] arrayTo1d(int[][] puz) {
        int newSize = puz.length * puz.length;
        int[] copy = new int[newSize];

        for (int row = 0; row < puz.length; row++) {
            for (int col = 0; col < puz.length; col++) {
                int index = row * puz.length + col;
                copy[index] = puz[row][col];
            }
        }
        return copy;
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

    private int[] getDeepCopy(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }
}

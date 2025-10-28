package backtrack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import sudokuboards.SudokuBoards;

public class BackTrack {
    public static void main(String[] args) {
        System.out.println("hello");
        int[][] board = SudokuBoards.board1;
    }

    private Map<String, Integer> backtrackSearch(Csp csp) {
        return backtrack(csp, new HashMap<>());
    }

    private Map<String, Integer> backtrack(Csp csp, Map<String, Integer> assignment) {
        if (assigment is complete) {
            return assignment;
        }

        var unassignedVar = selectUnassignedVar(csp, assignment).orElseThrow(() -> new RuntimeException("empty unassignedVar"));

        for (int value : orderDomainValue(csp, unassignedVar, assignment)) {
            if (csp.isConsistent(unassignedVar, value, assignment)) {
                assignment.put(unassignedVar, value);
                var inferences = inference(csp, unassignedVar, assignment);
                if (inferences.isPresent()) {
                    assignment.putAll(inferences.get());
                    var result = backtrack(csp, assignment);
                    if (result != null) {
                        return result;
                    }
                    for (String key : inferences.get().keySet()) {
                        assignment.remove(key);
                    }

                }
                assignment.remove(unassignedVar);
            }
        }
        return null;

    }

    private Optional<String> selectUnassignedVar(Csp csp, Map<String, Integer> assignment) {
        for (String var : csp.variables) {
            if (!assignment.containsKey(var)) {
                return Optional.of(var);
            }
        }
        return Optional.empty();
    }

    private List<Integer> orderDomainValue(Csp csp, String var, Map<String, Integer> assignment) {
        return csp.domains.get(var);
    }

    private Optional<Map<String, Integer>> inference(Csp csp, String unassignedVar, Map<String, Integer> assignment) {
        return Optional.empty();
    }

    private boolean isValid(int[][] board, int row, int col) {
        Set<Integer> rowValues = new HashSet<>();
        Set<Integer> colValues = new HashSet<>();
        Set<Integer> boxValues = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            rowValues.add(board[row][i]);
            colValues.add(board[i][col]);
            int blockRow = row / 3;
            int blockCol = col / 3;
            int blockIndex = blockRow * 3 + blockCol;

        }
    }
}

private class Csp {
    List<String> variables;
    Map<String, List<Integer>> domains;
    Map<String, List<Constraint>> constraints;

    public boolean isConsistent(String variable, int value, Map<String, Integer> assignment) {
        for (Constraint c : constraints.get(variable)) {
            if (!c.isSatisfied(assignment, variable, value)) {
                return false;
            }
        }
        return true;
    }
}

interface Constraint {
    public boolean isSatisfied(Map<String, Integer> assignment, String variable, int value);
}

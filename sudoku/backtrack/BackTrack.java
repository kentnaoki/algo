package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println(print(board));
        var result = backtrackSearch(createSudokuCsp(board));
        System.out.println(result);
    }

    private static String print(int[][] board) {
        return String.join("\n", Arrays.stream(board).map(Arrays::toString).toList());
    }

    private static Map<String, Integer> getInitialAssignment(int[][] board) {
        Map<String, Integer> assignment = new HashMap<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String var = String.valueOf((char) ('A' + r)) + (c + 1);
                int value = board[r][c];
                if (value != 0) {
                    assignment.put(var, value);
                }
            }
        }
        return assignment;
    }

    private static Map<String, Integer> backtrackSearch(Csp csp) {
        Map<String, Integer> initialAssignment = new HashMap<>();
        for (String var : csp.variables) {
            List<Integer> domain = csp.domains.get(var);
            if (domain.size() == 1) {
                initialAssignment.put(var, domain.get(0));
            }
        }
        return backtrack(csp, initialAssignment);
    }

    private static Map<String, Integer> backtrack(Csp csp, Map<String, Integer> assignment) {
        if (assignment.size() == csp.variables.size()) {
            return assignment;
        }

        var unassignedVar = selectUnassignedVar(csp, assignment)
                .orElseThrow(() -> new RuntimeException("empty unassignedVar"));

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

    private static Optional<String> selectUnassignedVar(Csp csp, Map<String, Integer> assignment) {
        for (String var : csp.variables) {
            if (!assignment.containsKey(var)) {
                return Optional.of(var);
            }
        }
        return Optional.empty();
    }

    private static List<Integer> orderDomainValue(Csp csp, String var, Map<String, Integer> assignment) {
        return csp.domains.get(var);
    }

    private static Optional<Map<String, Integer>> inference(Csp csp, String unassignedVar,
            Map<String, Integer> assignment) {
        return Optional.of(new HashMap<>());
    }

    private static Csp createSudokuCsp(int[][] board) {
        List<String> variables = new ArrayList<>();
        Map<String, List<Integer>> domains = new HashMap<>();
        Map<String, List<Constraint>> constraints = new HashMap<>();

        for (char row = 'A'; row <= 'I'; row++) {
            for (int col = 1; col <= 9; col++) {
                String var = String.valueOf(row) + col;
                variables.add(var);
                constraints.put(var, new ArrayList<>());
            }
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String var = String.valueOf((char) ('A' + r)) + (c + 1);
                int value = board[r][c];
                if (value != 0) {
                    domains.put(var, List.of(value));
                } else {
                    domains.put(var, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                }
            }
        }

        for (String var1 : variables) {
            for (String var2 : variables) {
                if (!var1.equals(var2) && isRelated(var1, var2)) {
                    constraints.get(var1).add(new SudokuConstraint(var1, var2));
                }
            }
        }
        return new Csp(variables, domains, constraints);
    }

    private static boolean isRelated(String var1, String var2) {
        int row1 = var1.charAt(0) - 'A';
        int row2 = var2.charAt(0) - 'A';
        int col1 = Character.getNumericValue(var1.charAt(1)) - 1;
        int col2 = Character.getNumericValue(var2.charAt(1)) - 1;
        boolean sameRow = row1 == row2;
        boolean sameCol = col1 == col2;
        boolean sameBox = (row1 / 3 == row2 / 3) && (col1 / 3 == col2 / 3);
        return sameRow || sameCol || sameBox;
    }
}

class Csp {
    List<String> variables;
    Map<String, List<Integer>> domains;
    Map<String, List<Constraint>> constraints;

    public Csp(List<String> variables, Map<String, List<Integer>> domains, Map<String, List<Constraint>> constraints) {
        this.variables = variables;
        this.domains = domains;
        this.constraints = constraints;
    }

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

class SudokuConstraint implements Constraint {

    private final String var1;
    private final String var2;

    public SudokuConstraint(String var1, String var2) {
        this.var1 = var1;
        this.var2 = var2;
    }

    @Override
    public boolean isSatisfied(Map<String, Integer> assignment, String variable, int value) {
        if (!variable.equals(var1) && !variable.equals(var2))
            return true;

        Integer value1 = assignment.get(var1);
        Integer value2 = assignment.get(var2);

        if (value1 != null && value2 != null && value1.equals(value2))
            return false;

        if (variable.equals(var1) && value2 != null && value2 == value)
            return false;
        if (variable.equals(var2) && value1 != null && value1 == value)
            return false;

        return true;
    }
}

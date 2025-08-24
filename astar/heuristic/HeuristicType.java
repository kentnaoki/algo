package astar.heuristic;

public enum HeuristicType {
    NO_HEURISTIC("NoHeuristic"),
    MISPLACED_TILE("MisplacedTile"),
    MANHATTAN("Manhattan");

    private final String name;

    private HeuristicType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HeuristicType fromString(String text) {
        for (HeuristicType h : HeuristicType.values()) {
            if (h.name.equalsIgnoreCase(text)) {
                return h;
            }
        }
        throw new IllegalArgumentException("No heuristic type " + text);
    }

}

package astar.encoder;

public class LongEncoder implements Encoder {

    private final int size;

    public LongEncoder(int size) {
        if (size > 4) {
            throw new RuntimeException("The puzzle size needs to be smaller than or equal to 4 x 4");
        }
        this.size = size;
    }

    @Override
    public long encode(int[][] puzzle) {
        if (puzzle.length != size) {
            throw new RuntimeException("Invalid puzzle size");
        }
        long code = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                code <<= 4;
                code |= puzzle[i][j] & 0xF;
            }
        }

        return code;
    }

    @Override
    public int[][] decode(long code) {
        int[][] board = new int[size][size];
        for (int i = size - 1; i >= 0; i--) {
            for (int j = size - 1; j >= 0; j--) {
                board[i][j] = (int) (code & 0xF);
                code >>= 4;
            }
        }
        return board;
    }
}

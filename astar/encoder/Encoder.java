package astar.encoder;

public interface Encoder {

    public long encode(int[] puzzle);

    public int[] decode(long encodedPuzzle);
}

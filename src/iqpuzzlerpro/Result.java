package iqpuzzlerpro;

public class Result {
    public boolean isSolved;
    public Board2D board;
    public double time;
    public long iterations;

    public Result() {
        isSolved = false;
        board = null;
        time = 0;
        iterations = 0;
    }

    public Result(boolean isSolved, Board2D board, double time, long iterations) {
        this.isSolved = isSolved;
        this.board = board;
        this.time = time;
        this.iterations = iterations;
    }
}

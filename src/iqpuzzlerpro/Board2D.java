package iqpuzzlerpro;
import java.util.*;

public class Board2D {
    private final ArrayList<Piece2D> pieces;
    private final boolean[][] board;
    private final int width;
    private final int height;

    public Board2D(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new boolean[width][height];
        this.pieces = new ArrayList<>();
    }

    private boolean[][] getAvailableSpaces() {
        boolean[][] boardCopy = board.clone();

        for (Piece2D piece : pieces) {
            for (Vec2I v : piece.getShape()) {
                boardCopy[v.x][v.y] = true;
            }
        }
        return boardCopy;
    }

    public boolean piecePlacable(Piece2D piece) {
        boolean[][] available = getAvailableSpaces();
        for (Vec2I v : piece.getShape()) {
            if (v.x < 0 || v.x >= width || v.y < 0 || v.y >= height) {
                return false;
            }
            if (!available[v.x][v.y]) {
                return false;
            }
        }
        return true;
    }

    public boolean placePiece(Piece2D piece) {
        boolean placable = piecePlacable(piece);
        if (placable) {
            pieces.add(piece);
        }
        return placable;
    }

    // public void print(ArrayList<Pair<int, char>>) {

    // }
}

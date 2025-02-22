package iqpuzzlerpro;
import java.util.*;

public class ProgramInput {
    public int rows;
    public int cols;
    public int pieceCount;
    public Mode mode;
    public ArrayList<Piece2D> pieces;
    public Board2D board;

    public void print() {
        System.out.println("rows: " + rows);
        System.out.println("cols: " + cols);
        System.out.println("Piece Count: " + pieceCount);
        System.out.println("Mode: " + mode);
        try {
            for (Piece2D piece : pieces) {
                piece.printShape();
            }
        } catch (Exception e) {
            System.err.println("Error printing pieces: " + e);
        }
        try {
            board.printEmptyBoard(Ansi256.coloredText("█", 0, 0), Ansi256.coloredText("█", 15, 15));
        } catch (Exception e) {
            System.err.println("Error printing board: " + e);
        }
    }
}


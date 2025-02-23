package iqpuzzlerpro;
import java.util.*;

public class Board2D {
    private final ArrayList<Piece2D> pieces;
    private final boolean[][] board;
    private final int rows;
    private final int cols;
    private boolean[][] availablePlaces;

    public Board2D(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = true;
            }
        }
        this.pieces = new ArrayList<>();
        this.availablePlaces = board.clone();
    }

    public Board2D(int rows, int cols, boolean[][] board) {
        this.rows = rows;
        this.cols = cols;
        this.board = board;
        this.pieces = new ArrayList<>();
        this.availablePlaces = board.clone();
    }

    public Board2D copy() {
        boolean[][] newBoardArray = new boolean[board.length][];
        for (int i = 0; i < board.length; i++) {
            newBoardArray[i] = board[i].clone();
        }

        boolean[][] newAvailablePlacesArray = new boolean[availablePlaces.length][];
        for (int i = 0; i < availablePlaces.length; i++) {
            newAvailablePlacesArray[i] = board[i].clone();
        }

        Board2D newBoard = new Board2D(rows, cols, newBoardArray);
        newBoard.availablePlaces = newAvailablePlacesArray;

        for (Piece2D piece : pieces) {
            if (!newBoard.placePiece(piece)) {
                System.err.println("Error copying piece");
        }
    }
    return newBoard;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void clearPieces() {
        pieces.clear();
        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, availablePlaces[i], 0, cols);
        }
    }

    public boolean[][] getEmptyBoard() {
        return board.clone();
    }

    private boolean segmentIsPlacable(Vec2I segment) {
        return segment.x >= 0 && segment.x < rows && segment.y >= 0 && segment.y < cols && availablePlaces[segment.x][segment.y];
    }


    public void printPieces() {
        for (Piece2D p : pieces) {
            p.printShape();
        }
    }


    public boolean piecePlacable(Piece2D piece) {
        for (Vec2I v : piece.getShape()) {
            if (!segmentIsPlacable(v)) {
                return false;
            }
        }
        return true;
    }

    public boolean placePiece(Piece2D piece) {
        boolean placable = piecePlacable(piece);
        if (placable) {
            for (Vec2I v : piece.getShape()) {
                availablePlaces[v.x][v.y] = false;
            }
            pieces.add(piece);
        }
        return placable;
    }

    public void popLastPiece() {
        Piece2D lastPiece = pieces.get(pieces.size()-1);
        for (Vec2I v : lastPiece.getShape()) {
            availablePlaces[v.x][v.y] = true;
        }
        this.pieces.removeLast();
    }

    public void printEmptyBoard(String empty, String wall) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j]) {
                    System.out.print(empty);
                } else {
                    System.out.print(wall);
                }
            }
            System.out.println();
        }
    }

    public int[][] getBoardMatrix() {
        int[][] boardMatrix = new int[rows][cols]; // -1 for wall, 0 for empty, id for piece
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardMatrix[i][j] = board[i][j] ? 0 : -1;
            }
        }
        for (int i = pieces.size()-1; i >= 0; i--) {
            for (Vec2I v : pieces.get(i).getShape()) {
                boardMatrix[v.x][v.y] = pieces.get(i).getId();
            }
        }
        return boardMatrix;
    }

    public void printBoard(boolean toFile, String path, Map<Integer, Integer> pieceColors, String empty, String wall, int background, boolean printId) {
        int[][] boardMatrix = getBoardMatrix();
        Map<Integer, Piece2D> pieceMap = new HashMap<>();
        for (Piece2D piece : pieces) {
            pieceMap.put(piece.getId(), piece);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (boardMatrix[i][j]) {
                    case -1 -> System.out.print(wall);
                    case 0 -> System.out.print(empty);
                    default -> System.out.print(Ansi256.coloredText(pieceMap.get(boardMatrix[i][j]).getSymbol(), pieceColors.get(boardMatrix[i][j]), background));
                }
            }
            System.out.println();
        }

        if (printId) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    // System.out.print(Ansi256.coloredText(String.valueOf(boardMatrix[i][j]), pieceColors.get(boardMatrix[i][j]), background));
                    System.out.print(boardMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public void printBoard(Map<Integer, Integer> pieceColors, String empty, String wall, int background) {
        printBoard(false, "", pieceColors, empty, wall, background, false);
    }

    public void printBoardStandard(Map<Integer, Integer> pieceColors) {
        printBoard(pieceColors, Ansi256.coloredText("█", 0, 0), Ansi256.coloredText("█", 15, 15), 0);
    }


    public boolean isSolved() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (availablePlaces[i][j] == true) return false;
            }
        }
        return true;

    }

    public Piece2D[] getPieces() {
        return pieces.toArray(Piece2D[]::new);
    }

    public void printAvailablePlaces() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(availablePlaces[i][j] ? "1" : "0");
            }
            System.out.println();
        }
    }
}

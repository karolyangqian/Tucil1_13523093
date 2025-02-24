package iqpuzzlerpro;
import java.io.IOException;
import java.util.*;

public class Solver {

    private static long iterations = 0;
    private static long startTime = 0;
    private static Map<Integer, Integer> colorMap;
    private static Board2D board;
    private static ArrayList<Piece2D> givenPieces;
    private static int rows;
    private static int cols;
    private static int numOfPieces;
    private static final int NUM_OF_ROT_STATES = 8;
    private static boolean colorMapSet = false;
    private static Result result;

    public static void setColorMap(String filename) throws IOException {
        try {
            colorMap = FileHandle.readColorMap(filename);
            colorMapSet = true;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Result getResult() {
        return new Result(result.isSolved, result.board.copy(), result.time, result.iterations);
    }

    public static boolean solve(ProgramInput programInput, Board2D boardToSolve, boolean printResult, boolean printProgress) {
        givenPieces = programInput.pieces;
        numOfPieces = givenPieces.size();
        board = boardToSolve.copy();
        rows = programInput.rows;
        cols = programInput.cols;
        iterations = 0;
        result = new Result();

        if (!colorMapSet) {
            colorMap = new HashMap<>();
            for (Piece2D piece : givenPieces) {
                colorMap.put(piece.getId(), 15);
            }
        }

        startTime = System.nanoTime();
        boolean solved = attempt(0, 0, 0, 0, printProgress);
        long endTime = System.nanoTime();

        double timeTaken = (double) (endTime - startTime) / 1e6;

        if (solved) {
            result = new Result(true, board.copy(), timeTaken, iterations);
        } else {
            result = new Result(false, board.copy(), timeTaken, iterations);
        }

        if (printResult) {
            if (solved) {
                board.printBoardStandard(colorMap);
                System.out.printf("\nSolved in %.3f ms with %d iterations\n", timeTaken, iterations);
            } else {
                // board.clearPieces();
                System.out.printf("\nCannot solve board with given pieces. Checked %d iterations in %.3f ms\n", iterations, timeTaken);
            }
        }
        return solved;
    }

    public static boolean solve(ProgramInput programInput, Board2D boardToSolve) {
        return solve(programInput, boardToSolve, false, false);
    }

    public static boolean solve(ProgramInput programInput, Board2D boardToSolve, boolean printResult) {
        return solve(programInput, boardToSolve, printResult, false);
    }
    
    private static void printProgress(long startTime) {
        long elapsedTime = System.nanoTime() - startTime;
        board.printBoardStandard(colorMap);
        System.out.printf("Iterations: %d | Time: %.3f ms\n", iterations, elapsedTime / 1e9);
    }
    
    private static boolean attempt(int pieceIndex, int r, int c, int rot, boolean printProgress) {
        if (pieceIndex == numOfPieces) {
            return board.isSolved();
        }
        
        if (printProgress) {
            if (iterations % 1000 == 0) {
                printProgress(startTime);
                System.err.printf("Piece %d %s on (%d, %d) with rot %d\n", pieceIndex, givenPieces.get(pieceIndex).getSymbol(), r, c, rot);
                System.err.println("Hold Enter to continue...");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();
            }
        }

        iterations++;

        if (rot == NUM_OF_ROT_STATES){
            return attempt(pieceIndex, r, c+1, 0, printProgress);
        }
        if (c == cols) {
            return attempt(pieceIndex, r+1, 0, rot, printProgress);
        } 
        if (r == rows) {
            return false;
        } 

        Piece2D currentPiece = givenPieces.get(pieceIndex).copy();

        currentPiece.setState(r, c, rot);

        if (board.placePiece(currentPiece)) {
            // System.err.printf("Piece %d placed\n", pieceIndex);
            if (attempt(pieceIndex+1, 0, 0, 0, printProgress)) return board.isSolved();
            else board.popLastPiece();
        }

        return attempt(pieceIndex, r, c, rot+1, printProgress);
    }
}

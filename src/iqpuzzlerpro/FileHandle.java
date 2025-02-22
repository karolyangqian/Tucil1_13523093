package iqpuzzlerpro;
import java.io.*;
import java.util.*;

public class FileHandle {
    public static Map<Integer, Integer> readColorMap(String filename) {
        Map<Integer, Integer> colorMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                colorMap.put(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
            }
        } catch (Exception e) {
        }
        return colorMap;
    }

    public static void read3NumConfig(ProgramInput programInput, BufferedReader br) throws IOException {
        try {
            String line = br.readLine();
            String[] split = line.split(" ");
            int w = Integer.parseInt(split[0]);
            int h = Integer.parseInt(split[1]);
            int p = Integer.parseInt(split[2]);
            if (w <= 0 || h <= 0 || p <= 0) throw new IOException("Board rows, columns, and pieces count must be positive numbers");
            programInput.rows = w;
            programInput.cols = h;
            programInput.pieceCount = p;
        } catch (Exception e) {
            throw new IOException("Invalid input for board rows, columns, and pieces count");
        }
    }

    public static void readMode(ProgramInput programInput, BufferedReader br) throws IOException {
        try {
            String line = br.readLine();
            programInput.mode = Mode.valueOf(line);
        } catch (Exception e) {
            throw new IOException("Invalid input for mode");
        }
    }

    public static void readBoard(ProgramInput programInput, BufferedReader br) throws IOException {
        try {
            boolean[][] boardData = new boolean[programInput.rows][programInput.cols];
            for (int i = 0; i < programInput.rows; i++) {
                String line = br.readLine();
                for (int j = 0; j < programInput.cols; j++) {
                    switch (line.charAt(j)) {
                        case '.':
                            boardData[i][j] = false;
                            break;
                        case 'X':
                            boardData[i][j] = true;
                            break;
                        default:
                            throw new IOException("Invalid input for board");
                    }
                }
            }
            Board2D board = new Board2D(programInput.rows, programInput.cols, boardData);
            programInput.board = board;
        } catch (Exception e) {
            throw new IOException("Invalid input for board");
        }
    }

    // Line valid tidak memiliki lebih dari satu jenis karakter selain space
    private static boolean isValidPieceLine(String line) {
        if (line == null) return false; 

        Set<Character> charSet = new HashSet<>();
        for (char c : line.toCharArray()) {
            if (!Character.isWhitespace(c)) charSet.add(c);
            if (charSet.size() > 1 || (Character.isAlphabetic(c) && !Character.isUpperCase(c)) || (!Character.isAlphabetic(c) && !Character.isWhitespace(c))) return false; 
        }
        return true;
    }

    private static Character getPieceLineSymbol(String line) {
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c != ' ') return c;
        }
        return null;
    }

    public static ArrayList<String[]> readPieceToList(BufferedReader br) throws IOException {
        ArrayList<String> rawPiecesList = new ArrayList<>();
        String line;
        ArrayList<String[]> pieces = new ArrayList<>();
        
        // Baca sampai akhir file
        while ((line = br.readLine()) != null) {
            if (!line.isBlank()) rawPiecesList.add(line);
        }

        // Cek apakah valid
        int n = rawPiecesList.size();
        for (int i = 0; i < n; i++) {
            if (!isValidPieceLine(rawPiecesList.get(i))) {
                throw new IOException("Invalid input for pieces");
            }
        }

        // 
        char prev = getPieceLineSymbol(rawPiecesList.get(0));
        ArrayList<String> aPiece = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char current = getPieceLineSymbol(rawPiecesList.get(i));
            if (current != prev) {
                pieces.add(aPiece.toArray(String[]::new));
                aPiece.clear();
            }
            aPiece.add(rawPiecesList.get(i));
            prev = current;
        }

        if (!aPiece.isEmpty()) pieces.add(aPiece.toArray(String[]::new));

        return pieces;
    }

    public static void readPieces(ProgramInput programInput, BufferedReader br) throws IOException {
        ArrayList<String[]> piecesList = readPieceToList(br);
        if (piecesList.size() != programInput.pieceCount) {
            throw new IOException("Expected " + programInput.pieceCount + " pieces, but received " + piecesList.size());
        }
        try {
            programInput.pieces = new ArrayList<>();
            for (int i = 0; i < programInput.pieceCount; i++) {
                String[] piece = piecesList.get(i);
                String newSymbol = getPieceLineSymbol(piece[0]).toString();
                programInput.pieces.add(Piece2D.stringArrayToPiece2D(i+1, newSymbol, piece));
            }
        } catch (Exception e) {
            throw new IOException("Invalid input for pieces");
        }
    }

    public static ProgramInput readProgramInput(String path) throws IOException {
        ProgramInput programInput = new ProgramInput();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            FileHandle.read3NumConfig(programInput, br);
            FileHandle.readMode(programInput, br);
            if (programInput.mode == Mode.DEFAULT) {
                Board2D board = new Board2D(programInput.rows, programInput.cols);
                programInput.board = board;
            } else if (programInput.mode == Mode.CUSTOM) {
                FileHandle.readBoard(programInput, br);
            } else {
                throw new IOException("Invalid mode");
            }
            FileHandle.readPieces(programInput, br);
        } catch (Exception e) {
            throw e;
        }
        return programInput;
    }

    public static void saveResult(Result result, String path) {
        try (PrintWriter writer = new PrintWriter(path)) {

            if (result.isSolved) {
                writer.println("Solved in " + result.time + " ms with " + result.iterations + " iterations");
            } else {
                writer.println("Cannot solve board with given pieces. Checked " + result.iterations + " iterations in " + result.time + " ms");
            }

            Piece2D[] pieces = result.board.getPieces();

            String[][] charBoard = new String[result.board.getRows()][result.board.getCols()];
            for (Piece2D piece : pieces) {
                for (Vec2I v : piece.getShape()) {
                    charBoard[v.x][v.y] = piece.getSymbol();
                }
            }

            writer.println(result.board.getRows() + " " + result.board.getCols());
            for (int i = 0; i < result.board.getRows(); i++) {
                for (int j = 0; j < result.board.getCols(); j++) {
                    if (charBoard[i][j] == null) {
                        writer.print(".");
                    } else {
                        writer.print(charBoard[i][j]);
                    }
                }
                writer.println();
            }

            int[][] boardIds = result.board.getBoardMatrix();
            for (int i = 0; i < result.board.getRows(); i++) {
                for (int j = 0; j < result.board.getCols(); j++) {
                    writer.print(boardIds[i][j] + " ");
                }
                writer.println();
            }

            writer.println(pieces.length);
            for (Piece2D piece : pieces) {
                writer.println(Integer.toString(piece.getId()) + piece.getSymbol());
                writer.println(piece.getPosition().x + " " + piece.getPosition().y + " " + piece.getState());
            }   

        } catch (Exception e) {
        }
    }

    public static void print(boolean toFile, String path, String text) {
        if (!toFile) {
            System.out.print(text);
        } else {
            try (PrintWriter writer = new PrintWriter(path)) {
                writer.print(text);
            } catch (Exception e) {
            }
        }
        
    }

    public static void print(String text) {
        print(false, text, "");
    }

    public static void println(boolean toFile, String path, String text) {
        print(toFile, path, text + "\n");
    }

    public static void println(boolean toFile, String path) {
        print(toFile, path, "\n");
    }

    public static void println(String text) {
        print(text + "\n");
    }

    public static void println() {
        print("\n");
    }

    public static void printf(String format, Object... args) {
        print(String.format(format, args));
    }
}

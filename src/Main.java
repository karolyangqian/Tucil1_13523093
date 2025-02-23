import iqpuzzlerpro.*;
import java.awt.*;
import java.io.File;
import java.util.*;


public class Main {
    public static void driver1() {
        // Vec2I v1 = new Vec2I(0, 1);
        // Vec2I v2 = new Vec2I(1, 0);
        // Vec2I v3 = new Vec2I(1, 1);
        // Vec2I v4 = new Vec2I(2, 1);
        // Vec2I v5 = new Vec2I(0, 2);
        // Vec2I[] shape = new ArrayList<>();
        // shape.add(v1);
        // shape.add(v2);
        // shape.add(v3);
        // shape.add(v4);
        // shape.add(v5);
        // Piece2D piece = new Piece2D(1, shape);

        Piece2D piece = Piece2D.stringArrayToPiece2D(
            1,
            "A",
            new String[] {
                " X ",
                "XX ",
                "XX ",
            }
        );
        int rows = 5;
        int cols = 5;
        int anchorx = 2;
        int anchory = 2;
        piece.print(rows, cols, anchorx, anchory);
        piece.rotateOnce();
        piece.print(rows, cols, anchorx, anchory);
        piece.rotateOnce();
        piece.print(rows, cols, anchorx, anchory);
        piece.rotateOnce();
        piece.print(rows, cols, anchorx, anchory);
        piece.flipHorizontal();
        piece.print(rows, cols, anchorx, anchory);


        int colors[] = {
            196, 202, 208, 214, 220, 226, 34, 40, 46, 82, 118, 154, 
            21, 27, 33, 39, 45, 51, 90, 99, 129, 165, 200, 201, 207, 213,
        };

        for (int i = 0; i < colors.length; i++) {
            Ansi256.printColoredText("X", colors[i], 15);
        }

        System.out.println();

        System.out.println("input:");
        String[][] arr = new String[5][5];

        try (Scanner sc = new Scanner(System.in)) {
            int i = 0;
            while (true) {
                String input = sc.nextLine();
                System.out.println(input.length());
                if (input.length() == 0) {
                    break;
                }
                String[] split = input.strip().split(" ");

                // for (String j : split) {
                //     System.out.println(j);
                // }
                
                arr[i] = split;
                i++;
            }
        }
        System.out.println("output:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(arr[i][j]);
            }
        }
    }

    public static void driver2() {

        Map<Integer, Integer> colorMap = FileHandle.readColorMap("src/colors/colors0.txt");

        boolean[][] data = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                data[i][j] = true;
            }
        }

        data[0][0] = false;
        data[0][data.length-1] = false;
        data[data.length-1][0] = false;
        data[data.length-1][data.length-1] = false;

        Board2D board = new Board2D(5, 5, data);
        Piece2D piece = Piece2D.stringArrayToPiece2D(
            1,
            "A",
            new String[] {
                " X",
                "XX",
                "XX",
            }
        );
        
        // board.printEmptyBoard(Ansi256.coloredText("█", 15, 15), Ansi256.coloredText("█", 0, 15));
        piece.setPosition(1,1);
        piece.rotateOnce();
        board.placePiece(piece);
        board.printBoard(colorMap, Ansi256.coloredText("█", 15, 15), Ansi256.coloredText("█", 0, 15), 15);
        

    }

    public static void driver3() {
        String path = "test/tc1_spek.txt";
        ProgramInput programInput = new ProgramInput(); 
        try {
            programInput = FileHandle.readProgramInput(path);
        } catch (Exception e) {
            System.err.println(e);
        }
        programInput.print();
        System.err.println();

        Board2D board = new Board2D(programInput.rows, programInput.cols, programInput.board.getEmptyBoard());

        int[][] states = new int[][] {
            {0, 0, 0},
            {1, 2, 0},
            {2, 1, 3},
            {1, 3, 1},
            {4, 2, 7},
            {4, 4, 2},
            {0, 1, 0},
        };

        ArrayList<Piece2D> pieces = programInput.pieces;

        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).setState(states[i][0], states[i][1], states[i][2]);
            pieces.get(i).print(5, 5, states[i][0], states[i][1]);
        }
        // for (int i = 0; i < pieces.size(); i++) {
        //     int[] state = states[i];
        //     pieces.get(i).setState(state[0], state[1], state[2]);
        // }
        // board.addPieces(pieces);
        for (Piece2D p : pieces) {
            board.placePiece(p);
        }
        board.printPieces();
        // board.addPiece(pieces.get(0));

        Map<Integer, Integer> colorMap = FileHandle.readColorMap("src/colors/colors0.txt");

        board.printBoard(colorMap, Ansi256.coloredText("█", 0, 0), Ansi256.coloredText("█", 15, 15), 0);
        System.err.println(board.isSolved());
        
    }

    public static void driver4() {
        // String path = "test/tc1_spek.txt";
        // String path = "test/tc2.txt";
        // String path = "test/tc3.txt";
        String path = "test/tc4.txt";
        ProgramInput programInput; 
        try {
            programInput = FileHandle.readProgramInput(path);
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
        programInput.print();
        System.err.println();

        Board2D board = new Board2D(programInput.rows, programInput.cols, programInput.board.getEmptyBoard());
        // board.printEmptyBoard("X", ".");
        Solver.setColorMap("src/colors/colors0.txt");
        Scanner sc = new Scanner(System.in);
        System.err.println("Press Enter to start...");
        String input = sc.nextLine();
        boolean solved = Solver.solve(programInput, board, true);

        // Map<Integer, Integer> colorMap = FileHandle.readColorMap("src/colors/colors0.txt");

        // board.printBoard(colorMap, Ansi256.coloredText("█", 0, 0), Ansi256.coloredText("█", 15, 15), 0);
    }

    public static boolean yesNoPrompt(String prompt) {
        System.err.println(prompt);
        String input;
        Scanner sc = new Scanner(System.in);
        input = sc.nextLine();
        if (input.toLowerCase().equals("y") || input.toLowerCase().equals("n")) {
            return input.toLowerCase().equals("y");
        } else {
            System.err.println("Invalid input. Defaulting to no.");
        
        }
        return false;
    }

    public static String pathPrompt(String prompt, String defaultPath) {
        System.err.println(prompt);
        String input;
        Scanner sc = new Scanner(System.in);
        input = sc.nextLine();
        File file = new File(input);
        while (!file.exists() && input.length() > 0) {
            System.err.println("File does not exist. Enter the path to an existing file (default: " + defaultPath + "):");
            input = sc.nextLine();
            file = new File(input);
        
        }

        if (input.length() > 0) {
            return input;
        }
        return defaultPath;
    }

    public static void saveFileDialog(Result result) {
        Frame frame = new Frame();
        FileDialog fileDialog = new FileDialog(frame, "Save result to file", FileDialog.SAVE);
        fileDialog.setFile("result.txt");
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (directory != null && filename != null) {
            if (!filename.toLowerCase().endsWith(".txt")) {
                filename += ".txt";
            }
            File file = new File(directory, filename);

            try {
                FileHandle.saveResult(result, file.getAbsolutePath());
                System.out.println("File saved successfully as " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        frame.dispose();
    }
    
    public static void startProgram() {
        String path = "test/tc4.txt";

        path = pathPrompt("Enter the path to the test case file (default:" + path + "):", path);

        ProgramInput programInput; 
        
        try {
            programInput = FileHandle.readProgramInput(path);
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
        
        programInput.print();
        System.err.println();
        
        String colorMapPath = "src/colors/colors0.txt";
        colorMapPath = pathPrompt("Enter the path to the color map file (default:" + colorMapPath + "):", colorMapPath);

        Solver.setColorMap(colorMapPath);
        Map<Integer, Integer> colorMap = FileHandle.readColorMap(colorMapPath);

        boolean printResult = yesNoPrompt("Print result? (y/n):");
        boolean printProgress = yesNoPrompt("Print progress? (y/n):");

        Board2D board = new Board2D(programInput.rows, programInput.cols, programInput.board.getEmptyBoard());
        
        Scanner sc = new Scanner(System.in);
        System.err.println("Press Enter to start...");
        String input = sc.nextLine();
        
        boolean solved = Solver.solve(programInput, board, printResult, printProgress);

        if (solved) {
            System.err.println("Solved!");
        } else {
            System.err.println("Not solved.");
        }

        boolean saveResult = yesNoPrompt("Save result? (y/n):");
        if (saveResult) {
            saveFileDialog(Solver.getResult());
        }
        System.err.println("Program ended.");

    }

    public static void main(String[] args) {
        // driver1();
        // driver2();
        // driver3();
        // driver4();
        startProgram();
    }
}

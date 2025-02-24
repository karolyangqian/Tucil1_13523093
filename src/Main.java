import iqpuzzlerpro.*;
import java.awt.*;
import java.io.File;
import java.util.*;


public class Main {

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
        startProgram();
    }
}

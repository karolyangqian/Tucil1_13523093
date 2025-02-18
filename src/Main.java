import iqpuzzlerpro.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
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

        Piece2D piece = Piece2D.formStringList(1,
            new String[] {
                " X ",
                "XX ",
                "XX ",
            }
        );
        int width = 5;
        int height = 5;
        int anchorx = 2;
        int anchory = 2;
        piece.printShape();
        piece.print("X", width, height, anchorx, anchory);
        piece.rotateOnce();
        piece.print("X", width, height, anchorx, anchory);
        piece.rotateOnce();
        piece.print("X", width, height, anchorx, anchory);
        piece.rotateOnce();
        piece.print("X", width, height, anchorx, anchory);
        piece.flipHorizontal();
        piece.print("X", width, height, anchorx, anchory);
        piece.flipVertical();
        piece.print("X", width, height, anchorx, anchory);


        int colors[] = {
            196, 202, 208, 214, 220, 226, 34, 40, 46, 82, 118, 154, 
            21, 27, 33, 39, 45, 51, 90, 99, 129, 165, 200, 201, 207, 213,
        };

        for (int i = 0; i < colors.length; i++) {
            Ansi.printColoredText("X", colors[i]);
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
}

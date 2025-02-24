package iqpuzzlerpro;
import java.util.*;

public class Piece2D {

    private final int id;
    private final String symbol;
    private final Vec2I[] shape;
    private int state;

    public Piece2D(int id, String symbol, Vec2I[] shape) {
        this.id = id;
        this.symbol = symbol;
        this.shape = shape;
        this.state = 0;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Vec2I[] getShape() {
        Vec2I[] shapeCopy = new Vec2I[shape.length];
        for (int i = 0; i < shape.length; i++) {
            shapeCopy[i] = new Vec2I(shape[i].x, shape[i].y);
        }
        return shapeCopy;
    }

    public void rotateOnce() {
        for (Vec2I v : shape) {
            v.rotateOnceAroundCenter(shape[0]);
        }
    }

    public boolean contains(Vec2I v) {
        for (Vec2I vec : shape) {
            if (vec.equals(v)) {
                return true;
            }
        }
        return false;
    }

    public void print(int rows, int cols, int offsetX, int offsetY) {
        printShape();
        Piece2D tempPiece = this.copy();
        tempPiece.setPosition(new Vec2I(offsetX, offsetY));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Vec2I v = new Vec2I(i, j);
                if (tempPiece.contains(v)) {
                    System.out.print(symbol);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void printShape() {
        System.out.print("Piece " + id + " " + symbol + ": ");
        for (Vec2I v : shape) {
            System.out.print("(" + v.x + ", " + v.y + ") ");
        }
        System.out.println();
    }

    public Vec2I getPosition() {
        return shape[0].copy();
    }

    public final void setPosition(Vec2I position) {
        Vec2I offset = new Vec2I(shape[0].x, shape[0].y);
        for (Vec2I v : shape) {
            v.x -= offset.x;
            v.y -= offset.y;
        }
        for (Vec2I v : shape) {
            v.x += position.x;
            v.y += position.y;
        }
    }

    public void setPosition(int x, int y) {
        setPosition(new Vec2I(x, y));
    }

    public void flipHorizontal() {
        for (Vec2I v : shape) {
            v.mirrorX(shape[0].x);
        }
    }

    public Piece2D copy() {
        Vec2I[] newShape = new Vec2I[shape.length];
        for (int i = 0; i < shape.length; i++) {
            newShape[i] = new Vec2I(shape[i].x, shape[i].y);
        }
        return new Piece2D(id, symbol, newShape);
    }

    public static Piece2D stringArrayToPiece2D(int newId, String newSymbol, String[] array) {
        ArrayList<Vec2I> newShapeList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String[] split = array[i].split("");
            for (int j = 0; j < split.length; j++) {
                if (!split[j].equals(" ")) {
                    newShapeList.add(new Vec2I(i, j));
                }
            }
        }
        return new Piece2D(newId, newSymbol, newShapeList.toArray(Vec2I[]::new));
    }

    public int getState() {
        return state;
    }

    public void setState(int r, int c, int state) {
        setPosition(r, c);
        int rot = state % 4;
        int flip = state / 4;
        for (int i = 0; i < rot; i++) {
            rotateOnce();
        }
        if (flip == 1) flipHorizontal();
        this.state = state;
    }

}

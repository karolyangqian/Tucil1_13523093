package iqpuzzlerpro;
import java.util.*;

public class Piece2D {

    private final int id;
    private final Vec2I[] shape;

    public Piece2D(int id, Vec2I[] shape) {
        this.id = id;
        this.shape = shape;
    }

    public int getId() {
        return id;
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

    public void print(String c, int width, int height, int offsetX, int offsetY) {
        System.out.print("Piece " + id + ": ");
        Piece2D tempPiece = this.copy();
        int n = shape.length;
        for (int i = 0; i < n; i++) {
            System.err.print("(" + shape[i].x + ", " + shape[i].y + ") ");
        }
        System.err.println();
        tempPiece.setPosition(new Vec2I(offsetX, offsetY));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vec2I v = new Vec2I(i, j);
                if (tempPiece.contains(v)) {
                    System.out.print(c);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void printShape() {
        System.out.println("Piece " + id);
        for (Vec2I v : shape) {
            System.out.println(v.x + " " + v.y);
        }
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

    public void flipHorizontal() {
        for (Vec2I v : shape) {
            v.mirrorX(shape[0].x);
        }
    }

    public void flipVertical() {
        for (Vec2I v : shape) {
            v.mirrorY(shape[0].y);
        }
    }

    public Piece2D copy() {
        Vec2I[] newShape = new Vec2I[shape.length];
        for (int i = 0; i < shape.length; i++) {
            newShape[i] = new Vec2I(shape[i].x, shape[i].y);
        }
        return new Piece2D(id, newShape);
    }

    public static Piece2D formStringList(int newId, String[] list) {
        ArrayList<Vec2I> newShapeList = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String[] split = list[i].split("");
            for (int j = 0; j < split.length; j++) {
                if (!split[j].equals(" ")) {
                    newShapeList.add(new Vec2I(i, j));
                }
            }
        }
        return new Piece2D(newId, newShapeList.toArray(Vec2I[]::new));
    }
}

package iqpuzzlerpro;

import java.util.Objects;

public class Vec2I extends Object {
    public int x;
    public int y;

    public Vec2I(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vec2I v) {
            return this.x == v.x && this.y == v.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void rotateOnceAroundCenter(Vec2I center) {
        int tempx = this.x;
        this.x = center.x - (this.y - center.y);
        this.y = center.y + (tempx - center.x);
    }

    public Vec2I copy() {
        return new Vec2I(this.x, this.y);
    }

    public void mirrorX(int lineX) {
        int d = lineX - this.x;
        this.x = lineX + d;
    }

    public void mirrorY(int lineY) {
        int d = lineY - this.y;
        this.y = lineY + d;
    }

}

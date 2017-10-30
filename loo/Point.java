package loo;

public class Point {
    private final int x, y;
    Point(final int x, final int y) {
      this.x = x;
      this.y = y;
    }
    public String toString() {
      return String.format("(%d, %d)", this.x, this.y);
    }
}

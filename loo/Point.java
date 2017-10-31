package loo;

public class Point {
    private final int x, y;
    public Point(final int x, final int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return this.x;
    }

    public int getY() {
      return this.y;
    }

    @Override
    public boolean equals(Object obj) {
      if(obj instanceof Point){
        Point point = (Point) obj;
        return (this.getX() == point.getX() && this.getY() == point.getY());
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Integer.valueOf(String.valueOf(this.x) + String.valueOf(this.y));
    }

    public String toString() {
      return String.format("(%d, %d)", this.x, this.y);
    }
}

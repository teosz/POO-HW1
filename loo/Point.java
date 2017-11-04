package loo;

public final class Point {
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

    public static Point fromObject(Object point) {
      return Point.class.cast(point);
    }


    @Override
    public boolean equals(final Object obj) {
      if (obj instanceof Point) {
        Point point = (Point) obj;
        return (this.getX() == point.getX() && this.getY() == point.getY());
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Integer.valueOf(String.valueOf(this.x) + String.valueOf(this.y));
    }

    @Override
    public String toString() {
      return String.format("(%d, %d)", this.x, this.y);
    }
}

package loo;
/**
  * Class providing the functionality to work with cartesian coordinates.
  */
public final class Point {
    private int x, y;

    /**
    * @param x the X coordinate
    * @param y the Y coordinate
    */
    public Point(final int x, final int y) {
      this.x = x;
      this.y = y;
    }

    /**
    * @return the X of the point
    */
    public int getX() {
      return this.x;
    }

    /**
    * @return the Y of the point
    */
    public int getY() {
      return this.y;
    }

    /**
    * Add two points together.
    * @param term the Point to add
    */
    public void add(final Point term) {
      this.x += term.getX();
      this.y += term.getY();
    }

    /**
      * Cast a point from Object.
      * @param point object to be casted
      * @return object casted into a Point
      */
    public static Point fromObject(final Object point) {
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
      int result = x;
      result = 31 * result + y;
      return result;
    }

    @Override
    public String toString() {
      return String.format("%d %d", this.x, this.y);
    }
}

package statecontainer;
class Point {
    private final int x,y;
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public String toString() {
      return String.format("(%d, %d)", this.x, this.y);
    }
}

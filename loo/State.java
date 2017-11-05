package loo;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

class State {
  List<StateCell> cells;
  Map<Point, String> terrianType;
  State(int noHeros) {
    this.cells = new ArrayList<StateCell>();
  }

  State(int mapWidth, int mapHeight, List<String> terrianMatrix) {
    this.cells = new ArrayList<StateCell>();
    for(int i = 0; i<mapHeight;i++)
      for(int j = 0; j<mapWidth;j++) {
        Point p = new Point(i,j);
        System.out.println(p);
      }
  }


  List<StateCell> getCells() {
    return this.cells;
  }

}

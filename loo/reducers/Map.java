package loo.reducers;

import loo.StateCell;
import loo.Actions;
import loo.Hero;
import loo.Point;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.Dictionary;
import java.util.Hashtable;

public class Map implements Reducer<StateCell[]> {
  private int nr=0;
  @Override
  public StateCell[] reduce(final StateCell[] state, final Action action) {
    switch (action.getType()) {
      case Actions.ADD_HERO:
        Dictionary payload = action.getPayload();
        Hero hero = (Hero) (payload.get("hero"));
        Point position = (Point) (payload.get("position"));
        state[this.nr++] = new StateCell(hero, position);
        System.out.println(state[this.nr-1]);
        return state;
      default:
        return state;
    }
  }
}

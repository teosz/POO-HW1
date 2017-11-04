package loo.reducers;

import loo.StateCell;
import loo.Actions;
import loo.Hero;
import loo.Point;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.Map;
import java.util.List;

public final class MapReducer implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    switch (action.getType()) {
      case Actions.ADD_HERO:
        Map payload = action.getPayload();
        Hero hero = Hero.fromObject(payload.get("hero"));
        Point position = Point.fromObject(payload.get("position"));
        state.add(new StateCell(hero, position));
        return state;
      default:
        return state;
    }
  }
}

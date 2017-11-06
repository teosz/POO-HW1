package loo.reducers;

import loo.StateCell;
import loo.Actions;
import loo.Hero;
import loo.Point;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.Map;
import java.util.List;

public final class MapManagement implements Reducer<List<StateCell>> {
  private Point getMoveBy(final Character s) {
    switch (s) {
      case 'L':
        return new Point(0, -1);
      case 'R':
        return new Point(0, 1);
      case 'U':
        return new Point(1, 0);
      case 'D':
        return new Point(-1, 0);
      default:
        return new Point(0, 0);
    }
  }
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    Map payload = action.getPayload();
    switch (action.getType()) {
      case Actions.ADD_HERO:
        Hero hero = Hero.fromObject(payload.get("hero"));
        Point position = Point.fromObject(payload.get("position"));
        state.add(new StateCell(hero, position));
        return state;
      case Actions.MOVE_BY_SEQUENCE:
        String sequence = payload.get("sequence").toString();
        for (int i = 0; i < state.size(); i++) {
          StateCell cell = state.get(i);
          if (!cell.getHero().isFrozen()) {
            cell.move(getMoveBy(sequence.charAt(i)));
          }
        }
        return state;
      default:
        return state;
    }
  }
}

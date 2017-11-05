package loo.reducers;

import loo.StateCell;
import loo.Spell;
import loo.Hero;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public final class RoundManagement implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    System.out.println(action);
    switch (action.getType()) {
      default:
        return state;
    }
  }
}

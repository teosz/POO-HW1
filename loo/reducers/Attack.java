package loo.reducers;

import loo.StateCell;
import loo.Actions;
import loo.Hero;
import loo.Point;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Attack implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(final List<StateCell> state, final Action action) {
    switch (action.getType()) {
      default:
        return state;
    }
  }
}

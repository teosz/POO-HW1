package loo.reducers;

import loo.StateCell;
import loo.Actions;
import loo.Hero;
import loo.Point;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.Dictionary;
import java.util.Hashtable;

public class Attack implements Reducer<StateCell[]> {
  @Override
  public StateCell[] reduce(final StateCell[] state, final Action action) {
    switch (action.getType()) {
      default:
        return state;
    }
  }
}

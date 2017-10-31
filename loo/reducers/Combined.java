package loo.reducers;

import loo.StateCell;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.Dictionary;
import java.util.Hashtable;

public class Combined implements Reducer<StateCell[]> {
  @Override
  public StateCell[] reduce(StateCell[] state, final Action action) {
    state = new Attack().reduce(state, action);
    state = new Map().reduce(state, action);
    return state;
  }
}

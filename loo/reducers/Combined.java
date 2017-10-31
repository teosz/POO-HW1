package loo.reducers;

import loo.StateCell;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Combined implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(List<StateCell> state, final Action action) {
    state = new Attack().reduce(state, action);
    state = new Map().reduce(state, action);
    return state;
  }
}

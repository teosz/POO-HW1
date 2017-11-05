package loo.reducers;

import loo.StateCell;
import statecontainer.Reducer;
import statecontainer.Action;
import java.util.List;

public final class Combined implements Reducer<List<StateCell>> {
  @Override
  public List<StateCell> reduce(List<StateCell> state, final Action action) {
    state = new Attack().reduce(state, action);
    state = new RoundManagement().reduce(state, action);
    state = new MapReducer().reduce(state, action);
    return state;
  }
}
